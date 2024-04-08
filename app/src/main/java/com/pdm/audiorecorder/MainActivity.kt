package com.pdm.audiorecorder

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.pdm.audiorecorder.presentation.HomeScreen
import com.pdm.audiorecorder.presentation.HomeViewModel
import com.pdm.audiorecorder.presentation.components.AnimatedCircleToRoundedRectangle
import com.pdm.audiorecorder.presentation.components.AnimatedCircleToRoundedRectangleWithPadding
import com.pdm.audiorecorder.presentation.components.AnimatedShapeToggle
import com.pdm.audiorecorder.ui.theme.AudioRecorderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // Permiso concedido, puedes realizar la grabación de audio
            } else {
                // Permiso denegado, muestra un mensaje o toma alguna acción
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AudioRecorderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current
                    val isPermissionGranted by remember { mutableStateOf(checkPermission()) }

                    Column {
                        if (isPermissionGranted) {
                            HomeScreen()
                        } else {
                            Button(onClick = { requestPermission() }) {
                                Text("Request Permission")
                            }
                        }
                    }
                }
            }
        }
    }

    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        requestPermissionLauncher.launch(android.Manifest.permission.RECORD_AUDIO)
    }
}

