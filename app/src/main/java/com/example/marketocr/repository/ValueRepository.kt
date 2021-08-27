package com.example.marketocr.repository

import com.example.marketocr.service.dao.ValueDao
import javax.inject.Inject

class ValueRepository @Inject constructor(private val dao: ValueDao){

    fun insertValue(value: String, quantity: Int?) =
        dao.registerValue(value, quantity)
}