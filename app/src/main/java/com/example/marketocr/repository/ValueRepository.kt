package com.example.marketocr.repository

import android.content.Context
import com.example.marketocr.service.dao.ValueDao
import javax.inject.Inject

class ValueRepository{

    fun insertValue(value: String, quantity: Int?, context: Context?) {
        val dao = ValueDao(context)

        dao.registerValue(value, quantity)
    }

    fun sumAll(context: Context?): String {

        return ValueDao(context).sumALl()

    }

    fun reset(context: Context?){
        ValueDao(context).restart()
    }
}