package com.example.m019_location.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class State(
    val latitude: Double,
    val longitude: Double,
    val zoom: Float,
    val azimuth: Float,
    val tilt: Float,
    val isDescriptionOpened: Boolean,
    val xid: String,
    val name: String
) : Parcelable