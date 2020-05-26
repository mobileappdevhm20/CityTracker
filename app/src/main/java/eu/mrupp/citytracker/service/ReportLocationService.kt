package eu.mrupp.citytracker.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import eu.mrupp.citytracker.util.LogManager

class ReportLocationService : Service() {

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult?) {
            // Send location to server
            LogManager.log("${result?.lastLocation?.latitude}, ${result?.lastLocation?.longitude}")
        }
    }

    private val client by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }

    private val locationRequest by lazy {
        LocationRequest().apply {
            interval = 10_000
            fastestInterval = 1_000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    override fun onCreate() {
        super.onCreate()
        LogManager.log("Start location updates")
        client.requestLocationUpdates(locationRequest, locationCallback , null)
    }

    override fun onDestroy() {
        super.onDestroy()
        LogManager.log("Stop location updates")
        client.removeLocationUpdates(locationCallback)
    }

    override fun onBind(intent: Intent)= null
}