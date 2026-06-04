package com.muindi.stephen.coopbanktest.data.local.room.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.muindi.stephen.coopbanktest.data.local.room.entity.LoanEntity

interface LoanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun applyLoan(loan: List<LoanEntity>)
}