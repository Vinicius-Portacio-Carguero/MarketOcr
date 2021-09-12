package com.example.marketocr.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.marketocr.repository.ValueRepository
import com.example.marketocr.service.dao.ValueDao
import java.lang.Exception
import javax.inject.Inject

class ValueViewModel(application: Application): AndroidViewModel(application)
{

    var repository = ValueRepository()

    fun doInsertValue(value: String, quantity: Int?, context: Context?)
    {
            repository.insertValue(value, quantity, context)
    }

    fun sumAll(context: Context?): String = repository.sumAll(context)
    fun reset(context: Context?) = repository.reset(context)


}