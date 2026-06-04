package com.muindi.stephen.coopbanktest.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "loan_calculations")
data class LoanCalculationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val principal: Double,
    val interestRate: Double,
    val tenure: Int,
    val isMonths: Boolean,
    val emi: Double,
    val totalInterest: Double,
    val totalAmount: Double,
    val timestamp: Long = System.currentTimeMillis()
)
