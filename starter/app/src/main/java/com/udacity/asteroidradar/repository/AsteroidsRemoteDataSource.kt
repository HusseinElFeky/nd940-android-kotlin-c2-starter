package com.udacity.asteroidradar.repository

import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.api.RetrofitClient
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.models.Asteroid
import com.udacity.asteroidradar.models.PictureOfDay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject

class AsteroidsRemoteDataSource {

    private val nasaApi = RetrofitClient.getClient().create(NasaApi::class.java)

    fun getAsteroids(startDate: String, endDate: String): Flow<List<Asteroid>> = flow {
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
