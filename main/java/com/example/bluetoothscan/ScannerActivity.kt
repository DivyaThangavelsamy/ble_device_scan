package com.example.bluetoothscan

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ScannerActivity : AppCompatActivity() {

    private var scanning = false
    private val handler = Handler()
    var scanTime: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scaner)
        scanTime = intent.getStringExtra("Key").toString()
    }

    val bluetoothAdapter: BluetoothAdapter by lazy {
        (getSystemService(BLUETOOTH_SERVICE) as BluetoothManager).adapter
    }

    override fun onResume() {
        super.onResume()

        if (bluetoothAdapter.isEnabled) {
            //Start scanning
            scanLeDevice(true)
        } else {
            Log.d(MainActivity.TAG, "Bluetooth is not enabled")
            val btIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(btIntent, MainActivity.BLUETOOTH_REQUEST_CODE)
        }
    }

    fun startBEScan() {
        Log.d(MainActivity.TAG, "Started BLE Scanning")

        val scanFilter = ScanFilter.Builder().build()
        val scanFilters: MutableList<ScanFilter> = mutableListOf()
        scanFilters.add(scanFilter)

        val scanSettings = ScanSettings.Builder().setScanMode(ScanSettings.CALLBACK_TYPE_ALL_MATCHES).build()

        Log.d(MainActivity.TAG, "Started Scanning")

        bluetoothAdapter.bluetoothLeScanner?.startScan(scanFilters, scanSettings, bleScanCallback)

    }

    private fun scanLeDevice(enable: Boolean) {
        val SCAN_PERIOD: Long? = scanTime?.toLong()
        bluetoothAdapter.bluetoothLeScanner?.let { scanner ->
            if (enable) {
                if (SCAN_PERIOD != null) {
                    handler.postDelayed({
                        scanning = false
                        scanner.stopScan(bleScanCallback)
                    }, SCAN_PERIOD)
                }
                scanning = true
                startBEScan()
            } else {
                scanning = false
                scanner.stopScan(bleScanCallback)
            }
        }
    }

    private val bleScanCallback: ScanCallback by lazy {
        object : ScanCallback() {
            override fun onScanResult(callbackType: Int, result: ScanResult?) {
                Log.d(MainActivity.TAG, "Scan result")

                val bluetoothDevice = result!!.scanRecord?.deviceName
                if (bluetoothDevice != null) {

                    var filters: ArrayList<String> = ArrayList()
                    filters?.add(bluetoothDevice.toString())

                    Log.d(MainActivity.TAG, "Filtered $filters")

                    Log.d(MainActivity.TAG, "Device Name ${result!!.scanRecord?.deviceName}")

                    val recyclerview: RecyclerView = findViewById(R.id.recycler_view)

                    recyclerview.apply {
                        layoutManager = LinearLayoutManager(this@ScannerActivity)
                        recyclerview.setHasFixedSize(true)
                        adapter = filters?.let { ScannerAdapter(it) }
                    }
                }
            }
        }
    }
}
