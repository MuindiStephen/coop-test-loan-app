package com.muindi.stephen.coopbanktest.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.muindi.stephen.coopbanktest.presentation.applyloan.ApplyLoanScreen
import com.muindi.stephen.coopbanktest.presentation.applyloan.LoanDetails
import com.muindi.stephen.coopbanktest.presentation.calculator.LoanCalculatorScreen
import com.muindi.stephen.coopbanktest.presentation.loans.DashboardAllLoans

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.AllLoans
    ) {
        composable(route = Screen.AllLoans) {
            DashboardAllLoans(
                onLoanClick = {
                    navController.navigate(Screen.ApplyLoan)
                },
                onCalculatorClick = {
                    navController.navigate(Screen.LoanCalculator)
                }
            )
        }

        composable(route = Screen.LoanDetails) {
            LoanDetails(modifier = Modifier, navController = navController)
        }

        composable(route = Screen.ApplyLoan) {
            ApplyLoanScreen(navController = navController)
        }

        composable(route = Screen.LoanCalculator) {
            LoanCalculatorScreen(navController = navController)
        }
    }
}
