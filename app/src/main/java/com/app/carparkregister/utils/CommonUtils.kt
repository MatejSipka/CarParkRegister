package com.app.carparkregister.utils

import android.net.ConnectivityManager

class CommonUtils() {

    fun checkInternetConnection(systemService: ConnectivityManager): Boolean {
        val activeNetworkInfo = systemService.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

}