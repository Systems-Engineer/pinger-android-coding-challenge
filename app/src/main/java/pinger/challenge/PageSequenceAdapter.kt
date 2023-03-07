package pinger.challenge

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.repeated_path_item.view.*

class PageSequenceAdapter(
    private val items: List<Pair<String, Int>>,
    private val context: Context
) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.repeated_path_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(items[position])
    }
}

class ViewHolder(private var view: View) : RecyclerView.ViewHolder(view) {
    fun bindData(data: Pair<String, Int>) {
        view.repeated_label.text = view.context.resources.getQuantityString(
            R.plurals.repeated_string,
            data.second,
            data.second
        )
        view.repeated_path.text = data.first
    }
}
