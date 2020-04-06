package tov.com.seniorandroidcasestudy.viewmodel

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import tov.com.seniorandroidcasestudy.data.repository.MarketingLogRepository

class DeepLinkActivityViewModel(private val marketingLogRepository: MarketingLogRepository) : AsyncViewModel() {

    init {

        marketingLogRepository.registerCallbackDeepLinkReferrer { resultMap ->
            liveDataIntent.value = resultMap
        }

        marketingLogRepository.registerDeepLinkCallBackApi { resultBody ->
            liveDataApiResponse.value = resultBody
        }
    }

    val liveDataApiResponse: MutableLiveData<String> = MutableLiveData()

    val liveDataIntent: MutableLiveData<HashMap<String, String?>> = MutableLiveData()

    fun processIntent(intent : Intent?){
        marketingLogRepository.processDeepLinkIntent(intent)
    }

    fun getDeepLinkRecord(id: Int) {
        marketingLogRepository.getDeepLinkRecord(id)
    }
}