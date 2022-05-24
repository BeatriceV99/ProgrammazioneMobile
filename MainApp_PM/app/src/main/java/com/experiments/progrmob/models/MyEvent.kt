package com.experiments.progrmob.models

import java.util.*

class MyEvent (val id : Long,
               val calId : Long,
               val title: String,
               val descr : String,
               val startDate : Date,
               val endDate: Date,
               val duration: Long,
               val allDay: Boolean,
               val isWeather: Boolean = false,
               val location: String = "") {

}