package com.example.bluetoothscan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ScannerAdapter(val device: ArrayList<String>) :
    RecyclerView.Adapter<ScannerAdapter.DeviceViewHolder>() {

    class DeviceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = itemView.findViewById(R.id.bledevice_name)

        fun bind(device: ArrayList<String>) {
            textView.text = device.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.device, parent, false)
        return DeviceViewHolder(view)
    }

    override fun getItemCount(): Int {
        return device.size
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        return holder.bind(device)
    }

}

