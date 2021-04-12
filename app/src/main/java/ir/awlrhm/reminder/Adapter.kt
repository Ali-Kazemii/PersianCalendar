package ir.awlrhm.reminder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item.view.*

class Adapter(
    private val list: MutableList<Model>,
    private val callback: (model: Model) -> Unit
): RecyclerView.Adapter<Adapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) =
        holder.onBind(list[position])

    override fun getItemCount(): Int = list.size

    inner class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun onBind(model: Model){
            itemView.txtDayNumber.text = model.dayNumber
            itemView.txtDayName.text = model.dayName
            itemView.txtMonthName.text = model.monthName
            itemView.txtDescription.text = model.description
            itemView.setOnClickListener {
                callback.invoke(model)
            }
        }
    }
}