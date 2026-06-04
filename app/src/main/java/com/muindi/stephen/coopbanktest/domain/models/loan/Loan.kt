package com.muindi.stephen.coopbanktest.domain.models.loan

import androidx.compose.ui.graphics.Color

data class Loan(
    val title: String,
    val description: String,
    val imageRes: Int,
    val color : Color
)
