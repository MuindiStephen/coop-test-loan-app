package com.muindi.stephen.coopbanktest.presentation.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.muindi.stephen.coopbanktest.R

@Composable
fun BlockCardSuccessDialog(
    cardNumber: String,
    requestId: String,
    date: String,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = {  },
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = false,
        )
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Request Sent Successfully",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF004E38)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = Color(0xFF272935),
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                fontStyle = FontStyle.Normal
                            )
                        ) {
                            append(text = "Request ID: ")
                        }

                        withStyle(
                            style = SpanStyle(
                                color = Color(0xFF272935),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                fontStyle = FontStyle.Normal
                            )
                        ) {
                            append(text = requestId)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = Color(0xFF272935),
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                fontStyle = FontStyle.Normal
                            )
                        ) {
                            append(text = "Date: ")
                        }

                        withStyle(
                            style = SpanStyle(
                                color = Color(0xFF272935),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                fontStyle = FontStyle.Normal
                            )
                        ) {
                            append(text = date)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                Icon(
                    painter = painterResource(id = R.drawable.block_cards),
                    contentDescription = "Lock",
                    tint = Color(0xFF8BC34A),
                    modifier = Modifier.size(64.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Your request has been submitted successfully",
                    fontSize = 14.sp,
                    color = Color(0xFF272935),
                    fontWeight = FontWeight.Normal,
                )

                Text(
                    text = cardNumber,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF004E38),
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))


                Spacer(modifier = Modifier.height(24.dp))

                Button (
                    onClick = { onDismiss() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF80BA27),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Go Home",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}