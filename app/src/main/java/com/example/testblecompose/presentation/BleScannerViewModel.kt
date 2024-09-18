package com.example.testblecompose.presentation

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class BleScannerViewModel(
) : ViewModel() {

    val bleDevices = mutableStateListOf<BluetoothDevice>()
    val isScanning = mutableStateOf(false)
    private val bluetoothScanner: BluetoothLeScanner =
        BluetoothAdapter.getDefaultAdapter().bluetoothLeScanner
    private var scanCallback: ScanCallback? = null

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.S)
    fun startBleScan() {
        scanCallback = object : ScanCallback() {
            override fun onScanResult(callbackType: Int, result: ScanResult?) {
                result?.device?.let { device ->
                    if (!bleDevices.contains(device)) {
                        bleDevices.add(device)
                    }
                }
                Log.d("BleScanner", "onScanResult")
            }

            override fun onBatchScanResults(results: MutableList<ScanResult>?) {
                results?.forEach { result ->
                    result.device?.let { device ->
                        if (!bleDevices.contains(device)) {
                            bleDevices.add(device)
                        }
                    }
                }
                Log.d("BleScanner", "onBatchScanResults")

            }

            override fun onScanFailed(errorCode: Int) {
                Log.e("BleScanner", "Ошибка сканирования: $errorCode")
            }
        }
        bluetoothScanner.startScan(scanCallback)
        isScanning.value = true
    }

    @SuppressLint("MissingPermission")
    fun stopScan() {
        scanCallback?.let {
            bluetoothScanner.stopScan(it)
        }
        isScanning.value = false
    }

}