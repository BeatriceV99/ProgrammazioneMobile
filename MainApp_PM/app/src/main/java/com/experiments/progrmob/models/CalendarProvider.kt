package com.experiments.progrmob.models

import android.content.ContentResolver
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.provider.CalendarContract
import android.util.Log
import androidx.annotation.NonNull
import java.util.*
import kotlin.collections.ArrayList

private val TAG : String = "CalendarProvider"

private val EVENT_PROJECTION: Array<String> = arrayOf(
    CalendarContract.Events._ID,
    CalendarContract.Events.CALENDAR_ID,
    CalendarContract.Events.DESCRIPTION,
    CalendarContract.Events.TITLE,
    CalendarContract.Events.DTSTART,
    CalendarContract.Events.DTEND,
    CalendarContract.Events.ALL_DAY,
    CalendarContract.Events.DURATION
)

// NOTE: This is the index of the column in the SELECT query
// In our case the SELECT query retrieve the column in EVENT_PROJECTION!!
private val EVENT_COLUMN: Map<String, Int> = mapOf(
    // 1 to "x", 2 to "y", -1 to "zz"
    CalendarContract.Events._ID to 0,
    CalendarContract.Events.CALENDAR_ID to 1,
    CalendarContract.Events.DESCRIPTION to 2,
    CalendarContract.Events.TITLE to 3,
    CalendarContract.Events.DTSTART to 4,
    CalendarContract.Events.DTEND to 5,
    CalendarContract.Events.DURATION to 6,
    CalendarContract.Events.ALL_DAY to 7
)

fun getEventsOfDay(contentResolver: ContentResolver, day: Int, month: Int, year: Int) : List<MyEvent> {
    val uri: Uri = CalendarContract.Events.CONTENT_URI
    val calendar = Calendar.getInstance()
    calendar.set(year, month, day, 0, 0)
    val startMillis = calendar.timeInMillis
    calendar.add(Calendar.DAY_OF_YEAR, 1)
    val endMillis = calendar.timeInMillis

    val selection: String = "((${CalendarContract.Events.DTSTART} >= ?) AND (" +
            "${CalendarContract.Events.DTSTART} < ?)) OR ((${CalendarContract.Events.DTSTART} < ?) AND (" +
            "${CalendarContract.Events.DTEND} >= ?))"
    val selectionArgs: Array<String> = arrayOf(startMillis.toString(), endMillis.toString(), startMillis.toString(), startMillis.toString())

    val cur: Cursor =
        contentResolver.query(uri, EVENT_PROJECTION, selection, selectionArgs, null)!!

    val result : MutableList<MyEvent>  = ArrayList<MyEvent>()
    while (cur.moveToNext()) {
        // Get the field values
        val eventId: Long = cur.getLong(EVENT_COLUMN[CalendarContract.Events._ID]!!)
        val calendarId: Long = cur.getLong(EVENT_COLUMN[CalendarContract.Events.CALENDAR_ID]!!)
        val title: String = cur.getString(EVENT_COLUMN[CalendarContract.Events.TITLE]!!)
        val descr: String = cur.getString(EVENT_COLUMN[CalendarContract.Events.DESCRIPTION]!!) ?: ""
        val dtStart: Long = cur.getLong(EVENT_COLUMN[CalendarContract.Events.DTSTART]!!)
        val dtEnd: Long = cur.getLong(EVENT_COLUMN[CalendarContract.Events.DTEND]!!)
        val dur: String = cur.getString(EVENT_COLUMN[CalendarContract.Events.DURATION]!!)
        val allDay: Int = cur.getInt(EVENT_COLUMN[CalendarContract.Events.ALL_DAY]!!)

        Log.d(TAG, "event id: ${eventId} - title : ${title}" )

        result.add(MyEvent(eventId, calendarId, title, descr, Date(dtStart), Date(dtEnd), dur, allDay == 1))
    }

    Log.d(TAG, "Retrieved ${result.size} event from the content provider of the google calendar")
    return result
}

fun getAllEvents (contentResolver : ContentResolver) : List<MyEvent> {

    val uri: Uri = CalendarContract.Events.CONTENT_URI
    val cur: Cursor =
        contentResolver.query(uri, EVENT_PROJECTION, null, null, null)!!

    val result: MutableList<MyEvent> = ArrayList<MyEvent>()
    while (cur.moveToNext()) {
        // Get the field values
        val eventId: Long = cur.getLong(EVENT_COLUMN[CalendarContract.Events._ID]!!)
        val calendarId: Long = cur.getLong(EVENT_COLUMN[CalendarContract.Events.CALENDAR_ID]!!)
        val title: String = cur.getString(EVENT_COLUMN[CalendarContract.Events.TITLE]!!)
        val descr: String = cur.getString(EVENT_COLUMN[CalendarContract.Events.DESCRIPTION]!!)
        val dtStart: Long = cur.getLong(EVENT_COLUMN[CalendarContract.Events.DTSTART]!!)
        val dtEnd: Long = cur.getLong(EVENT_COLUMN[CalendarContract.Events.DTEND]!!)
        val dur: String = cur.getString(EVENT_COLUMN[CalendarContract.Events.DURATION]!!)
        val allDay: Int = cur.getInt(EVENT_COLUMN[CalendarContract.Events.ALL_DAY]!!)

        Log.d(TAG, "event id: ${eventId} - title : ${title}")

        result.add(
            MyEvent(
                eventId,
                calendarId,
                title,
                descr,
                Date(dtStart),
                Date(dtEnd),
                dur,
                allDay == 1
            )
        )
    }

    Log.d(TAG, "Retrieved ${result.size} event from the content provider of the google calendar")
    return result
}

// See: https://developer.android.com/guide/topics/providers/calendar-provider#add-event
fun addNewEvent(@NonNull contentResolver : ContentResolver, myEvent : MyEvent) : Long {
    val values = ContentValues().apply {
        put(CalendarContract.Events.DTSTART, myEvent.startDate.time)
        put(CalendarContract.Events.DTEND, myEvent.endDate.time)
        put(CalendarContract.Events.TITLE, myEvent.title)
        put(CalendarContract.Events.CALENDAR_ID, myEvent.calId)
        put(CalendarContract.Events.EVENT_TIMEZONE, Locale.ITALY.country)
    }
    val uri: Uri? = contentResolver.insert(CalendarContract.Events.CONTENT_URI, values)

    // get the event ID that is the last element in the Uri
    return uri?.lastPathSegment?.toLong() ?: -1
}