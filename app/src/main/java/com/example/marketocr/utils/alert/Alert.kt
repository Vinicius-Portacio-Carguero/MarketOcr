package com.example.marketocr.utils.alert

import android.app.AlertDialog
import android.content.Context

open class Alert {

    open fun showDialog(title: String, context: Context?): AlertDialog.Builder{

        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)

        return builder
    }
}