package com.example.standardstudy.workmanager

import android.os.Bundle
import androidx.work.*
import com.example.standardstudy.BaseActivity
import com.example.standardstudy.R
import com.example.standardstudy.databinding.ActivityWorkManagerBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class WorkManagerActivity : BaseActivity<ActivityWorkManagerBinding, WorkManagerViewModel>(R.layout.activity_work_manager) {

    override val viewModel: WorkManagerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val constraints =
            Constraints
                .Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresStorageNotLow(true)
                .setRequiresDeviceIdle(true)
                .build()

        val workRequest =
            OneTimeWorkRequestBuilder<TestWorker>()
                .setInputData(
                    workDataOf(
                        TestWorker.WORKER_INPUT_DATA to TestWorker.SUCCESS,
                    )
                )
                .setConstraints(constraints)
                .setInitialDelay(10, TimeUnit.MINUTES)
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                .addTag("tag")
                .build()

        WorkManager
            .getInstance(this)
            .enqueue(workRequest)


//        WorkManager.getInstance(this).cancelWorkById(workRequest.id)
//        WorkManager.getInstance(this).cancelAllWorkByTag("tag")
//        WorkManager.getInstance(this).cancelAllWork()
//        WorkManager.getInstance(this).cancelUniqueWork("uniqueWorkName")

    }

}