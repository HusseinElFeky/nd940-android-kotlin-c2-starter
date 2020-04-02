package com.udacity.asteroidradar.repository

import com.udacity.asteroidradar.common.Constants
import com.udacity.asteroidradar.common.DateUtils
import com.udacity.asteroidradar.common.toFormattedString
import com.udacity.asteroidradar.models.Asteroid
import com.udacity.asteroidradar.persistence.AsteroidDao
import kotlinx.coroutines.flow.Flow

class AsteroidsLocalDataSource(private val asteroidDao: AsteroidDao) {

    fun getWeeklyAsteroids(): Flow<List<Asteroid>> =
        asteroidDao.getAsteroidsBetween(
            DateUtils.getToday().time,
            DateUtils.getDateAfter(Constants.DEFAULT_END_DATE_DAYS).time - 1
        )

    fun getTodayAsteroids(): Flow<List<Asteroid>> =
        asteroidDao.getAsteroidsToday(DateUtils.getToday().toFormattedString())

    fun getSavedAsteroids(): Flow<List<Asteroid>> =
        asteroidDao.getAllAsteroids()

    suspend fun saveAsteroid(asteroid: Asteroid) =
        asteroidDao.insert(asteroid)

    suspend fun deleteOldAsteroids() =
        asteroidDao.deleteAsteroidsBefore(DateUtils.getToday().time)
}
