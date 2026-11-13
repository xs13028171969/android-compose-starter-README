package com.example.androidcomposestarter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.androidcomposestarter.ui.YecaiIslandApp
import com.example.androidcomposestarter.ui.theme.YecaiIslandTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YecaiIslandTheme {
                YecaiIslandApp()
            }
        }
    }
}
