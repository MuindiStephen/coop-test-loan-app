package com.muindi.stephen.coopbanktest.presentation.calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muindi.stephen.coopbanktest.data.local.room.entity.LoanCalculationEntity
import com.muindi.stephen.coopbanktest.domain.models.loan.AmortizationScheduleItem
import com.muindi.stephen.coopbanktest.domain.repository.LoanRepository
import com.muindi.stephen.coopbanktest.presentation.utils.LoanCalculator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoanCalculatorViewModel @Inject constructor(
    private val loanRepository: LoanRepository
) : ViewModel() {

    private val _principal = MutableStateFlow("")
    val principal = _principal.asStateFlow()

    private val _interestRate = MutableStateFlow("")
    val interestRate = _interestRate.asStateFlow()

    private val _tenure = MutableStateFlow("")
    val tenure = _tenure.asStateFlow()

    private val _isMonths = MutableStateFlow(true)
    val isMonths = _isMonths.asStateFlow()

    private val _emi = MutableStateFlow(0.0)
    val emi = _emi.asStateFlow()

    private val _totalInterest = MutableStateFlow(0.0)
    val totalInterest = _totalInterest.asStateFlow()

    private val _totalAmount = MutableStateFlow(0.0)
    val totalAmount = _totalAmount.asStateFlow()

    private val _amortizationSchedule = MutableStateFlow<List<AmortizationScheduleItem>>(emptyList())
    val amortizationSchedule = _amortizationSchedule.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _saveSuccess = MutableStateFlow(false)
    val saveSuccess = _saveSuccess.asStateFlow()

    val savedCalculations: StateFlow<List<LoanCalculationEntity>> = loanRepository.getAllCalculations()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onPrincipalChange(value: String) {
        if (value.length <= 12) {
            _principal.value = value.filter { it.isDigit() || it == '.' }
            calculate()
        }
    }

    fun onInterestRateChange(value: String) {
        if (value.length <= 5) {
            _interestRate.value = value.filter { it.isDigit() || it == '.' }
            calculate()
        }
    }

    fun onTenureChange(value: String) {
        if (value.length <= 3) {
            _tenure.value = value.filter { it.isDigit() }
            calculate()
        }
    }

    fun onTenureUnitChange(isMonths: Boolean) {
        _isMonths.value = isMonths
        calculate()
    }

    private fun calculate() {
        val p = _principal.value.toDoubleOrNull() ?: 0.0
        val r = _interestRate.value.toDoubleOrNull() ?: 0.0
        val t = _tenure.value.toIntOrNull() ?: 0
        val tenureInMonths = if (_isMonths.value) t else t * 12

        if (p > 0 && r > 0 && tenureInMonths > 0) {
            val emiValue = LoanCalculator.calculateEMI(p, r, tenureInMonths)
            _emi.value = emiValue
            val totalAmt = emiValue * tenureInMonths
            _totalAmount.value = totalAmt
            _totalInterest.value = totalAmt - p
            _amortizationSchedule.value = LoanCalculator.calculateAmortizationSchedule(p, r, tenureInMonths)
        } else {
            _emi.value = 0.0
            _totalAmount.value = 0.0
            _totalInterest.value = 0.0
            _amortizationSchedule.value = emptyList()
        }
    }

    fun saveCalculation() {
        val p = _principal.value.toDoubleOrNull()
        val r = _interestRate.value.toDoubleOrNull()
        val t = _tenure.value.toIntOrNull()

        if (p == null || r == null || t == null || p <= 0 || r <= 0 || t <= 0) {
            _error.value = "Please enter valid loan details"
            return
        }
        
        viewModelScope.launch {
            try {
                loanRepository.saveCalculation(
                    LoanCalculationEntity(
                        principal = p,
                        interestRate = r,
                        tenure = t,
                        isMonths = _isMonths.value,
                        emi = _emi.value,
                        totalInterest = _totalInterest.value,
                        totalAmount = _totalAmount.value
                    )
                )
                _saveSuccess.value = true
            } catch (e: Exception) {
                _error.value = "Failed to save calculation"
            }
        }
    }

    fun loadCalculation(calc: LoanCalculationEntity) {
        _principal.value = calc.principal.toString()
        _interestRate.value = calc.interestRate.toString()
        _tenure.value = calc.tenure.toString()
        _isMonths.value = calc.isMonths
        calculate()
    }

    fun clearStatus() {
        _error.value = null
        _saveSuccess.value = false
    }
}
