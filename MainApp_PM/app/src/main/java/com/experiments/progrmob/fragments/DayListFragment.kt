package com.experiments.progrmob.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.ListView
import com.experiments.progrmob.R
import com.experiments.progrmob.models.MyArrayAdapter
import com.experiments.progrmob.models.MyEvent
import com.experiments.progrmob.models.getEventsOfDay


/**
 * A simple [Fragment] subclass.
 * Use the [DayListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DayListFragment : Fragment() {
    private var day: Int? = null
    private var month: Int? = null
    private var year: Int? = null
    private var events: List<MyEvent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            day = it.getInt("day")
            month = it.getInt("month")
            year = it.getInt("year")
        }

        // TODO: retrieve list of events
        this.events = getEventsOfDay(this.requireActivity().contentResolver, day!!, month!!, year!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_day_list, container, false)

        val eventList: ListView = view.findViewById(R.id.eventList)
        if (this.events != null) {
            val adapter: ListAdapter = MyArrayAdapter(this.requireActivity(), 0, this.events!!)
            eventList.setAdapter(adapter as ListAdapter)
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(/*events : List<MyEvent>*/day: Int, month: Int, year: Int) =
            DayListFragment().apply {
                arguments = Bundle().apply {
                    putInt("day", day)
                    putInt("month", month)
                    putInt("year", year)
                    // putString(ARG_PARAM2, param2)
                    // putString(ARG_PARAM1, param1)
                }
            }
    }
}