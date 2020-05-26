package eu.mrupp.citytracker.geo

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import eu.mrupp.citytracker.receiver.GeofenceActionReceiver
import eu.mrupp.citytracker.util.LogManager

class GeofenceManager(context: Context) {

    private val geofenceClient by lazy {
        LocationServices.getGeofencingClient(context)
    }

    private val pendingIntent by lazy {
        val intent = Intent(context, GeofenceActionReceiver::class.java)
        PendingIntent.getBroadcast(context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private val geofences = listOf<Geofence>(
        Geofence.Builder()
            .setRequestId("center")
            .setCircularRegion(centerLat, centerLon, 1_500f)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setTransitionTypes(
                Geofence.GEOFENCE_TRANSITION_ENTER
                        or Geofence.GEOFENCE_TRANSITION_EXIT)
            .build()
    )

    private val geofencingRequest by lazy {
        GeofencingRequest.Builder()
            .addGeofences(geofences)
            .setInitialTrigger(0)
            .build()
    }

    /**
     * Create geofence around Marienplatz
     */
    fun startGeofencing() {
        geofenceClient.addGeofences(geofencingRequest, pendingIntent)
        LogManager.log("Start geofencing.")
    }

    companion object {
        private const val centerLat = 48.137966
        private const val centerLon = 11.5739324
    }
}