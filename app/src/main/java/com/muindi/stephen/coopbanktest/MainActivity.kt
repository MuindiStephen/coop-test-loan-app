package com.muindi.stephen.coopbanktest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.muindi.stephen.coopbanktest.navigation.AppNavGraph
import com.muindi.stephen.coopbanktest.presentation.ui.theme.COOPBankAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            COOPBankAppTheme {
                AppNavGraph()
            }
        }
    }
}
