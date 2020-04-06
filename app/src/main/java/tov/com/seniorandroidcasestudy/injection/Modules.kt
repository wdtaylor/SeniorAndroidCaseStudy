package tov.com.seniorandroidcasestudy.injection

import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import tov.com.seniorandroidcasestudy.data.database.AppDatabase
import tov.com.seniorandroidcasestudy.data.network.CaseStudyPostApi
import tov.com.seniorandroidcasestudy.data.repository.MarketingLogRepository
import tov.com.seniorandroidcasestudy.viewmodel.DatabaseListViewModel
import tov.com.seniorandroidcasestudy.viewmodel.DeepLinkActivityViewModel
import tov.com.seniorandroidcasestudy.viewmodel.MainActivityViewModel

object Modules {

    private val networkModule = module {
        single {
            val retrofit: Retrofit = get()
            retrofit.create(CaseStudyPostApi::class.java)
        }
        single {
            Retrofit.Builder()
                .baseUrl("https://casestudy.alltheapps.org")
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
        }
    }

    private val databaseModule = module {

        single {
            val db: AppDatabase = get()
            db.marketingLogDao()
        }

        single {
            val db: AppDatabase = get()
            db.deepLinkLogDao()
        }

        single {
            Room.databaseBuilder(get(), AppDatabase::class.java, "marketing_logs")
                .fallbackToDestructiveMigration()
                .build()
        }

    }

    private val repositoryModule = module {
        single { MarketingLogRepository(get(), get(), get(), get()) }
    }

    private val viewModelModule = module {
        viewModel { MainActivityViewModel(get()) }
        viewModel { DatabaseListViewModel(get()) }
        viewModel { DeepLinkActivityViewModel(get()) }
    }

    val all: List<Module> = listOf(
        networkModule,
        databaseModule,
        repositoryModule,
        viewModelModule
    )
}