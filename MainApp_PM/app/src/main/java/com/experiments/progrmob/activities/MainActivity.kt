package com.experiments.progrmob.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CalendarView
import androidx.fragment.app.commit
import com.experiments.progrmob.R
import com.experiments.progrmob.fragments.DayListFragment

class MainActivity : AppCompatActivity() {
    private val TAG : String? = MainActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Connect to content provider

        // Create Fragment on https://developer.android.com/reference/android/widget/CalendarView#setOnDateChangeListener(android.widget.CalendarView.OnDateChangeListener)

        val fragmentManager = this.getSupportFragmentManager()
        val calendarView : CalendarView = findViewById(R.id.calendarView)
        calendarView.setOnDateChangeListener(object : CalendarView.OnDateChangeListener {
            override fun onSelectedDayChange(
                view: CalendarView,
                year: Int,
                month: Int,
                dayOfMonth: Int
            ) {
                // Create the fragment
                fragmentManager.commit {
                    setReorderingAllowed(true)

                    val frag = DayListFragment.newInstance(dayOfMonth, month, year)
                    this.replace(R.id.fragmentContainerView, frag)
                }
            }

        })
    }
}