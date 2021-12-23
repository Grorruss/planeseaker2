package com.example.flightarchive_2021

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

/**
 * Created by sergio on 11/10/21
 * All rights reserved GoodBarber
 */
class FlightCell : RelativeLayout {

    var callSignTextView: TextView? = null

    constructor(context: Context?) : super(context) {
        initLayout()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initLayout()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initLayout()
    }

    private fun bindViews() {
        // make the find view by ids for your view
        callSignTextView = findViewById(R.id.textview_callsign)
    }

    //CACA: c'est pas bien de traiter de la donnée dans une vue
    fun bindData(flight: FlightModel) {
        //fill your views
        callSignTextView?.text = flight.callsign

    }

    private fun initLayout() {
        LayoutInflater.from(context).inflate(R.layout.flightcell_view, this, true)
        bindViews()
    }
}