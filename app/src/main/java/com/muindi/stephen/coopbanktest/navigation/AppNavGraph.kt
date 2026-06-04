package com.muindi.stephen.coopbanktest.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.muindi.stephen.coopbanktest.presentation.applyloan.ApplyLoanScreen
import com.muindi.stephen.coopbanktest.presentation.applyloan.ApplyLoanViewModel
import com.muindi.stephen.coopbanktest.presentation.applyloan.LoanDetails
import com.muindi.stephen.coopbanktest.presentation.calculator.LoanCalculatorScreen
import com.muindi.stephen.coopbanktest.presentation.loans.DashboardAllLoans
import com.muindi.stephen.coopbanktest.presentation.loans.DashboardViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.AllLoans
    ) {
        composable(route = Screen.AllLoans) {
            val viewModel: DashboardViewModel = hiltViewModel()
            DashboardAllLoans(
                viewModel = viewModel,
                onLoanClick = {
                    navController.navigate(Screen.ApplyLoan)
                },
                onCalculatorClick = {
                    navController.navigate(Screen.LoanCalculator)
                }
            )
        }

        composable(route = Screen.LoanDetails) {
            val backStackEntry = remember(it) {
                navController.getBackStackEntry(Screen.ApplyLoan)
            }
            val viewModel: ApplyLoanViewModel = hiltViewModel(backStackEntry)
            LoanDetails(navController = navController, viewModel = viewModel)
        }

        composable(route = Screen.ApplyLoan) {
            val viewModel: ApplyLoanViewModel = hiltViewModel()
            ApplyLoanScreen(navController = navController, viewModel = viewModel)
        }

        composable(route = Screen.LoanCalculator) {
            LoanCalculatorScreen(navController = navController)
        }
    }
}
