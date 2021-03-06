package com.udacity.asteroidradar

import android.app.Application
import androidx.work.*
import com.udacity.asteroidradar.work.AsteroidsDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

class AsteroidRadarApp : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        delayedInit()
    }

    private fun delayedInit() {
        applicationScope.launch {
            initAsteroidsWorker()
        }
    }

    private fun initAsteroidsWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)
            .build()

        val asteroidsWorkRequest =
            PeriodicWorkRequestBuilder<AsteroidsDataWorker>(1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            AsteroidsDataWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            asteroidsWorkRequest
        )
    }
}
