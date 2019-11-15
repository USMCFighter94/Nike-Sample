package dev.brevitz.nike.library.ui.location

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import dev.brevitz.nike.library.ui.R
import kotlinx.android.synthetic.main.view_location.view.*

class LocationView : LinearLayout {

    private val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager?
    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location?) {
            if (location != null) {
                locationText.text = String.format(LOCATION_MESSAGE, location.latitude, location.longitude)
            }
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
        override fun onProviderEnabled(provider: String?) {}
        override fun onProviderDisabled(provider: String?) {}
    }

    private var requestedLocation = false

    init {
        inflate(context, R.layout.view_location, this)

        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        orientation = VERTICAL

        locationUpdateButton.setOnClickListener {
            requestedLocation = true
            checkLocation()
        }
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)

        if (visibility == View.VISIBLE) {
            if (requestedLocation) {
                checkLocation()
            }
        } else {
            locationManager?.removeUpdates(locationListener)
        }
    }

    private fun checkLocation() {
        if (locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true) {
            if (
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            ) {
                context.startActivity(Intent(context, LocationPermissionActivity::class.java))
            } else {
                requestedLocation = false
                locationText.text = context.getString(R.string.getting_location)

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, MIN_DISTANCE, locationListener)
            }
        }
    }

    private companion object {
        private const val LOCATION_MESSAGE = "You are at: %s, %s"
        private const val LOCATION_INTERVAL = 1000L
        private const val MIN_DISTANCE = 1f
    }
}
