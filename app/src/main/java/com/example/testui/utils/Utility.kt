package com.example.testui.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import java.text.SimpleDateFormat
import java.util.Locale

fun Context.formatDateTime(dateTime: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

    val date = inputFormat.parse(dateTime)
    return outputFormat.format(date)
}

fun formatmothYear(dateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())

    val date = inputFormat.parse(dateString)
    return outputFormat.format(date)
}

