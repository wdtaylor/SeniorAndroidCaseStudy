package tov.com.seniorandroidcasestudy.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_app_driver.*
import tov.com.seniorandroidcasestudy.R
import tov.com.seniorandroidcasestudy.data.Constants.Companion.ACTION_INSTALL_REFERRER
import tov.com.seniorandroidcasestudy.data.Constants.Companion.KEY_REFERRER

class AppDriverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_driver)

        btn_launch_gp_broadcast_referrer.setOnClickListener {
            Intent().also { intent ->
                intent.action = ACTION_INSTALL_REFERRER
                intent.putExtra(KEY_REFERRER, "https%3a%2f%2fm.alltheapps.org%2fget%2fapp%3fuserId%3dB1C92850-8202-44AC-B514-1849569F37B6%26implementationid%3dcl-and-erp%26trafficSource%3derp%26userClass%3d20200101")
                startActivity(intent)
            }
        }

        btn_database_content_list.setOnClickListener {
            val intent = Intent(this, DatabaseListActivity::class.java)
            startActivity(intent)
        }

        btn_deep_link_action.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("spigot://eng.dev/cs/product_info?id=12345&trafficSource=deeplink")
            startActivity(intent)
        }
    }

}
