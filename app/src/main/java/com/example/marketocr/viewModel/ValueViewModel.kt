package com.example.marketocr.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.marketocr.repository.ValueRepository
import java.lang.Exception
import javax.inject.Inject

class ValueViewModel(application: Application): AndroidViewModel(application) {

    @Inject
    lateinit var repository: ValueRepository

    fun doInsertValue(value: String, quantity: Int?){

            repository.insertValue(value, quantity)

    }
}