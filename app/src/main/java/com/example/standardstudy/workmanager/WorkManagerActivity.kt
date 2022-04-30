package com.example.standardstudy.workmanager

import android.os.Bundle
import android.util.Log
import androidx.work.*
import com.example.standardstudy.BaseActivity
import com.example.standardstudy.R
import com.example.standardstudy.databinding.ActivityWorkManagerBinding
import com.example.standardstudy.network.address.data.AddressParam
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import androidx.activity.viewModels
import com.example.standardstudy.util.repeatOnStarted
import com.example.standardstudy.util.toast
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class WorkManagerActivity : BaseActivity<ActivityWorkManagerBinding, WorkManagerViewModel>(R.layout.activity_work_manager) {

    override val viewModel: WorkManagerViewModel by viewModels()
    private var isMoreData = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getAddressList(keyword = "가능로 109번길", currentPage = 1)

        binding.button.setOnClickListener {

            val workData = workDataOf(TestWorker.WORKER_INPUT_DATA to binding.editText.text.toString())

//            val constraints = Constraints.Builder()
//                .setRequiresBatteryNotLow(true)
//                .setRequiredNetworkType(NetworkType.CONNECTED)
//                .setRequiresCharging(true)
//                .setRequiresStorageNotLow(true)
//                .setRequiresDeviceIdle(true)
//                .build()

            val uploadWorkRequest =
                OneTimeWorkRequestBuilder<TestWorker>()
                    .setInputData(workData)
//                    .setConstraints(constraints)
//                    .setBackoffCriteria(
//                        BackoffPolicy.LINEAR,
//                        OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
//                        TimeUnit.MILLISECONDS
//                    )
                    .build()

//            WorkManager
//                .getInstance(this)
//                .enqueue(uploadWorkRequest)

            WorkManager.getInstance(this).enqueueUniqueWork("test", ExistingWorkPolicy.KEEP, uploadWorkRequest)
            WorkManager
                .getInstance(this)
                .getWorkInfoByIdLiveData(uploadWorkRequest.id)
                .observe(this) { workInfo ->
                    if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                        binding.textView.text = "Worker Success"
                    } else {
                        binding.textView.text = "Worker Failure"
                    }
                }


        }

        repeatOnStarted {
            viewModel.eventFlow.collect { event -> handleEvent(event) }
        }

    }

    /** 이벤트 핸들러 **/
    private fun handleEvent(event : WorkManagerViewModel.AddressEvent) {
        when(event) {
            is WorkManagerViewModel.AddressEvent.AddressList -> {
                isMoreData = event.isMoreData
                event.list.forEach {
                    Log.e("+++++", it)
                }
            }
            is WorkManagerViewModel.AddressEvent.Error -> {
                toast("오류가 발생함")
            }
        }
    }

}

//     // https://www.juso.go.kr/addrlink/devAddrLinkRequestGuide.do?menu=roadApi
//    @GET("addrlink/addrLinkApi.do")
//    suspend fun getAddress(
//        @Query("confmKey") confmKey : String? = "U01TX0FVVEgyMDIyMDEyODA4NDEwOTExMjE5MTM=",
//        @Query("currentPage") currentPage : Int? = 1,
//        @Query("countPerPage") countPerPage : Int? = 10,
//        @Query("keyword") keyword : String,
//        @Query("resultType") resultType : String? = "json",
//        @Query("firstSort") firstSort : String? = "road"
//    ): Response<AddressResult>