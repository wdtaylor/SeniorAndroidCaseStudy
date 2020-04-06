package tov.com.seniorandroidcasestudy.data.network

import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST
import tov.com.seniorandroidcasestudy.data.model.DeepLinkLogData
import tov.com.seniorandroidcasestudy.data.model.MarketingLogData

interface CaseStudyPostApi {
    @POST("mobile/install")
    fun mobileInstall(@Body marketingLogData: MarketingLogData): Deferred<ResponseBody>

    @POST("mobile/install")
    fun mobileInstall(@Body deepLinkLogData: DeepLinkLogData): Deferred<ResponseBody>
}