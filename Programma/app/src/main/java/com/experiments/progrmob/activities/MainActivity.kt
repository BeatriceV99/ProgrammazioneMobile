package com.experiments.progrmob.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import androidx.fragment.app.commit
import com.experiments.progrmob.R
import com.experiments.progrmob.fragments.DayListFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

// Form validation example: https://www.geeksforgeeks.org/implement-form-validation-error-to-edittext-in-android/

// pm grant <package-name> <permission>

// Example code: our secret is not secure - Hardcoded!
//        /*
//        val SECRET_KEY: String = getString(R.string.secret_key)
//
//        var str = "]HYVM"
//        val res = str.toCharArray()
//        for (i in 0..(SECRET_KEY.length-1)) {
//            res[i] = res[i].code.xor(SECRET_KEY[i].code).toChar();
//        }
//        str = String(res)
//
//        Log.d("MainActivity", "Decrypted key = ${str}")
//        */
class MainActivity : AppCompatActivity() {
    private val TAG : String? = MainActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        });

        val context : Context = this
        val addButton : FloatingActionButton = findViewById(R.id.addButton)
        addButton.setOnClickListener( object : View.OnClickListener {
            override fun onClick(v: View?) {
                val addEventIntent = Intent(context, AddEventActivity::class.java)
                startActivity(addEventIntent)
                finish()
            }
        });

    }
}
