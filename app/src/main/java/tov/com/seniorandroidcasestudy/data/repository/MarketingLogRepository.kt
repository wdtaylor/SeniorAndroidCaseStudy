package tov.com.seniorandroidcasestudy.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Looper
import android.provider.Settings
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.launch
import timber.log.Timber
import tov.com.seniorandroidcasestudy.data.Constants.Companion.KEY_REFERRER
import tov.com.seniorandroidcasestudy.data.database.DeepLinkLogDao
import tov.com.seniorandroidcasestudy.data.database.MarketingLogDao
import tov.com.seniorandroidcasestudy.data.model.DeepLinkLogData
import tov.com.seniorandroidcasestudy.data.model.MarketingLogData
import tov.com.seniorandroidcasestudy.data.network.CaseStudyPostApi
import java.net.URLDecoder

class MarketingLogRepository(
    private val context: Context,
    private val marketingLogDao: MarketingLogDao,
    private val deepLinkLogDao: DeepLinkLogDao,
    private val caseStudyPostApi: CaseStudyPostApi
) :
    AsyncRepository() {

    val TAG = MarketingLogRepository::class.java.simpleName

    fun processIntent(intent: Intent?) {
        val resultMap = parseIntentData(intent)
        launch { logMarketingData(resultsMap = resultMap) }
        callbackReferrer?.invoke(resultMap)
        Log.d(TAG, resultMap.toString())
    }

    private var callbackReferrer: ((map: HashMap<String, String?>) -> Unit)? = null
    fun registerCallBackReferrer(callback: (map: HashMap<String, String?>) -> Unit) {
        callbackReferrer = callback
    }

    private var callbackApi: ((result: String?) -> Unit)? = null
    fun registerCallBackApi(callback: (result: String?) -> Unit) {
        callbackApi = callback
    }

    private var callbackDeepLinkApi: ((result: String?) -> Unit)? = null
    fun registerDeepLinkCallBackApi(callback: (result: String?) -> Unit) {
        callbackDeepLinkApi = callback
    }

    private var callbackDatabase: ((result: List<MarketingLogData>?) -> Unit)? = null
    fun registerCallBackDatabase(callback: (result: List<MarketingLogData>?) -> Unit) {
        callbackDatabase = callback
    }

    private var callbackDeepLinkReferrer: ((map: HashMap<String, String?>) -> Unit)? = null
    fun registerCallbackDeepLinkReferrer(callback: (map: HashMap<String, String?>) -> Unit) {
        callbackDeepLinkReferrer = callback
    }

    @SuppressLint("HardwareIds")
    private fun parseIntentData(intent: Intent?): HashMap<String, String?> {

        val resultsMap: HashMap<String, String?> = HashMap()

        intent?.extras?.get(KEY_REFERRER)?.let {
            val uri = Uri.parse(URLDecoder.decode(it.toString(), "UTF-8") as String)

            resultsMap[MarketingLogDao.userId] = uri.getQueryParameter(MarketingLogDao.userId)
            resultsMap[MarketingLogDao.implementationId] =
                uri.getQueryParameter(MarketingLogDao.implementationId)
            resultsMap[MarketingLogDao.trafficSource] =
                uri.getQueryParameter(MarketingLogDao.trafficSource)
            resultsMap[MarketingLogDao.userClass] = uri.getQueryParameter(MarketingLogDao.userClass)
            resultsMap[MarketingLogDao.osVersion] = System.getProperty("os.version") // OS version
            resultsMap[MarketingLogDao.deviceId] = Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            )

            resultsMap[MarketingLogDao.model] = android.os.Build.MODEL
            resultsMap[MarketingLogDao.manufacturer] = android.os.Build.MANUFACTURER
            resultsMap[MarketingLogDao.sdkInt] = android.os.Build.VERSION.SDK_INT.toString()
            resultsMap[MarketingLogDao.deviceType] = android.os.Build.DEVICE

        }

        return resultsMap
    }

    fun getAllDatabaseContent(){
        launch { getAllDatabaseContentLocal() }
    }

    suspend fun getAllDatabaseContentLocal() {
        withContext(Dispatchers.IO) {
            try {
                callbackDatabase?.let { callback ->
                    val allData = marketingLogDao.all()
                    android.os.Handler(Looper.getMainLooper()).post {
                        callback(allData)
                    }
                }

            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    suspend fun logMarketingData(resultsMap: HashMap<String, String?>) =
        withContext(Dispatchers.IO) {

            try {

                val marketingLogData = getMarketingLogData(resultsMap)
                marketingLogDao.insert(marketingLogData)

                callbackApi?.let { callback ->
                    val responseBody =
                        caseStudyPostApi.mobileInstall(marketingLogData).await().string()
                    android.os.Handler(Looper.getMainLooper()).post {
                        callback(responseBody)
                    }
                }

            } catch (e: Exception) {
                Timber.e(e)
            }
        }

    fun getMarketingLogData(resultsMap: HashMap<String, String?>): MarketingLogData {

        return MarketingLogData(
            0,
            resultsMap[MarketingLogDao.userClass].orEmpty(),
            resultsMap[MarketingLogDao.implementationId].orEmpty(),
            resultsMap[MarketingLogDao.trafficSource].orEmpty(),
            resultsMap[MarketingLogDao.model].orEmpty(),
            resultsMap[MarketingLogDao.userId].orEmpty(),
            resultsMap[MarketingLogDao.deviceId].orEmpty(),
            resultsMap[MarketingLogDao.osVersion].orEmpty(),
            resultsMap[MarketingLogDao.deviceType].orEmpty(),
            resultsMap[MarketingLogDao.manufacturer].orEmpty(),
            resultsMap[MarketingLogDao.sdkInt].orEmpty()
        )
    }

    fun getDeepLinkLogData(resultsMap: HashMap<String, String?>): DeepLinkLogData {

        val id = resultsMap[DeepLinkLogDao.id]?.toInt() ?: 0

        return DeepLinkLogData(
            id,
            resultsMap[DeepLinkLogDao.trafficSource].orEmpty()
        )
    }

    fun processDeepLinkIntent(intent: Intent?) {
        val resultMap = parseDeepLinkIntentData(intent)
        launch { logDeepLinkData(resultsMap = resultMap) }
        callbackDeepLinkReferrer?.invoke(resultMap)
    }

    suspend fun logDeepLinkData(resultsMap: HashMap<String, String?>) =
        withContext(Dispatchers.IO) {

            try {

                val deepLinkLogData = getDeepLinkLogData(resultsMap)
                deepLinkLogDao.insert(deepLinkLogData)

                Log.d(TAG, "Count: ${deepLinkLogDao.count}")

            } catch (e: Exception) {
                Timber.e(e)
            }
        }

    @SuppressLint("HardwareIds")
    private fun parseDeepLinkIntentData(intent: Intent?): HashMap<String, String?> {

        val resultsMap: HashMap<String, String?> = HashMap()
        val uri: Uri? = intent?.data

        uri?.let {
            resultsMap[DeepLinkLogDao.id] = it.getQueryParameter(DeepLinkLogDao.id)
            resultsMap[DeepLinkLogDao.trafficSource] = it.getQueryParameter(DeepLinkLogDao.trafficSource)
        }

        return resultsMap
    }

    fun getDeepLinkRecord(id: Int) {

        launch { queryDeepLinkData(id) }

    }

    suspend fun queryDeepLinkData(id : Int) =
        withContext(Dispatchers.IO) {

            try {

                val deepLinkLogData = deepLinkLogDao.getById(id)

                Log.d(TAG, "Was Fetched: ${deepLinkLogData.id}")

                deepLinkLogData?.let {
                    callbackDeepLinkApi?.let { callback ->
                        val responseBody =
                            caseStudyPostApi.mobileInstall(deepLinkLogData).await().string()
                        android.os.Handler(Looper.getMainLooper()).post {
                            callback(responseBody)
                        }
                    }
                }

            } catch (e: Exception) {
                Timber.e(e)
            }
        }



}