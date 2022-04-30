package com.example.standardstudy.network.address

import com.example.standardstudy.network.address.api.AddressService
import com.example.standardstudy.network.address.data.AddressParam
import com.example.standardstudy.network.address.data.AddressResult
import retrofit2.Response
import javax.inject.Inject

class AddressClient @Inject constructor(
    private val addressService: AddressService
) {

    suspend fun getAddress(
        keyword : String,
        currentPage : Int?
    ) : Response<AddressResult> =
        addressService.getAddress(
            keyword = keyword,
            currentPage = currentPage
        )

}