package com.muindi.stephen.coopbanktest.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "applyloans")
data class LoanEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val loanType: String,
    val loanAmount: String,
    val balance: String,
    val monthlyPayment: String,
    val interest: String,
    val period: String,
    val account: String,
    val nextRepaymentDate: String,
    val status: String = "ACTIVE"
)
