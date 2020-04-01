package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.common.Constants
import com.udacity.asteroidradar.common.DateUtils
import com.udacity.asteroidradar.common.toFormattedString
import com.udacity.asteroidradar.persistence.AppRoomDatabase.Companion.getDatabase
import com.udacity.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import timber.log.Timber

class AsteroidsDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = AsteroidsRepository(database.asteroidDao())

        try {
            repository.getAsteroidsRemotely(
                DateUtils.getToday().toFormattedString(),
                DateUtils.getDateAfter(Constants.DEFAULT_END_DATE_DAYS).toFormattedString()
            ).first().forEach {
                repository.saveAsteroid(it)
            }
            Timber.d("WorkManager: Work request for syncing asteroids is run.")
        } catch (e: HttpException) {
            return Result.retry()
        }

        return Result.success()
    }
}
