package com.experiments.progrmob.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.experiments.progrmob.R

class MainActivity : AppCompatActivity() {
    private val TAG : String? = MainActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Connect to content provider
    }
}