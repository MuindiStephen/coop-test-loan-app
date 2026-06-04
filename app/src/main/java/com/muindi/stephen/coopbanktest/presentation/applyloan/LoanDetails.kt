package com.muindi.stephen.coopbanktest.presentation.applyloan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.muindi.stephen.coopbanktest.navigation.Screen
import com.muindi.stephen.coopbanktest.presentation.components.LoanSuccessDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanDetails(
    navController: NavController,
    viewModel: ApplyLoanViewModel
) {
    val loanAmount by viewModel.loanAmount.collectAsState()
    val loanPeriod by viewModel.loanPeriod.collectAsState()
    val accountNumber by viewModel.accountNumber.collectAsState()
    val calculatedValues by viewModel.calculatedValues.collectAsState()
    
    var showSuccessDialog by remember { mutableStateOf(false) }

    if (showSuccessDialog) {
        LoanSuccessDialog(
            onDismiss = {
                showSuccessDialog = false
                navController.navigate(Screen.AllLoans) {
                    popUpTo(Screen.AllLoans) { inclusive = true }
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Apply Loan",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF005C43)
                )
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    viewModel.confirmLoanApplication {
                        showSuccessDialog = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .navigationBarsPadding()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF7BC043)
                )
            ) {
                Text(
                    text = "Confirm",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        containerColor = Color.White
    ) { paddingValues ->

        LazyColumn (
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(
                start = 24.dp,
                end = 24.dp,
                top = 24.dp,
                bottom = 120.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Loan Details",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF272935)
                )
                
                Spacer(modifier = Modifier.height(8.dp))

                DetailRow(label = "Loan Amount:", value = "$loanAmount KES", valueColor = Color(0xFF005C43), isValueBold = true)
                DetailRow(label = "Interest:", value = "${calculatedValues.interest} KES")
                DetailRow(label = "Total Charges:", value = "${calculatedValues.totalCharges} KES")
                DetailRow(label = "Period:", value = "$loanPeriod Months")
            }

            item {
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Color.LightGray.copy(alpha = 0.5f))
            }

            item {
                Text(
                    text = "Disbursement Details",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF272935)
                )

                Spacer(modifier = Modifier.height(8.dp))

                DetailRow(label = "Account:", value = accountNumber)
                DetailRow(label = "Amount:", value = "$loanAmount KES", isValueBold = true)
            }

            item {
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Color.LightGray.copy(alpha = 0.5f))
            }

            item {
                Text(
                    text = "Repayment Details",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF272935)
                )

                Spacer(modifier = Modifier.height(8.dp))

                DetailRow(label = "Amount:", value = "${calculatedValues.totalCharges} KES", isValueBold = true)
                DetailRow(label = "Installments:", value = loanPeriod)
                DetailRow(label = "Next Repayment Date:", value = calculatedValues.nextRepaymentDate)
            }
        }
    }
}

@Composable
fun DetailRow(
    label: String,
    value: String,
    valueColor: Color = Color(0xFF272935),
    isValueBold: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray
        )

        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = if (isValueBold) FontWeight.Bold else FontWeight.Normal,
            color = valueColor
        )
    }
}
