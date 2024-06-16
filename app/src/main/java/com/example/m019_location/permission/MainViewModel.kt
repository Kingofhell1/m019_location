package com.example.m019_location.permission

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m019_location.data.PhotoDao
import com.example.m019_location.data.Repository
import com.google.android.gms.location.*
import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val API_KEY = "5ae2e3f221c38a28845f05b6951552651e1b99433475df864ee48408"

class MainViewModel(context: Context, val repository: Repository) : ViewModel() {

    private var _geoLocationFlow = MutableStateFlow<Point?>(null)
    val geoLocationFlow = _geoLocationFlow.asStateFlow()

    private var _sightsFlow = MutableStateFlow<PhotoDao?>(null)
    val sightsFlow = _sightsFlow.asStateFlow()

    private var _errorMessageFlow = MutableStateFlow<String?>(null)
    val errorMessageFlow = _errorMessageFlow.asStateFlow()

    var fusedClient: FusedLocationProviderClient
    private var longitude: Double = 0.0
    private var latitude: Double = 0.0


    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(position: LocationResult) {
            longitude = position.lastLocation!!.longitude
            latitude = position.lastLocation!!.latitude
            val point = Point(latitude, longitude)
            _geoLocationFlow.value = point
        }
    }

    init {
        fusedClient = LocationServices.getFusedLocationProviderClient(context)
    }

    fun getPhotoFlow(point: Point) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                repository.getPhotoList.getPhoto(
                    1000,
                    point.longitude, point.latitude, 50, API_KEY
                )
            }.fold(
                onSuccess = { response ->
                    if (response!!.isSuccessful) _sightsFlow.value = response.body()
                },
                onFailure = { _errorMessageFlow.value = it.message }
            )
        }
    }


    @SuppressLint("MissingPermission")
    fun startLocation() {
        val request = LocationRequest.create()
            .setInterval(1_000)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)

        fusedClient.requestLocationUpdates(
            request,
            locationCallback,
            Looper.getMainLooper()
        )
    }
}