package com.muindi.stephen.coopbanktest.di

import com.muindi.stephen.coopbanktest.data.repository.LoanRepositoryImpl
import com.muindi.stephen.coopbanktest.domain.repository.LoanRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLoanRepository(
        loanRepositoryImpl: LoanRepositoryImpl
    ): LoanRepository
}
