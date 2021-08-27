package com.example.marketocr.utils.camera

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Permission(context: Context?) {

     fun requestPermissionCamera(context: Context?, activity: Activity){

        if(context?.let { ContextCompat.checkSelfPermission(it, android.Manifest.permission.CAMERA) }
            != PackageManager.PERMISSION_GRANTED){

            if(activity?.let { ActivityCompat.shouldShowRequestPermissionRationale(it, android.Manifest.permission.CAMERA) } == true){

            }else{
                activity?.let { ActivityCompat.requestPermissions(it,
                    arrayOf(android.Manifest.permission.CAMERA),
                    101) }
            }
        }

    }

     fun permissionCheck(context: Context?): Int?{

        val checkPermission = context?.let { ContextCompat.checkSelfPermission(it, android.Manifest.permission.CAMERA) }

        if(checkPermission == PackageManager.PERMISSION_DENIED){
            println("Permissão negada")
        } else{
            println("O device já possui permissão de camera")
        }

        return checkPermission
    }
}