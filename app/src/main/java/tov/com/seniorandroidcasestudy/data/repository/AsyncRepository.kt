package tov.com.seniorandroidcasestudy.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class AsyncRepository : CoroutineScope {

    private var repositoryJob = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + repositoryJob

}