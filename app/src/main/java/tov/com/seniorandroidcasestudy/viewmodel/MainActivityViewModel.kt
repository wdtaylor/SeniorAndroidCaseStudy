package tov.com.seniorandroidcasestudy.viewmodel

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import tov.com.seniorandroidcasestudy.data.repository.MarketingLogRepository

class MainActivityViewModel(private val marketingLogRepository: MarketingLogRepository) : AsyncViewModel() {

    init {

        marketingLogRepository.registerCallBackReferrer { resultMap ->
            liveDataIntent.value = resultMap
        }

        marketingLogRepository.registerCallBackApi { resultBody ->
            liveDataApiResponse.value = resultBody
        }
    }

    val liveDataUpdate: MutableLiveData<String> = MutableLiveData()
    val liveDataIntent: MutableLiveData<HashMap<String, String?>> = MutableLiveData()
    val liveDataApiResponse: MutableLiveData<String> = MutableLiveData()

    fun processIntent(intent : Intent?){
        marketingLogRepository.processIntent(intent)
    }
}