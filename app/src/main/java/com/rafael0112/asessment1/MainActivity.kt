package com.rafael0112.asessment1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.rafael0112.asessment1.ui.theme.Asessment1Theme
import com.rafael0112.asessment1.ui.screen.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Asessment1Theme {
                MainScreen()
            }
        }
    }
}