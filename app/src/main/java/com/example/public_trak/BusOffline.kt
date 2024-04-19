package com.example.public_trak

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast

class BusOffline : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_offline)

        Toast.makeText(this, "Offline Mode", Toast.LENGTH_SHORT).show()

        val spinner: Spinner = findViewById(R.id.sp_agency)
        val agenciesList = readAgenciesFromRawFile()
        val offlineBtn: Button = findViewById(R.id.offline_btn)
        // Create an ArrayAdapter using the agencies list and a default spinner layout
        var adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, agenciesList)
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        spinner.adapter = adapter
        val autoCompleteTextViewStops: AutoCompleteTextView = findViewById(R.id.autotv_stops)
        val stopsList = readStopsFromRawFile()

        // Create an ArrayAdapter using the stops list and a default layout
        adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, stopsList)
        // Apply the adapter to the AutoCompleteTextView
        autoCompleteTextViewStops.setAdapter(adapter)
        val autoCompleteTextViewDestination: AutoCompleteTextView = findViewById(R.id.autotv_destination)
        // Apply the adapter to the AutoCompleteTextView
        autoCompleteTextViewDestination.setAdapter(adapter)

        offlineBtn.setOnClickListener {
            if (isInternetAvailable()) {
                // Internet connection available, start MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                // No internet connection, handle appropriately (optional)
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun readStopsFromRawFile(): List<String> {
        val stopsList = mutableListOf<String>()
        val inputStream = resources.openRawResource(R.raw.stops)
        val reader = inputStream.bufferedReader()
        // Skip the first line (column names)
        reader.readLine()

        reader.useLines { lines ->
            lines.forEach { line ->
                // Split each line by comma and add the first element (agency name) to the list
                val parts = line.split(",")
                if (parts.isNotEmpty()) {
                    stopsList.add(parts[4])
                }
            }
        }
        return stopsList
    }

    private fun readAgenciesFromRawFile(): List<String> {
        val agenciesList = mutableListOf<String>()
        agenciesList.add("None");
        val inputStream = resources.openRawResource(R.raw.agency)
        val reader = inputStream.bufferedReader()
        // Skip the first line (column names)
        reader.readLine()

        reader.useLines { lines ->
            lines.forEach { line ->
                // Split each line by comma and add the first element (agency name) to the list
                val parts = line.split(",")
                if (parts.isNotEmpty()) {
                    agenciesList.add(parts[1])
                }
            }
        }
        return agenciesList
    }

    // Method to check if internet is available
    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManager?.let {
            val networkInfo = it.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
        return false
    }
}