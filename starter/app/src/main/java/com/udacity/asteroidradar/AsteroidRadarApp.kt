package com.udacity.asteroidradar

import android.app.Application
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.udacity.asteroidradar.work.AsteroidsDataWorker
import java.util.concurrent.TimeUnit

class AsteroidRadarApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initAsteroidsWorker()
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

        WorkManager.getInstance().enqueue(asteroidsWorkRequest)
    }
}
