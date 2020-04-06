package tov.com.seniorandroidcasestudy.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import tov.com.seniorandroidcasestudy.data.Constants.Companion.ACTION_INSTALL_REFERRER
import tov.com.seniorandroidcasestudy.data.Constants.Companion.KEY_REFERRER

@Deprecated("Not used in project")
class GooglePlayReferrerReceiver(private val callback: (intent: Intent?) -> Unit) : BroadcastReceiver() {

    companion object {
        fun sendBroadcastNow(context: Context?){
            Intent().also { intent ->
                intent.action = ACTION_INSTALL_REFERRER
                intent.putExtra(KEY_REFERRER, "https%3a%2f%2fm.alltheapps.org%2fget%2fapp%3fuserId%3dB1C92850-8202-44AC-B514-1849569F37B6%26implementationid%3dcl-and-erp%26trafficSource%3derp%26userClass%3d20200101")
                context?.sendBroadcast(intent)
            }
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        callback(intent)
    }

}