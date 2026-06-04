package com.muindi.stephen.coopbanktest.presentation.loans

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.muindi.stephen.coopbanktest.R
import com.muindi.stephen.coopbanktest.data.local.room.entity.LoanEntity
import com.muindi.stephen.coopbanktest.domain.models.loan.Loan
import com.muindi.stephen.coopbanktest.presentation.components.LoanItem

@Composable
fun DashboardAllLoans(
    viewModel: DashboardViewModel,
    onLoanClick: (String) -> Unit,
    onCalculatorClick: () -> Unit = {}
) {
    val activeLoans by viewModel.activeLoans.collectAsState()
    val showInfoNotification by viewModel.showInfoNotification.collectAsState()

    Scaffold(
        topBar = {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF004E38))
                        .windowInsetsPadding(WindowInsets.statusBars)
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                ) {
                    AsyncImage(
                        model = R.drawable.profile,
                        contentDescription = "User profile avatar",
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .align(Alignment.CenterStart),
                        contentScale = ContentScale.Fit,
                        placeholder = painterResource(R.drawable.profile),
                        error = painterResource(R.drawable.profile)
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Hello There!",
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Boost your income today!",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    IconButton(
                        onClick = onCalculatorClick,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Calculate,
                            contentDescription = "Loan Calculator",
                            tint = Color.White
                        )
                    }
                }

                AnimatedVisibility(
                    visible = showInfoNotification,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    InfoNotification()
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            val availableLoans = listOf(
                Loan(
                    "Salary E-Loan",
                    "Get quick loans to boost\nyour income",
                    R.drawable.bg,
                    Color(0xFF398900)
                ), Loan(
                    "Buy Now, Pay Later",
                    "Buy goods today, pay\nlater",
                    R.drawable.img,
                    Color(0xFF0079B9)
                ),
                Loan(
                    "Stock Loan",
                    "Boost your business\nstock today",
                    R.drawable.img_1,
                    Color(0xFFD27111)
                )
            )

            DashboardContent(
                activeLoans = activeLoans,
                availableLoans = availableLoans,
                onLoanClick = { loanTitle ->
                    viewModel.onLoanApplyClick(loanTitle, onLoanClick)
                }
            )
        }
    }
}

@Composable
fun InfoNotification() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(8.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = Color(0xFF7BC043),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "Info",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF004E38),
                    fontSize = 14.sp
                )
                Text(
                    text = "Please repay the current loan to apply for a new one.",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun DashboardContent(
    activeLoans: List<LoanEntity>,
    availableLoans: List<Loan>,
    onLoanClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (activeLoans.isNotEmpty()) {
            item {
                Text(
                    text = "Active Loans",
                    color = Color(0xFF272935),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }
            items(activeLoans) { loan ->
                ActiveLoanCard(loan)
            }
            
            item {
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Color.LightGray.copy(alpha = 0.5f))
            }
        }

        item {
            Text(
                text = if (activeLoans.isEmpty()) "Available Loans" else "Other Loans Available",
                color = Color(0xFF272935),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }

        items(availableLoans) { loan ->
            LoanItem(
                loan = loan,
                onClick = { onLoanClick(loan.title) }
            )
        }
    }
}

@Composable
fun ActiveLoanCard(loan: LoanEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${loan.loanType} Balance",
                color = Color(0xFF005C43),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = loan.balance,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF272935)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "KES",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF005C43),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Next Payment", fontSize = 12.sp, color = Color.Gray)
                    Text(text = "${loan.monthlyPayment} KES", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
                
                Box(modifier = Modifier.width(1.dp).height(40.dp).background(Color.LightGray.copy(alpha = 0.5f)))

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Instalment", fontSize = 12.sp, color = Color.Gray)
                    Text(text = loan.nextRepaymentDate, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
            }
        }
    }
}
