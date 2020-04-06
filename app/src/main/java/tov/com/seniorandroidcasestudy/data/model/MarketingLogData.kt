package tov.com.seniorandroidcasestudy.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MarketingLogData(@field:PrimaryKey(autoGenerate = true) val id: Int, val userClass: String, val implementationId: String,
                            val trafficSource: String, val model : String, val userId : String, val deviceId :String,
                            val osVersion: String, val deviceType: String, val manufacturer: String, val sdkInt: String )