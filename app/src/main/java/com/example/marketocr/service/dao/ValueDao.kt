package com.example.marketocr.service.dao

import android.content.Context
import com.example.marketocr.service.database.DatabaseHelper

class ValueDao(context: Context) {
    var contextHelper = DatabaseHelper(context)

    fun registerValue(value: String, quantity: Int?){

        val db = contextHelper.writableDatabase
         db.execSQL("INSERT INTO value(VALUE, QUANTITY) VALUES('$value', '$quantity')")


    }
}