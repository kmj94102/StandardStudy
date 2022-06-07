package com.example.standardstudy.workmanager

import android.os.Bundle
import android.util.Log
import androidx.work.*
import com.example.standardstudy.BaseActivity
import com.example.standardstudy.R
import com.example.standardstudy.databinding.ActivityWorkManagerBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import androidx.core.view.isVisible

@AndroidEntryPoint
class WorkManagerActivity :
    BaseActivity<ActivityWorkManagerBinding, WorkManagerViewModel>(R.layout.activity_work_manager) {

    override val viewModel: WorkManagerViewModel by viewModels()
    private var data = TestWorker.FAILURE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()

    }

    private fun initViews() = with(binding) {
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                radioSuccess.id -> {
                    data = TestWorker.SUCCESS
                }
                radioFailure.id -> {
                    data = TestWorker.FAILURE
                }
            }
        }

        button.setOnClickListener {
            val request = createWorkerRequest()
            val request2 = createWorkerRequest2()

            WorkManager
                .getInstance(this@WorkManagerActivity)
                .beginWith(request)
                .then(request2)
                .enqueue()

//                    getWorkInfoByIdLiveData(request.id)
//                        .observe(this@WorkManagerActivity) { workInfo ->
//                            setWorkResult(workInfo)
//                        }
        }
    }

    private fun createWorkerRequest() =
        OneTimeWorkRequestBuilder<TestWorker>()
            .setInputData(
                workDataOf(
                    TestWorker.WORKER_INPUT_DATA to data,
                )
            )
            .addTag("tag")
            .build()

    private fun createWorkerRequest2() =
        OneTimeWorkRequestBuilder<Test2Worker>()
            .setInputData(
                workDataOf(
                    Test2Worker.WORKER_INPUT_DATA to data,
                )
            )
            .addTag("tag")
            .build()

    private fun setWorkResult(workInfo: WorkInfo) {
        when (workInfo.state) {
            WorkInfo.State.SUCCEEDED -> {
                val result = workInfo.outputData.getString(TestWorker.RESULT)
                binding.textView.text = result
                binding.progressBar.isVisible = false
            }
            WorkInfo.State.FAILED -> {
                val result = workInfo.outputData.getString(TestWorker.RESULT)
                binding.textView.text = result
                binding.progressBar.isVisible = false
            }
            WorkInfo.State.RUNNING -> {
                binding.progressBar.isVisible = true
            }
            else -> {}
        }
    }

}