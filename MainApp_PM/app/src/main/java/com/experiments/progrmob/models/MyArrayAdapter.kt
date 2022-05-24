package com.experiments.progrmob.models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.experiments.progrmob.R

class MyArrayAdapter (context: Context, val resource: Int, val events: List<MyEvent>) : ArrayAdapter<MyEvent>(context, resource, events) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val event : MyEvent = events.get(position)
        var view : View? = convertView

        // NOTE: The view iterate over all elements of the events list
        //      I have to istantiate only the first time (and for the other, we reuse the 'convertView' variable)
        if (view == null) {
            view =
                LayoutInflater.from(context).inflate(R.layout.my_event_layout, parent, false)
        }

        val eventTitle: TextView = view!!.findViewById<TextView>(R.id.eventTitle)
        eventTitle.setText(event.title)

        val eventStartTime: TextView = view.findViewById(R.id.eventStartTime)
        eventStartTime.setText(event.formattedStartDate)

        val eventEndTime: TextView = view.findViewById(R.id.eventEndTime)
        eventEndTime.setText(event.formattedEndDate)

        return view
    }
}