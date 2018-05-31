package com.app.carparkregister.utils

import android.graphics.Color
import android.net.ConnectivityManager
import java.util.*

class CommonUtils {

    fun checkInternetConnection(systemService: ConnectivityManager): Boolean {
        val activeNetworkInfo = systemService.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun getCurrentDay(): Int {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_WEEK)
        return day
    }

}