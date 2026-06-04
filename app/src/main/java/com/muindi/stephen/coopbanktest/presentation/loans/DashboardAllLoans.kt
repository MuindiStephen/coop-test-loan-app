package com.muindi.stephen.coopbanktest.presentation.loans

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.muindi.stephen.coopbanktest.R
import com.muindi.stephen.coopbanktest.domain.models.loan.Loan
import com.muindi.stephen.coopbanktest.presentation.components.LoanItem

@Composable
fun DashboardAllLoans(
    onLoanClick: (String) -> Unit,
) {

    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF004E38))
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val user =

                    AsyncImage(
                        model = R.drawable.profile,
                        contentDescription = "User profile avatar",
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = ripple(bounded = true),
                                onClick = {  }
                            ),
                        contentScale = ContentScale.Fit,
                        placeholder = painterResource(R.drawable.profile),
                        error = painterResource(R.drawable.profile)
                    )

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.weight(1f),
                    ) {
                        Column(

                        ) {
                            Text(
                                text = "Hi There!",
                                color = Color.White,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "Boost your income today!",
                                color = Color.White,
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                    }
                }
            }
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {

            val loan = listOf(
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

            SuccessState(
                loan = loan,
                onLoanClick = onLoanClick
            )
        }
    }
}


@Composable
fun SuccessState(
    loan: List<Loan>,
    onLoanClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Available Loans",
                color = Color(0xFF272935),
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }

        items(loan, key = { it.title }) { loan ->
            LoanItem(
                loan = loan,
                onClick = { onLoanClick(loan.title) }
            )
        }
    }
}




@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
private fun LoanListPreview() {

    val items = listOf(
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

    SuccessState(
        onLoanClick = {},
        loan = items
    )
}


