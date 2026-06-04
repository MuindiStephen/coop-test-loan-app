package com.muindi.stephen.coopbanktest.presentation.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateUtils {
    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentFormattedDate(): String {
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        return LocalDate.now().format(formatter)
    }
}
