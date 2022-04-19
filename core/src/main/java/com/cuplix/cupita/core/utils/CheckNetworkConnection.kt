package com.cuplix.cupita.core.utils

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings
import com.cuplix.cupita.core.R
import com.geniusforapp.fancydialog.FancyAlertDialog


class CheckNetworkConnection(internal var ctx: Context) {


    private var result = false

    private val isInternetConnected: Boolean
        get() {
            val cm = ctx.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cm.run {
                    cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                        result = when {
                            hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                            hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                            hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                            else -> false
                        }
                    }
                }
            } else {
                cm.run {
                    cm.activeNetworkInfo?.run {
                        if (type == ConnectivityManager.TYPE_WIFI) {
                            result = true
                        } else if (type == ConnectivityManager.TYPE_MOBILE) {
                            result = true
                        }
                    }
                }
            }
            return result

        }


    fun checkConnection() {

        if (!isInternetConnected) {

            val alert = FancyAlertDialog.Builder(ctx)
                .setBackgroundColor(R.color.white)
                .setimageResource(R.drawable.ic_baseline_wifi_off_24)
                .setTextTitle(R.string.app_name)
                .setTextSubTitle(R.string.checkconnection)
                .setBody(R.string.noconnection)
                .setPositiveButtonText(R.string.ok)
                .setPositiveColor(R.color.colorPrimary)
                .setOnPositiveClicked { view, dialog ->
                    if (isInternetConnected) {
                        dialog.dismiss()

                    } else {

                        val dialogIntent = Intent(Settings.ACTION_SETTINGS)
                        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        ctx.startActivity(dialogIntent)
                    }
                }
                .setBodyGravity(FancyAlertDialog.TextGravity.CENTER)
                .setTitleGravity(FancyAlertDialog.TextGravity.CENTER)
                .setSubtitleGravity(FancyAlertDialog.TextGravity.CENTER)
                .setCancelable(false)
                .build()
            alert.show()
        }
    }
}