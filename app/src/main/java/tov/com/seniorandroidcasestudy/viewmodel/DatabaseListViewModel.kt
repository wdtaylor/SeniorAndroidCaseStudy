package tov.com.seniorandroidcasestudy.viewmodel

import androidx.lifecycle.MutableLiveData
import tov.com.seniorandroidcasestudy.data.model.MarketingLogData
import tov.com.seniorandroidcasestudy.data.repository.MarketingLogRepository

class DatabaseListViewModel(private val marketingLogRepository: MarketingLogRepository) : AsyncViewModel() {

    init {
        marketingLogRepository.registerCallBackDatabase { databaseContents ->
            liveDataDatabaseContent.value = databaseContents
        }
    }

    val liveDataDatabaseContent: MutableLiveData<List<MarketingLogData>> = MutableLiveData()

    fun getAllDatabaseContent(){
        marketingLogRepository.getAllDatabaseContent()
    }

}