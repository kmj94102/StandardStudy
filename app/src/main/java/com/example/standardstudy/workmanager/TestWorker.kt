package com.example.standardstudy.workmanager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.delay

class TestWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        Log.e("TestWorker", "Test1Worker")

        // Worker 에게 전달한 데이터 받기
        val inputData = inputData.getString(WORKER_INPUT_DATA)

        // 추가 작업... 파일 업로드, 데이터 통신 등
        Thread.sleep(2000)

        return if (inputData == SUCCESS){
            Log.e("TestWorker", "Test1 doWork end : Success")
            val output = workDataOf(RESULT to "Test1 doWork end : Success")
            Result.success(output)
        } else {
            Log.e("TestWorker", "Test1 doWork end : Failure")
            val output = workDataOf(RESULT to "Test1 doWork end : Failure")
            Result.failure(output)
        }
    }

    companion object {
        const val WORKER_INPUT_DATA = "worker_input_data"
        const val SUCCESS = "success"
        const val FAILURE = "failure"
        const val RESULT = "result"
    }

}