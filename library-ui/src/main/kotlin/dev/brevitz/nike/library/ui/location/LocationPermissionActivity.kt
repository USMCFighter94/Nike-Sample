package dev.brevitz.nike.library.ui.location

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class LocationPermissionActivity : AppCompatActivity() {

    private val locationPermissionsNeeded = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions(locationPermissionsNeeded, PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                val allPermissionsGranted = grantResults.map { it == PackageManager.PERMISSION_GRANTED }.none { !it }

                if (allPermissionsGranted) {
                    setResult(Activity.RESULT_OK)
                } else {
                    setResult(Activity.RESULT_CANCELED)
                }

                finish()
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
        }
    }

    private companion object {
        private const val PERMISSION_REQUEST_CODE = 68745
    }
}
