package com.example.flightarchive_2021

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by sergio on 11/10/21
 * All rights reserved GoodBarber
 */
class FlightsRecyclerAdapter(
    private val flightList: List<FlightModel>,
    private val onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<FlightsRecyclerAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    interface OnItemClickListener {
        fun onItemClicked(selectedFlight: FlightModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(FlightCell(parent.context))
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val flightCell = holder.itemView as FlightCell
        flightCell.bindData(flightList[position])
        flightCell.setOnClickListener { onItemClickListener.onItemClicked(flightList[position]) }
    }

    override fun getItemCount(): Int {
        return flightList.size
    }
}