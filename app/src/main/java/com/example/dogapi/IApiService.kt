package com.example.dogapi

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Esta interfaz va a crear el metodo por el cual accedemos a nuestra api
 */

interface IApiService {

    @GET
    fun getDogsByBreeds(@Url url:String):Response<DogsResponse>

}