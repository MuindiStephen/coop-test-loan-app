package com.muindi.stephen.coopbanktest.presentation.applyloan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplyLoanScreen(
    navController: NavController,
) {

    var loanType by remember { mutableStateOf(loanTypes[0]) }
    var expanded by remember { mutableStateOf(false) }
    var loanAmount by remember { mutableStateOf("10,000.00") }
    var loanPeriod by remember { mutableStateOf("2") }
    var accountNumber by remember { mutableStateOf("01090145246100") }

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
                onClick = { },
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
                    text = "Apply Loan",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        containerColor = Color(0xFFF5F5F5)
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .background(Color(0xFFF5F5F5))
                .padding(16.dp)
        ) {

            Text(
                text = "Loan Type",
                fontSize = 12.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(6.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it }
            ) {

                OutlinedTextField(
                    value = loanType,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    trailingIcon = {
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = null
                        )
                    }
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    loanTypes.forEach { type ->
                        androidx.compose.material3.DropdownMenuItem(
                            text = { Text(type) },
                            onClick = {
                                loanType = type
                                expanded = false
                            }
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Interest: 15% p.a",
                color = Color(0xFF7BC043),
                fontSize = 11.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Loan Amount",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Spacer(modifier = Modifier.height(6.dp))

            OutlinedTextField(
                value = loanAmount,
                onValueChange = { loanAmount = it },
                prefix = {
                    Text("KES")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Available Loan Limit: 12,000.00 KES",
                color = Color(0xFF7BC043),
                fontSize = 11.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Loan Period (months)",
                fontSize = 12.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(6.dp))

            OutlinedTextField(
                value = loanPeriod,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Total Amount Payable: 11,500.00 KES",
                color = Color(0xFF7BC043),
                fontSize = 11.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Disbursement Account",
                fontSize = 12.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(6.dp))

            OutlinedTextField(
                value = accountNumber,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Available Loan Limit: 120,000.00 KES",
                color = Color(0xFF7BC043),
                fontSize = 11.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Repayment Schedule",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "1st instalment - 22 Oct 2025",
                    fontSize = 13.sp
                )

                Text(
                    text = "5,750.00 KES",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "2nd instalment - 22 Nov 2025",
                    fontSize = 13.sp
                )

                Text(
                    text = "5,750.00 KES",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }


}

val loanTypes = listOf(
    "Salary E-Loan",
    "Stock Loan",
    "Buy Now Pay Later"
)

@Preview(showBackground = true)
@Composable
fun ApplyLoanScreenPreview() {
    ApplyLoanScreen(navController = rememberNavController())
}
