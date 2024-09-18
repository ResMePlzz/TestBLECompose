package com.example.testblecompose.presentation

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.S)
    private val checkLocationPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(this, "Разрешение предоставлено", Toast.LENGTH_LONG).show()
        } else {
            Log.e("BleScanner", "Разрешение на сканирование отклонено")
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkLocationPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)

        enableEdgeToEdge()
        setContent {
            val viewModel = viewModel(modelClass = BleScannerViewModel::class)
            BleScannerScreen(viewModel = viewModel)
        }

    }

}

