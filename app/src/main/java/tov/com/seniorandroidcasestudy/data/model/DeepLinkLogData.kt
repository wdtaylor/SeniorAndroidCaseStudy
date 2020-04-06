package tov.com.seniorandroidcasestudy.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DeepLinkLogData(@field:PrimaryKey val id: Int, val trafficSource: String)
//data class DeepLinkLogData(@field:PrimaryKey(autoGenerate = true) val id: Int, val idDeepLink: String, val trafficSource: String)