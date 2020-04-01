package com.udacity.asteroidradar.repository

import com.udacity.asteroidradar.models.Asteroid
import com.udacity.asteroidradar.models.PictureOfDay
import com.udacity.asteroidradar.persistence.AsteroidDao
import kotlinx.coroutines.flow.Flow

class AsteroidsRepository(asteroidDao: AsteroidDao) {

    private val localDataSource = AsteroidsLocalDataSource(asteroidDao)
    private val remoteDataSource = AsteroidsRemoteDataSource()

    fun getWeeklyAsteroids(): Flow<List<Asteroid>> =
        localDataSource.getWeeklyAsteroids()

    fun getTodayAsteroids(): Flow<List<Asteroid>> =
        localDataSource.getTodayAsteroids()

    fun getSavedAsteroids(): Flow<List<Asteroid>> =
        localDataSource.getSavedAsteroids()

    suspend fun saveAsteroid(asteroid: Asteroid) =
        localDataSource.saveAsteroid(asteroid)

    suspend fun deleteOldAsteroids() =
        localDataSource.deleteOldAsteroids()

    fun getAsteroidsRemotely(startDate: String, endDate: String): Flow<List<Asteroid>> =
        remoteDataSource.getAsteroids(startDate, endDate)

    fun getImageOfTheDay(): Flow<PictureOfDay> =
        remoteDataSource.getImageOfTheDay()
}
