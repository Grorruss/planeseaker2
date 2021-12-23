package com.example.flightarchive_2021

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * Created by sergio on 11/9/21
 * All rights reserved GoodBarber
 */
class MainViewModel : ViewModel() {

    private val airportListLiveData = MutableLiveData<List<String>>()

    private val fromCalendarLiveData = MutableLiveData<Calendar>()
    private val toCalendarLiveData = MutableLiveData<Calendar>()
    private val requestStatusLiveData = MutableLiveData<Int>()
    private var airportList: List<Airport> = Utils.generateAirportList()

    fun getAirportListNamesLiveData(): LiveData<List<String>> {
        return airportListLiveData
    }

    fun getFromCalendarLiveData(): LiveData<Calendar> {
        return fromCalendarLiveData
    }

    fun getToCalendarLiveData(): LiveData<Calendar> {
        return toCalendarLiveData
    }

    fun getRequestStatusLiveData(): LiveData<Int> {
        return requestStatusLiveData
    }

    init {
        val airportListNames = ArrayList<String>()
        for (airport in airportList) {
            airportListNames.add(airport.getFormattedName())
        }
        airportListLiveData.value = airportListNames

        fromCalendarLiveData.value = Calendar.getInstance()
        toCalendarLiveData.value = Calendar.getInstance()
        requestStatusLiveData.value = 0
    }

    fun updateCalendar(year: Int, month: Int, day: Int, isFromCalendar: Boolean) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        if (isFromCalendar)
            fromCalendarLiveData.value = calendar
        else
            toCalendarLiveData.value = calendar
    }

    fun doSearch(airportSelectedIndex: Int, isArrival: Boolean) {

        //Créer l'URL
        var url =
            if (isArrival) "https://opensky-network.org/api/flights/arrival"
            else "https://opensky-network.org/api/flights/departure"

        val airportIcao = airportList[airportSelectedIndex].icao

        val begin = fromCalendarLiveData.value!!.timeInMillis / 1000
        val end = toCalendarLiveData.value!!.timeInMillis / 1000

        url += "?airport=${airportIcao}&begin=${begin}&end=${end}"


        viewModelScope.launch {
            requestStatusLiveData.value = 1 // 1 means pending
            withContext(Dispatchers.IO) {
                val result = RequestManager.getSuspended(url, HashMap())
                //CACA: requestStatusLiveData to trigger navigation is limit limit.
                if (result != null) {
                    Log.i("RESULT", result)
                    val flightsList = Utils.convertFlightsDataIntoList(result)
                    DataHolder.flightsList = flightsList
                    requestStatusLiveData.postValue(200)
                } else {
                    requestStatusLiveData.postValue(400)
                }
            }


        }
        // stocker le résultat dans un singleton

    }
}