package tov.com.seniorandroidcasestudy.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import tov.com.seniorandroidcasestudy.R
import tov.com.seniorandroidcasestudy.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    val TAG = MainActivity::class.java.simpleName

    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.liveDataUpdate.observe(this, Observer {
            Log.d(TAG, it)
        })

        viewModel.liveDataIntent.observe(this, Observer { result ->
            result?.let {
                results1.text = it.toString()
            }
        })

        viewModel.liveDataApiResponse.observe(this, Observer {
            results2.text = it
        })

        viewModel.processIntent(intent)

    }

}
