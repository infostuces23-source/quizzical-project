package com.example.quizapp.utils

import android.text.Html
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

fun String.decodeHtml(): String {
    return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY).toString()
}

fun String.capitalizeWords(): String {
    return this.split(" ").joinToString(" ") { word ->
        word.replaceFirstChar { it.uppercase() }
    }
}

fun Float.formatScore(): String {
    return String.format("%.1f%%", this)
}

fun Long.formatDate(): String {
    val sdf = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault())
    return sdf.format(java.util.Date(this))
}

fun AppCompatActivity.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun <T> Result<T>.getOrNull(): T? {
    return getOrNull()
}

fun <T> Result<T>.getOrThrow(): T {
    return getOrThrow()
}
