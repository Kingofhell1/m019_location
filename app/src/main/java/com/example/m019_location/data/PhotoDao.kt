package com.example.m019_location.data

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize


@Parcelize
@JsonClass(generateAdapter = true)
data class PhotoDao(
    @Json(name = "type") val type: String,
    @Json(name = "features") val features: List<Feature>
) : Parcelable

@Parcelize
data class Feature(
    @Json(name = "type") val type: String,
    @Json(name = "id") val id: String,
    @Json(name = "geometry") val geometry: Point,
    @Json(name = "properties") val properties: Property

) : Parcelable

@Parcelize
data class Point(
    @Json(name = "coordinates") val coordinates: List<Double>
) : Parcelable

@Parcelize
data class Property(
    @Json(name = "xid") val xid: String,
    @Json(name = "name") val name: String,
    @Json(name = "dist") val dist: Float,
    @Json(name = "rate") val rate: Int,
    @Json(name = "kinds") val kinds: String,
) : Parcelable