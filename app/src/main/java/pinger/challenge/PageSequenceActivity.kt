package pinger.challenge

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import pinger.challenge.networking.NetworkTransactions
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class PageSequenceActivity : AppCompatActivity(), PageSequenceContract.View {
    private lateinit var adapter: PageSequenceAdapter
    private lateinit var presenter: PageSequenceContract.Presenter

    override fun setupPresenter(presenter: PageSequenceContract.Presenter) {
        this.presenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        PageSequencePresenter(this, NetworkTransactions())
        setupViews()
    }

    override fun onResume() {
        super.onResume()
        presenter.fetchMostPopularPathSequences()
    }

    private fun setupViews() {
        repeated_path_list.layoutManager = LinearLayoutManager(this)

        fab.setOnClickListener {
            presenter.fetchMostPopularPathSequences()
        }
    }

    override fun changeProgressBarVisibility(visible: Boolean) {
        loading_view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun updatePathSequenceList(pageSequenceData: List<Pair<String, Int>>) {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
            adapter = PageSequenceAdapter(pageSequenceData, this@PageSequenceActivity)
            repeated_path_list.adapter = adapter
        }
    }

    override fun showErrorMessage(message: String) {
        Snackbar.make(parent_layout, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        presenter.cleanUp()
        super.onDestroy()
    }
}
