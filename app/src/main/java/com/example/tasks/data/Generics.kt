package com.example.tasks.data

import android.graphics.Color
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Generics {

    fun getCurrentDateTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }

    fun getPriorityColor(priority: Int): Int {
        val priorityColorMap = mapOf(
            1 to Color.parseColor("#F3C9C9"),
            2 to Color.parseColor("#96C89E"),
            3 to Color.parseColor("#FD5B5B")
        )

        return priorityColorMap[priority] ?: Color.WHITE
    }

    fun formatDateTime(dateTime: String): String {
        val sourceFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = sourceFormat.parse(dateTime)

        val targetFormat = SimpleDateFormat(getDateTimeFormat(), Locale.getDefault())
        return targetFormat.format(date)
    }

    private fun getDateTimeFormat(): String {
        val defaultFormat = "dd/MM/yyyy HH:mm:ss"
        val usFormat = "MM/dd/yyyy HH:mm:ss"

        val locale = Locale.getDefault().language
        return if (locale == "en") usFormat else defaultFormat
    }

}