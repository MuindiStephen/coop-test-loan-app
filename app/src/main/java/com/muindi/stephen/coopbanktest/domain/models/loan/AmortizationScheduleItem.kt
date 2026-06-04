package com.muindi.stephen.coopbanktest.domain.models.loan

data class AmortizationScheduleItem(
    val paymentNumber: Int,
    val emi: Double,
    val interestComponent: Double,
    val principalComponent: Double,
    val remainingBalance: Double
)
