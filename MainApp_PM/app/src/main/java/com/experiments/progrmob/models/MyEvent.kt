package com.experiments.progrmob.models

import java.text.SimpleDateFormat
import java.util.*

// See: https://github.com/kizitonwose/CalendarView
class MyEvent (val id : Long,
               val calId : Long,
               val title: String,
               val descr : String,
               val startDate : Date,
               val endDate: Date,
               val duration: String,
               val allDay: Boolean,
               var isWeather: Boolean = false,
               var location: String = "") {
    private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)

    val formattedStartDate :String? get() : String? {
        return formatter.format(this.startDate)
    }

    val formattedEndDate :String? get() : String? {
        return formatter.format(this.endDate)
    }

}