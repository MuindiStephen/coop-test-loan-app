package com.muindi.stephen.coopbanktest.presentation.utils

import com.muindi.stephen.coopbanktest.domain.models.loan.AmortizationScheduleItem
import kotlin.math.pow

object LoanCalculator {

    fun calculateEMI(principal: Double, annualRate: Double, tenureInMonths: Int): Double {
        if (principal <= 0 || annualRate <= 0 || tenureInMonths <= 0) return 0.0
        val monthlyRate = annualRate / (12 * 100)
        return (principal * monthlyRate * (1 + monthlyRate).pow(tenureInMonths.toDouble())) /
                ((1 + monthlyRate).pow(tenureInMonths.toDouble()) - 1)
    }

    fun calculateAmortizationSchedule(
        principal: Double,
        annualRate: Double,
        tenureInMonths: Int
    ): List<AmortizationScheduleItem> {
        val emi = calculateEMI(principal, annualRate, tenureInMonths)
        val monthlyRate = annualRate / (12 * 100)
        val schedule = mutableListOf<AmortizationScheduleItem>()
        var remainingBalance = principal

        for (i in 1..tenureInMonths) {
            val interestComponent = remainingBalance * monthlyRate
            val principalComponent = emi - interestComponent
            remainingBalance -= principalComponent
            
            schedule.add(
                AmortizationScheduleItem(
                    paymentNumber = i,
                    emi = emi,
                    interestComponent = interestComponent,
                    principalComponent = principalComponent,
                    remainingBalance = if (remainingBalance < 0) 0.0 else remainingBalance
                )
            )
        }
        return schedule
    }
}
