package com.muindi.stephen.coopbanktest.data.local.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muindi.stephen.coopbanktest.data.local.room.dao.LoanDao
import com.muindi.stephen.coopbanktest.data.local.room.entity.LoanCalculationEntity
import com.muindi.stephen.coopbanktest.data.local.room.entity.LoanEntity

@Database(
    entities = [LoanEntity::class, LoanCalculationEntity::class],
    version = 6,
    exportSchema = false
)
abstract class LoanDB : RoomDatabase() {
    abstract fun loanDao(): LoanDao
}
