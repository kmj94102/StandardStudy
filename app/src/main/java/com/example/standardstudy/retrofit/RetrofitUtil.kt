package com.example.standardstudy.retrofit

import com.example.standardstudy.BuildConfig
import com.example.standardstudy.network.address.api.AddressService
import com.example.standardstudy.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitUtil {

    val service : AddressService by lazy { getRetrofit().create(AddressService::class.java) }

    private fun getRetrofit() : Retrofit =
        Retrofit.Builder()
            // 공통으로 사용하는 주소
            .baseUrl(Constants.ADDRESS_API_BASE_URL)
            // OkHttpClient 등록
            .client(getOkHttpClient())
            // ConverterFactory 등록
            .addConverterFactory(getGsonConvertFactory())
            .build()

    // 각종 통신에 관련한 설정
    private fun getOkHttpClient() : OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(getLoggingInterceptor())
            .build()

    // retrofit 에 관련한 로그를 볼 수 있는 범위 지정
    private fun getLoggingInterceptor() : HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if(BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    // 서버로부터 받은 GSON 결과를 클래스에 맞게 변환해줍니다.
    private fun getGsonConvertFactory() : GsonConverterFactory = GsonConverterFactory.create()

}