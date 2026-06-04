package com.muindi.stephen.coopbanktest.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.muindi.stephen.coopbanktest.data.local.room.entity.LoanCalculationEntity
import com.muindi.stephen.coopbanktest.data.local.room.entity.LoanEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LoanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun applyLoan(loan: List<LoanEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCalculation(calculation: LoanCalculationEntity)

    @Query("SELECT * FROM loan_calculations ORDER BY timestamp DESC")
    fun getAllCalculations(): Flow<List<LoanCalculationEntity>>

    @Query("SELECT * FROM loan_calculations WHERE id = :id")
    suspend fun getCalculationById(id: Long): LoanCalculationEntity?
}
