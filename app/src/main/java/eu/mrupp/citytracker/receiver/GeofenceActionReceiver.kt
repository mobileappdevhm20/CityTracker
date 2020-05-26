package eu.mrupp.citytracker.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import eu.mrupp.citytracker.service.ReportLocationService
import eu.mrupp.citytracker.util.LogManager

class GeofenceActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val event = GeofencingEvent.fromIntent(intent)
        val serviceIntent = Intent(context, ReportLocationService::class.java)

        when (event.geofenceTransition) {
            Geofence.GEOFENCE_TRANSITION_ENTER -> {
                LogManager.log("Geofence action triggered: ENTER")
                context.startService(serviceIntent)
            }
            Geofence.GEOFENCE_TRANSITION_EXIT -> {
                LogManager.log("Geofence action triggered: EXIT")
                context.stopService(serviceIntent)
            }
        }
    }
}
