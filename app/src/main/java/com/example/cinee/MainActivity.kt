package com.example.cinee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.cinee.component.progress.CircularDeterminateProgressBar
import com.example.cinee.ui.theme.CineeTheme
import kotlinx.coroutines.delay
import androidx.compose.runtime.LaunchedEffect
import com.example.cinee.component.progress.LinearDeterminateProgressBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CineeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface {
                        AppScreen(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}

@Composable
fun AppScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.background(MaterialTheme.colorScheme.background))
    { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            var progress by remember { mutableFloatStateOf(0f) }
            
            CircularDeterminateProgressBar(
                progress = progress
            )
            LinearDeterminateProgressBar(
                progress = progress
            )
            
            LaunchedEffect(Unit) {
                while (progress < 1f) {
                    delay(25)
                    progress += 0.01f
                }
            }
        }
    }
}

