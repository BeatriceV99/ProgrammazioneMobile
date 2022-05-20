package com.experiments.progrmob.activities

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import com.experiments.progrmob.R
import com.experiments.progrmob.models.FirebaseDbWrapper
import com.experiments.progrmob.models.MyEvent
import com.experiments.progrmob.models.addNewEvent
import java.text.SimpleDateFormat
import java.util.*

class AddEventActivity : AppCompatActivity() {
    private val TAG : String = AddEventActivity::class.simpleName.toString()
    private val myCalendar : Calendar = Calendar.getInstance()

    /*
    TODO: Possible exercises
        1. Add missing hardcoded fields in the AddEventActivity (e.g., calendar id, description)
        2. Move save routine in a different thread
        3. Handle in a better way some error -> e.g., https://www.geeksforgeeks.org/implement-form-validation-error-to-edittext-in-android/
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)

        // Add date picker
        val startDateEditText : EditText = findViewById<View>(R.id.startDateEditText) as EditText
        val endDateEditText : EditText = findViewById<View>(R.id.endDateEditText) as EditText
        for (editText: EditText in arrayOf(startDateEditText, endDateEditText)) {
            val dateListener =
                DatePickerDialog.OnDateSetListener { view, year, month, day ->
                    myCalendar.set(Calendar.YEAR, year)
                    myCalendar.set(Calendar.MONTH, month)
                    myCalendar.set(Calendar.DAY_OF_MONTH, day)

                    // Update label
                    val myFormat = "dd/MM/yy"
                    val dateFormat = SimpleDateFormat(myFormat, Locale.US)
                    editText.setText(dateFormat.format(this.myCalendar.getTime()))
                }

            editText.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?) {
                    DatePickerDialog(
                        this@AddEventActivity,
                        dateListener,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
            })
        }

        // Add time picker
        val startTimeEditText: EditText = findViewById<View>(R.id.startTimeEditText) as EditText
        val endTimeEditText: EditText = findViewById<View>(R.id.endTimeEditText) as EditText
        for (editText: EditText in arrayOf(startTimeEditText, endTimeEditText)) {
            editText.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?) {
                    val mcurrentTime = Calendar.getInstance()
                    val hour = mcurrentTime[Calendar.HOUR_OF_DAY]
                    val minute = mcurrentTime[Calendar.MINUTE]

                    TimePickerDialog(
                        this@AddEventActivity,
                        { timePicker, selectedHour, selectedMinute ->
                            editText.setText("$selectedHour:$selectedMinute")
                        }, hour, minute, true
                    ).show()
                }
            })
        }

        // Save button
        val saveButton: Button = findViewById<View>(R.id.buttonAddEvent) as Button
        val context : Context = this
        val contentResolver : ContentResolver = this.contentResolver

        saveButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val dateFormat : SimpleDateFormat = SimpleDateFormat("dd/MM/yy HH:mm")

                val startDate = dateFormat.parse("${startDateEditText.text} ${startTimeEditText.text}")
                val endDate = dateFormat.parse("${endDateEditText.text} ${endTimeEditText.text}")

                if (startDate == null || endDate == null) {
                    Log.e(TAG, "Invalid date!")
                    return
                }

                // Create myEvent
                val myEvent : MyEvent = MyEvent(
                    -1,
                    5, // TODO: Check this dummy hardcoded value in your device!
                    (findViewById<View>(R.id.titleEditText) as EditText).text.toString(),
                    "",
                    startDate,
                    endDate,
                    "",
                    false,
                    (findViewById<View>(R.id.addWeatherCheckBox) as CheckBox).isChecked,
                    (findViewById<View>(R.id.weatherLocationEditText) as EditText).text.toString()
                )

                // Save event to calendar
                val eventId : Long = addNewEvent(contentResolver, myEvent)

                FirebaseDbWrapper(context).writeDbData(FirebaseDbWrapper.Companion.FirebaseEvent(eventId, myEvent.isWeather, myEvent.location))

                // Return to the main activity
                val addEventIntent =  Intent(context, MainActivity::class.java)
                startActivity(addEventIntent)
                finish()
            }
        })

    }
}