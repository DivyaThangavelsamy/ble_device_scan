package com.example.bluetoothscan

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {
        val TAG = "Scanner"
        val BLUETOOTH_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onPause() {
        super.onPause()
        val editText = findViewById<EditText>(R.id.scan_time)
        val message = editText.text.toString()
        val sharedPref: SharedPreferences = this.getPreferences(Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putInt("scan_time", message.toInt())
        editor.apply()
    }

    fun sendMessage(view: View) {
        val editText = findViewById<EditText>(R.id.scan_time)
        val message = editText.text.toString()
        val intent = Intent(this, ScannerActivity::class.java).apply {
            putExtra("Key", message)
        }
        startActivity(intent)
    }
}




