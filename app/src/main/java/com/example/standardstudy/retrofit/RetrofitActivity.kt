package com.example.standardstudy.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.standardstudy.databinding.ActivityRetrofitBinding
import com.example.standardstudy.network.address.data.AddressResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitActivity : AppCompatActivity() {

    private val binding : ActivityRetrofitBinding by lazy { ActivityRetrofitBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        RetrofitUtil.service.getAddress2(keyword = "서울시 강남구").enqueue(object : Callback<AddressResult> {
            override fun onResponse(call: Call<AddressResult>, response: Response<AddressResult>) {
                if (response.isSuccessful){
                    val result = response.body()
                    result?.let {
                        it.results.address?.forEach { address ->
                            Log.e("ResultActivity", "result : $address")
                        }
                    }
                } else {
                    Log.e("RetrofitActivity", "response error")
                }
            }

            override fun onFailure(call: Call<AddressResult>, t: Throwable) {
                Log.e("RetrofitActivity", "error : ${t.printStackTrace()}")
            }

        })

    }
}