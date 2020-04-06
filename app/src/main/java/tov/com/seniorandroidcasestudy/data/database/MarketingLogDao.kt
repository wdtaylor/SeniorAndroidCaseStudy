package tov.com.seniorandroidcasestudy.data.database

import androidx.room.*
import tov.com.seniorandroidcasestudy.data.model.MarketingLogData


@Dao
interface MarketingLogDao {

    companion object {
        const val userClass = "userClass"
        const val implementationId = "implementationid"
        const val trafficSource = "trafficSource"
        const val model = "model"
        const val userId = "userId"
        const val deviceId = "deviceId"
        const val osVersion = "osVersion"
        const val deviceType = "deviceType"
        const val manufacturer = "manufacturer"
        const val sdkInt = "sdkInt"
    }

    @Query("SELECT * FROM marketinglogdata")
    fun all(): List<MarketingLogData>

    @Query("SELECT * FROM marketinglogdata WHERE id=:marketinglogdataId")
    fun getById(marketinglogdataId: Int): MarketingLogData

    @get:Query("SELECT count(*) FROM marketinglogdata")
    val count: Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg marketingLogData: MarketingLogData)

    @Delete
    fun delete(model: MarketingLogData?)

}