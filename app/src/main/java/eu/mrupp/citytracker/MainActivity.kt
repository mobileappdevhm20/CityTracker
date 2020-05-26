package eu.mrupp.citytracker

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import eu.mrupp.citytracker.geo.GeofenceManager
import eu.mrupp.citytracker.util.LogManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Runnable {

    private val geofenceManager by lazy { GeofenceManager(this) }

    private val requiredPermissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
    )

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LogManager.observe(this)

        if (requiredPermissions.all {
                    checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED }) {
            geofenceManager.startGeofencing()
        } else {
            requestPermissions(requiredPermissions, 1)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (grantResults.any { it == PackageManager.PERMISSION_GRANTED }) {
            geofenceManager.startGeofencing()
        } else {
            toastGrantPermissions()
        }
    }

    private fun toastGrantPermissions() {
        Toast.makeText(this,
                "Please grant the required permissions to use the app.",
                Toast.LENGTH_SHORT).show()
    }

    override fun run() {
        log.text = LogManager.getAsString()
    }
}
