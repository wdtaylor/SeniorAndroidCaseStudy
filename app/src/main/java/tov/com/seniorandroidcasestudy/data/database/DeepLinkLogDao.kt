package tov.com.seniorandroidcasestudy.data.database

import androidx.room.*
import tov.com.seniorandroidcasestudy.data.model.DeepLinkLogData


@Dao
interface DeepLinkLogDao {

    companion object {
        const val id = "id"
        const val trafficSource = "trafficSource"
    }

    @Query("SELECT * FROM deeplinklogdata")
    fun all(): List<DeepLinkLogData>

    @Query("SELECT * FROM deeplinklogdata WHERE id=:id")
    fun getById(id: Int): DeepLinkLogData

    @get:Query("SELECT count(*) FROM deeplinklogdata")
    val count: Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg deeplinklogdata: DeepLinkLogData)

    @Delete
    fun delete(model: DeepLinkLogData?)

}