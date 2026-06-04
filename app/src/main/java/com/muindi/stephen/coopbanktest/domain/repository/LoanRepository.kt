package com.muindi.stephen.coopbanktest.domain.repository

import com.muindi.stephen.coopbanktest.data.local.room.entity.LoanCalculationEntity
import com.muindi.stephen.coopbanktest.data.local.room.entity.LoanEntity
import kotlinx.coroutines.flow.Flow

interface LoanRepository {
    suspend fun applyLoan(loan: LoanEntity)
    fun getActiveLoans(): Flow<List<LoanEntity>>
    suspend fun saveCalculation(calculation: LoanCalculationEntity)
    fun getAllCalculations(): Flow<List<LoanCalculationEntity>>
    suspend fun getCalculationById(id: Long): LoanCalculationEntity?
}
