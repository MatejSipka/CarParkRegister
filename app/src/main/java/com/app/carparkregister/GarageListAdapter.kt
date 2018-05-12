package com.app.carparkregister


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.carparkregister.domain.CarDao
import kotlinx.android.synthetic.main.user_garage_list_item.view.*

class GarageListAdapter(private val dataSource: ArrayList<CarDao>) : RecyclerView.Adapter<GarageListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): GarageListAdapter.ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.user_garage_list_item, parent, false)
        var viewHolder = ViewHolder(v)
//        viewHolder.bindItems(dataSource[viewType])
        return viewHolder
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

//    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
////        val rowView = inflater.inflate(R.layout.user_garage_list_item, parent, false)
//        val view: View
//        val holder: ViewHolder
//
//        if (convertView == null) {
//            view = inflater.inflate(R.layout.user_garage_list_item, parent, false)
//            holder = ViewHolder()
//            holder.carModel = view.findViewById<TextView>(R.id.car_model) as TextView
//            holder.carPlate = view.findViewById<TextView>(R.id.car_plate) as TextView
//            holder.carColor = view.findViewById<TextView>(R.id.car_color) as TextView
//            view.tag = holder
//        } else {
//            view = convertView
//            holder = convertView.tag as ViewHolder
//        }
//
//        val carModel = holder.carModel
//        val carPlate = holder.carPlate
//        val carColor = holder.carColor
//
//        val car = getItem(position) as CarDao
//
//        carModel.text = car.model
//        carPlate.text = car.spz
//        carColor.text = car.color
//
//        view.setOnTouchListener(OnSwipeTouchListener(context))
//
//        return view
//    }
//
//    override fun getItem(p0: Int): Any {
//        return dataSource[p0]
//    }
//
//    override fun getItemId(p0: Int): Long {
//        return p0.toLong()
//    }
//
//    override fun getCount(): Int {
//        return dataSource.size
//    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(car: CarDao) {
            itemView.car_model.text = car.model
            itemView.car_color.text = car.color
            itemView.car_plate.text = car.spz

        }
    }

}