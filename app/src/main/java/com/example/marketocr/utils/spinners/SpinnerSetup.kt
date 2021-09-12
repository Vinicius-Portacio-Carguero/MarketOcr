package com.example.marketocr.utils.spinners

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.marketocr.R

class SpinnerSetup {

    fun spinnerSetup(spinner: Spinner?, list: MutableList<Int>, context: Context){

//        val spinner = view?.findViewById<Spinner>(R.id.spinner)
        spinner?.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, list)

        spinner?.onItemSelectedListener = object : AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                list[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {}

        }
    }
}