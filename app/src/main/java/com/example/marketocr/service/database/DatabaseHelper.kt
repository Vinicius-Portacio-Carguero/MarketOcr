package com.example.marketocr.service.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context?) : SQLiteOpenHelper(context, "VALUES", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE value(ID INTEGER PRIMARY KEY AUTOINCREMENT, VALUE DOUBLE, QUANTITY INT, RESULT DOUBLE)")
        db?.execSQL("INSERT INTO value(VALUE, QUANTITY, RESULT) VALUES (0.0, 0, 0.0)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS value")

        onCreate(db)
    }
}