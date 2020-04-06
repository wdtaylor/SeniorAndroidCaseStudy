package tov.com.seniorandroidcasestudy.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import tov.com.seniorandroidcasestudy.data.model.DeepLinkLogData
import tov.com.seniorandroidcasestudy.data.model.MarketingLogData

@Database(entities = [MarketingLogData::class, DeepLinkLogData::class], version = 3, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun marketingLogDao(): MarketingLogDao

    abstract fun deepLinkLogDao(): DeepLinkLogDao

}