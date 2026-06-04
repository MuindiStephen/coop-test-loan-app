package com.muindi.stephen.coopbanktest.data.repository

import com.muindi.stephen.coopbanktest.data.local.room.dao.LoanDao
import com.muindi.stephen.coopbanktest.data.local.room.entity.LoanCalculationEntity
import com.muindi.stephen.coopbanktest.data.local.room.entity.LoanEntity
import com.muindi.stephen.coopbanktest.domain.repository.LoanRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoanRepositoryImpl @Inject constructor(
    private val loanDao: LoanDao
) : LoanRepository {
    override suspend fun applyLoan(loan: LoanEntity) {
        loanDao.applyLoan(loan)
    }

    override fun getActiveLoans(): Flow<List<LoanEntity>> {
        return loanDao.getActiveLoans()
    }

    override suspend fun saveCalculation(calculation: LoanCalculationEntity) {
        loanDao.saveCalculation(calculation)
    }

    override fun getAllCalculations(): Flow<List<LoanCalculationEntity>> {
        return loanDao.getAllCalculations()
    }

    override suspend fun getCalculationById(id: Long): LoanCalculationEntity? {
        return loanDao.getCalculationById(id)
    }
}
