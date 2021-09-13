package com.example.marketocr.service.dao

import android.content.Context
import com.example.marketocr.service.database.DatabaseHelper
import com.example.marketocr.values.Value

open class ValueDao(context: Context?) {
    var contextHelper = DatabaseHelper(context)
    val db = contextHelper.writableDatabase

    fun registerValue(value: Double, quantity: Int?){
        var result = value * quantity!!
//        var result = 0.0

         db.execSQL("INSERT INTO value(VALUE, QUANTITY, RESULT) VALUES('$value', '$quantity','$result')")

    }

    fun sumALl(): String{
        val result =  db.rawQuery("SELECT SUM(RESULT) from value", null)

        result.moveToFirst()
        return result.getString(0)
    }

    fun restart(){
        db.execSQL("DELETE FROM value")
    }
}