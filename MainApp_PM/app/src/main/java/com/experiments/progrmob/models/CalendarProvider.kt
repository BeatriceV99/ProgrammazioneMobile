package com.experiments.progrmob.models

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.CalendarContract
import android.util.Log
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

private val EVENT_COLUMN: Map<String, Int> = mapOf(
    // 1 to "x", 2 to "y", -1 to "zz"
    CalendarContract.Events._ID to 0,
    CalendarContract.Events.CALENDAR_ID to 1,
    CalendarContract.Events.TITLE to 3,
    CalendarContract.Events.DESCRIPTION to 5,
    CalendarContract.Events.DTSTART to 6,
    CalendarContract.Events.DTEND to 7,
    CalendarContract.Events.DURATION to 10,
    CalendarContract.Events.ALL_DAY to 11
)


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
        val dur: Long = cur.getLong(EVENT_COLUMN[CalendarContract.Events.DURATION]!!)
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