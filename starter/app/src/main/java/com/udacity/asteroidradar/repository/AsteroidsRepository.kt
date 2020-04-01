package com.udacity.asteroidradar.repository

import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.api.RetrofitClient.getClient
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.models.Asteroid
import com.udacity.asteroidradar.models.PictureOfDay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject

object AsteroidsRepository {

    private val nasaApi = getClient().create(NasaApi::class.java)

    fun getAsteroids(startDate: String, endDate: String): Flow<ArrayList<Asteroid>> = flow {
        emit(
            parseAsteroidsJsonResult(
                JSONObject(
                    nasaApi.getAsteroids(
                        startDate,
                        endDate
                    )
                )
            )
        )
    }

    fun getImageOfTheDay(): Flow<PictureOfDay> = flow {
        emit(nasaApi.getImageOfTheDay())
    }
}
