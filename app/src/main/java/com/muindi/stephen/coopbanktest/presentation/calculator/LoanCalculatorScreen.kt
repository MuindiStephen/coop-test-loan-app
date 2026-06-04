package com.muindi.stephen.coopbanktest.presentation.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.muindi.stephen.coopbanktest.data.local.room.entity.LoanCalculationEntity
import com.muindi.stephen.coopbanktest.domain.models.loan.AmortizationScheduleItem
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanCalculatorScreen(
    navController: NavController,
    viewModel: LoanCalculatorViewModel = hiltViewModel()
) {
    val principal by viewModel.principal.collectAsState()
    val interestRate by viewModel.interestRate.collectAsState()
    val tenure by viewModel.tenure.collectAsState()
    val isMonths by viewModel.isMonths.collectAsState()
    val emi by viewModel.emi.collectAsState()
    val totalInterest by viewModel.totalInterest.collectAsState()
    val totalAmount by viewModel.totalAmount.collectAsState()
    val schedule by viewModel.amortizationSchedule.collectAsState()
    val savedCalculations by viewModel.savedCalculations.collectAsState()
    val error by viewModel.error.collectAsState()
    val saveSuccess by viewModel.saveSuccess.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    var showHistory by remember { mutableStateOf(false) }

    LaunchedEffect(error) {
        error?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearStatus()
        }
    }

    LaunchedEffect(saveSuccess) {
        if (saveSuccess) {
            snackbarHostState.showSnackbar("Calculation saved successfully")
            viewModel.clearStatus()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Loan Calculator", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = { showHistory = true }) {
                        Icon(Icons.Default.History, contentDescription = "History", tint = Color.White)
                    }
                    IconButton(onClick = { viewModel.saveCalculation() }) {
                        Icon(Icons.Default.Save, contentDescription = "Save", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF005C43))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Inputs
            OutlinedTextField(
                value = principal,
                onValueChange = viewModel::onPrincipalChange,
                label = { Text("Loan Amount (Principal)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                prefix = { Text("KES ") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = interestRate,
                onValueChange = viewModel::onInterestRateChange,
                label = { Text("Annual Interest Rate (%)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = tenure,
                    onValueChange = viewModel::onTenureChange,
                    label = { Text("Tenure") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = isMonths, onClick = { viewModel.onTenureUnitChange(true) })
                    Text("Months")
                    RadioButton(selected = !isMonths, onClick = { viewModel.onTenureUnitChange(false) })
                    Text("Years")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Results Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    ResultRow("Monthly EMI", emi)
                    ResultRow("Total Interest", totalInterest)
                    ResultRow("Total Amount", totalAmount)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Amortization Schedule", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))

            AmortizationHeader()
            
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(schedule) { item ->
                    AmortizationRow(item)
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                }
            }
        }
    }

    if (showHistory) {
        HistoryDialog(
            calculations = savedCalculations,
            onDismiss = { showHistory = false },
            onSelect = {
                viewModel.loadCalculation(it)
                showHistory = false
            }
        )
    }
}

@Composable
fun ResultRow(label: String, value: Double) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSecondaryContainer)
        Text(
            String.format(Locale.US, "KES %.2f", value),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun AmortizationHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(4.dp)
    ) {
        Text("No.", modifier = Modifier.weight(0.5f), fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text("EMI", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text("Int.", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text("Prin.", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text("Bal.", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
fun AmortizationRow(item: AmortizationScheduleItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Text("${item.paymentNumber}", modifier = Modifier.weight(0.5f), fontSize = 11.sp)
        Text(String.format(Locale.US, "%.0f", item.emi), modifier = Modifier.weight(1f), fontSize = 11.sp)
        Text(String.format(Locale.US, "%.0f", item.interestComponent), modifier = Modifier.weight(1f), fontSize = 11.sp)
        Text(String.format(Locale.US, "%.0f", item.principalComponent), modifier = Modifier.weight(1f), fontSize = 11.sp)
        Text(String.format(Locale.US, "%.0f", item.remainingBalance), modifier = Modifier.weight(1f), fontSize = 11.sp)
    }
}

@Composable
fun HistoryDialog(
    calculations: List<LoanCalculationEntity>,
    onDismiss: () -> Unit,
    onSelect: (LoanCalculationEntity) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Saved Calculations") },
        text = {
            if (calculations.isEmpty()) {
                Text("No saved calculations found.")
            } else {
                LazyColumn {
                    items(calculations) { calc ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSelect(calc) }
                                .padding(8.dp)
                        ) {
                            Text("Amount: KES ${calc.principal}", fontWeight = FontWeight.Bold)
                            Text("Rate: ${calc.interestRate}% | Tenure: ${calc.tenure} ${if (calc.isMonths) "Months" else "Years"}")
                            HorizontalDivider(modifier = Modifier.padding(top = 4.dp), color = MaterialTheme.colorScheme.outlineVariant)
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("Close") }
        }
    )
}
