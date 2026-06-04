package com.muindi.stephen.coopbanktest.presentation.applyloan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muindi.stephen.coopbanktest.data.local.room.entity.LoanEntity
import com.muindi.stephen.coopbanktest.domain.repository.LoanRepository
import com.muindi.stephen.coopbanktest.presentation.utils.LoanCalculator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ApplyLoanViewModel @Inject constructor(
    private val loanRepository: LoanRepository
) : ViewModel() {
    private val _loanType = MutableStateFlow("Salary E-Loan")
    val loanType = _loanType.asStateFlow()

    private val _loanAmount = MutableStateFlow("10,000.00")
    val loanAmount = _loanAmount.asStateFlow()

    private val _loanPeriod = MutableStateFlow("2")
    val loanPeriod = _loanPeriod.asStateFlow()

    private val _accountNumber = MutableStateFlow("011090145246100")
    val accountNumber = _accountNumber.asStateFlow()

    // Assuming a fixed annual interest rate of 15% for now as per UI "15% p.a"
    private val annualInterestRate = 15.0

    val calculatedValues: StateFlow<LoanCalculationResult> = combine(
        _loanAmount,
        _loanPeriod
    ) { amountStr, periodStr ->
        val principal = amountStr.replace(",", "").toDoubleOrNull() ?: 0.0
        val months = periodStr.toIntOrNull() ?: 0

        if (principal > 0 && months > 0) {
            val emi = LoanCalculator.calculateEMI(principal, annualInterestRate, months)
            val totalAmountPayable = emi * months
            val totalInterest = totalAmountPayable - principal
            
            LoanCalculationResult(
                interest = String.format(Locale.US, "%,.2f", totalInterest),
                totalCharges = String.format(Locale.US, "%,.2f", totalAmountPayable),
                emi = String.format(Locale.US, "%,.2f", emi),
                nextRepaymentDate = "22 Oct 2025" // Mocked date for now
            )
        } else {
            LoanCalculationResult()
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = LoanCalculationResult()
    )

    fun updateLoanType(type: String) {
        _loanType.value = type
    }

    fun updateLoanAmount(amount: String) {
        // Clean input to allow only digits and one dot
        val cleaned = amount.filter { it.isDigit() || it == '.' }
        _loanAmount.value = cleaned
    }

    fun updateLoanPeriod(period: String) {
        _loanPeriod.value = period
    }

    fun updateAccountNumber(account: String) {
        _accountNumber.value = account
    }

    fun confirmLoanApplication(onSuccess: () -> Unit) {
        val result = calculatedValues.value
        viewModelScope.launch {
            val entity = LoanEntity(
                loanType = _loanType.value,
                loanAmount = _loanAmount.value,
                balance = result.totalCharges,
                monthlyPayment = result.emi,
                interest = result.interest,
                period = _loanPeriod.value,
                account = _accountNumber.value,
                nextRepaymentDate = result.nextRepaymentDate
            )
            loanRepository.applyLoan(entity)
            onSuccess()
        }
    }
}

data class LoanCalculationResult(
    val interest: String = "0.00",
    val totalCharges: String = "0.00",
    val emi: String = "0.00",
    val nextRepaymentDate: String = "N/A"
)
