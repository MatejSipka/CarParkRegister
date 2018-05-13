package com.app.carparkregister


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.app.carparkregister.domain.CarDao
import kotlinx.android.synthetic.main.user_garage_list_item.view.*

class GarageListAdapter(private val dataSource: ArrayList<CarDao>) : RecyclerView.Adapter<GarageListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): GarageListAdapter.ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.user_garage_list_item, parent, false)
        var viewHolder = ViewHolder(v)

        return viewHolder.listen { pos, type ->
            val item = dataSource.get(pos)
            Toast.makeText(parent?.context, item.spz, Toast.LENGTH_LONG).show()
        }
    }

    fun getItemAtPosition(position: Int): CarDao {
        return dataSource[position]
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        if (holder != null) {
            holder.bindItems(dataSource[position])
        }
    }

    fun removeAt(position: Int) {
        dataSource.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getListData(): ArrayList<CarDao> {
        return dataSource
    }

    fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
        itemView.setOnClickListener {
            event.invoke(getAdapterPosition(), getItemViewType())
        }
        return this
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(car: CarDao) {
            itemView.car_model.text = car.model
            itemView.car_color.text = car.color
            itemView.car_plate.text = car.spz

        }
    }

}