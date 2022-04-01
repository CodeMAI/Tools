package com.dk.tool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.dk.utils.SoftInputHelper



class MainActivity : AppCompatActivity() {

    lateinit var et: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        et = findViewById(R.id.et)

        findViewById<View>(R.id.tv).setOnClickListener {
//            SoftInputHelper.hideSoftInput(et)
            findViewById<View>(R.id.view).visibility = View.VISIBLE
        }

        SoftInputHelper().attachSoftInput(this, object : SoftInputHelper.OnSoftInputChangeListener{
            override fun onChanged(isShow: Boolean, height: Int) {

            }
        })
    }
}