package com.ftgrl.cosmotica.retrofit

class ApiUtils {

    companion object {

        val Base_Url = "https://dummyjson.com/"

        fun getProductService() : ProductService{

            return ApiClient.getClient(Base_Url).create(ProductService::class.java)


        }
    }
}