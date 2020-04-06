package tov.com.seniorandroidcasestudy.ui.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_deeplink.*
import kotlinx.android.synthetic.main.activity_main.results1
import org.koin.android.viewmodel.ext.android.viewModel
import tov.com.seniorandroidcasestudy.R
import tov.com.seniorandroidcasestudy.data.database.DeepLinkLogDao
import tov.com.seniorandroidcasestudy.viewmodel.DeepLinkActivityViewModel


class DeepLinkActivity : AppCompatActivity() {

    val TAG = DeepLinkActivity::class.java.simpleName

    private val viewModel: DeepLinkActivityViewModel by viewModel()

    private var results : HashMap<String, String?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deeplink)

        viewModel.liveDataIntent.observe(this, Observer { result1 ->
            result1?.let {
                results = it
                results1.text = it.toString()
                btn_post_data_to_api.visibility = View.VISIBLE
            }
        })

        viewModel.liveDataApiResponse.observe(this, Observer { result2 ->
            result2?.let { data ->
                showAlertDialogCopyPaste(data)
            }
        })

        btn_post_data_to_api.setOnClickListener {

            val id = results?.get(DeepLinkLogDao.id)?.toInt() ?: -1

            viewModel.getDeepLinkRecord(id)
        }

        viewModel.processIntent(intent)

    }

    private fun showAlertDialogCopyPaste(data : String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Deep Link Data")
        builder.setMessage(data)

        builder.setPositiveButton(android.R.string.copy) { dialog, which ->

            val clipboard: ClipboardManager =
                getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("deeplink", data)
            clipboard.primaryClip = clip

            Toast.makeText(applicationContext,
                "copied to clipboard: $data", Toast.LENGTH_LONG).show()
        }

        builder.setNegativeButton(android.R.string.cancel) { dialog, which -> }

        builder.show()
    }

}
