package com.example.testblecompose.presentation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@SuppressLint("MissingPermission")
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun BleScannerScreen(
    viewModel: BleScannerViewModel,
) {
    val isScanning by viewModel.isScanning
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = {
            if (isScanning) {
                viewModel.stopScan()
            } else {
                viewModel.startBleScan()
            }
        }) {
            Text(if (isScanning) "Остановить сканирование" else "Начать сканирование")
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(2f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(viewModel.bleDevices) { device ->
                Text(text = "${device.name ?: "Неизвестное устройство"} - ${device.address}")
                Spacer(modifier = Modifier.height(3.dp))
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}