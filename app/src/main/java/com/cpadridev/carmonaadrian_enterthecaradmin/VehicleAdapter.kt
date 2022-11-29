package com.cpadridev.carmonaadrian_enterthecaradmin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cpadridev.carmonaadrian_enterthecaradmin.model.Vehicle

class VehicleAdapter : RecyclerView.Adapter<VehicleAdapter.MyViewHolder>() {
    private var list: ArrayList<Vehicle> = ArrayList()
    private var listener: View.OnClickListener? = null

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txvId: TextView
        val txvType: TextView
        val txvPrice: TextView

        init {
            txvId = view.findViewById(R.id.txvId)
            txvType = view.findViewById(R.id.txvType)
            txvPrice = view.findViewById(R.id.txvPrice)
        }
    }

    // Creamos nuevas views inflando el layout "elementos_lista"
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.elements_list, viewGroup, false)

        view.setOnClickListener(listener)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        viewHolder.txvId.text = list[position].id.toString()
        viewHolder.txvType.text = list[position].type
        viewHolder.txvPrice.text = list[position].price.toString()
    }

    override fun getItemCount() = list.size

    fun setOnItemClickListerner(onClickListener: View.OnClickListener){
        listener = onClickListener
    }

    fun getItem(pos: Int) = list[pos]

    fun addToList(list_: ArrayList<Vehicle>){
        list.clear()
        list.addAll(list_)

        notifyDataSetChanged()
    }

    fun addToList(vehicle: Vehicle){
        list.add(vehicle)

        notifyDataSetChanged()
    }

    fun updateList(pos: Int, vehicle: Vehicle){
        list.set(pos, vehicle)

        notifyDataSetChanged()
    }

    fun deleteFromList(pos: Int){
        list.removeAt(pos)

        notifyDataSetChanged()
    }
}