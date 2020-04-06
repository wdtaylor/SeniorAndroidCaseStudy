package tov.com.seniorandroidcasestudy.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_database_list.*
import org.koin.android.viewmodel.ext.android.viewModel
import tov.com.seniorandroidcasestudy.R
import tov.com.seniorandroidcasestudy.data.model.MarketingLogData
import tov.com.seniorandroidcasestudy.viewmodel.DatabaseListViewModel

class DatabaseListActivity : AppCompatActivity(){

    private val viewModel: DatabaseListViewModel by viewModel()

    private lateinit var viewAdapter: MyAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database_list)

        viewManager = LinearLayoutManager(this)
        viewAdapter = MyAdapter()

        btn_database_content_list.apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }

        viewModel.liveDataDatabaseContent.observe(this, Observer { allDatabaseContent ->
            viewAdapter.updateDataset(allDatabaseContent)
        })

        viewModel.getAllDatabaseContent()

    }

    class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

        private var myDataset: List<MarketingLogData>? = null

        fun updateDataset(dataset: List<MarketingLogData>){
            myDataset = dataset
            notifyDataSetChanged()
        }

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder.
        // Each data item is just a string in this case that is shown in a TextView.
        class MyViewHolder(parentLayout: LinearLayout) : RecyclerView.ViewHolder(parentLayout) {
            val devideId : TextView = parentLayout.findViewById(R.id.deviceId)
        }

        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): MyViewHolder {
            // create a new view
            val parentLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.data_content_row, parent, false) as LinearLayout
            return MyViewHolder(parentLayout)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element

            val marketingLogData = myDataset?.get(position)
            val marketingLogDataJson = Gson().toJson(marketingLogData)

            holder.devideId.text =marketingLogDataJson ?: ""
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = myDataset?.size ?: 0
    }


}