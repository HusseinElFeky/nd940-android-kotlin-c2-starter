package com.udacity.asteroidradar.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "asteroids")
data class Asteroid(
    @PrimaryKey val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val epochDateCloseApproach: Long,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
) : Parcelable
