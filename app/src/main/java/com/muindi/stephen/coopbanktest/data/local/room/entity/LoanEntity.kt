package com.muindi.stephen.coopbanktest.data.local.room.entity

import androidx.room.Entity

@Entity("applyloans")
data class LoanEntity (
    val id: Int =0,
    val loanType: String,
    val period: String,
    val account: String
)