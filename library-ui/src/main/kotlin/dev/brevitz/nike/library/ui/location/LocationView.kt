package dev.brevitz.nike.library.ui.location

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.squareup.phrase.Phrase
import dev.brevitz.nike.library.ui.R
import kotlinx.android.synthetic.main.view_location.view.*
import java.util.*

class LocationView : LinearLayout {

    private val geocoder = Geocoder(context, Locale.getDefault())
    private val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager?
    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location?) {
            if (location != null) {
                locationText.text = Phrase.from(context, R.string.user_location_message)
                    .put("lat", location.latitude.toString())
                    .put("long", location.longitude.toString())
                    .format()

                geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    .firstOrNull()
                    ?.let {
                        cityName.text = Phrase.from(context, R.string.user_city_message)
                            .put("city", it.locality)
                            .put("state", it.adminArea)
                            .format()

                        if (currentCity != it.locality || currentState != it.adminArea) {
                            currentCity = it.locality
                            currentState = it.adminArea
                            locationUpdates?.locationUpdated(currentCity!!, currentState!!)
                        }
                    }
            }
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
        override fun onProviderEnabled(provider: String?) {}
        override fun onProviderDisabled(provider: String?) {}
    }

    private var requestedLocation = false
    private var currentCity: String? = null
    private var currentState: String? = null

    var locationUpdates: LocationUpdates? = null

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
            stopLocationUpdates()
        }
    }

    fun stopLocationUpdates() {
        locationManager?.removeUpdates(locationListener)
        currentCity = null
        currentState = null
        locationText.isVisible = false
        cityName.isVisible = false
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
                locationText.isVisible = true
                cityName.isVisible = true
                locationText.text = context.getString(R.string.getting_location)

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, MIN_DISTANCE, locationListener)
            }
        }
    }

    private companion object {
        private const val LOCATION_INTERVAL = 1000L
        private const val MIN_DISTANCE = 1f
    }
}
