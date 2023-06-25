package com.lucanicoletti.compadapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lucanicoletti.compad.Compad
import com.lucanicoletti.compad.CompadCallbacks
import com.lucanicoletti.compad.CompadDirections
import com.lucanicoletti.compadapp.ui.theme.CompadAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompadAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val directionText = remember { mutableStateOf("none") }
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "Current direction: ${directionText.value}"
                    )
                    val callBacks = CompadCallbacks(
                        moveRight = { directionText.value = "Right" },
                        moveLeft = { directionText.value = "Left" },
                        moveUp = { directionText.value = "Up" },
                        moveDown = { directionText.value = "Down" },
                        moveUpRight = { directionText.value = "UpRight" },
                        moveDownRight = { directionText.value = "DownRight" },
                        moveUpLeft = { directionText.value = "UpLeft" },
                        moveDownLeft = { directionText.value = "DownLeft" },
                        onRelease = { directionText.value = "none" }
                    )
                    Compad(
                        modifier = Modifier.padding(8.dp),
                        callbacks = callBacks,
                        directions = CompadDirections.EightDirections
                    )
                }
            }
        }
    }
}