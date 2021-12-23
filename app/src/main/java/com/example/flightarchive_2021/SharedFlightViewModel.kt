package com.example.flightarchive_2021

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by sergio on 11/10/21
 * All rights reserved GoodBarber
 */
class SharedFlightViewModel : ViewModel() {
    private val selectedFlightLiveData = MutableLiveData<FlightModel>()

    fun getSelectedFlightLiveData(): LiveData<FlightModel> {
        return selectedFlightLiveData
    }

    fun updateSelectedFlight(flight: FlightModel) {
        selectedFlightLiveData.value = flight
    }
}