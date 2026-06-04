package com.muindi.stephen.coopbanktest.presentation.loans

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muindi.stephen.coopbanktest.data.local.room.entity.LoanEntity
import com.muindi.stephen.coopbanktest.domain.repository.LoanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val loanRepository: LoanRepository
) : ViewModel() {

    val activeLoans: StateFlow<List<LoanEntity>> = loanRepository.getActiveLoans()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _showInfoNotification = MutableStateFlow(false)
    val showInfoNotification = _showInfoNotification.asStateFlow()

    fun onLoanApplyClick(loanTitle: String, onNavigate: (String) -> Unit) {
        if (activeLoans.value.isNotEmpty()) {
            viewModelScope.launch {
                _showInfoNotification.value = true
                delay(3000)
                _showInfoNotification.value = false
            }
        } else {
            onNavigate(loanTitle)
        }
    }
}
