package com.muindi.stephen.coopbanktest.di

import android.content.Context
import androidx.room.Room
import com.muindi.stephen.coopbanktest.data.local.room.dao.LoanDao
import com.muindi.stephen.coopbanktest.data.local.room.db.LoanDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideLoanDatabase(@ApplicationContext context: Context): LoanDB {
        return Room.databaseBuilder(
            context,
            LoanDB::class.java,
            "loan_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideLoanDao(database: LoanDB): LoanDao {
        return database.loanDao()
    }
}
