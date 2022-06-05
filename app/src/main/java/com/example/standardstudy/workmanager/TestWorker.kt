package com.example.standardstudy.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class TestWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {
    override fun doWork(): Result {

        Log.e("UploadWorker", "doWork start")

        // Worker 에게 전달한 데이터 받기
        val inputData = inputData.getString(WORKER_INPUT_DATA)
        Log.e("UploadWorker", "inputData : $inputData")

        // 추가 작업... 파일 업로드, 데이터 통신 등

        return if (inputData == SUCCESS){
            Log.e("uploadWorker", "doWork end : Success")
            Result.success()
        } else {
            Log.e("uploadWorker", "doWork end : Failure")
            Result.failure()
        }
    }

    companion object {
        const val WORKER_INPUT_DATA = "worker_input_data"
        const val SUCCESS = "success"
        const val FAILURE = "failure"
    }

}