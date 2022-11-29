package com.cpadridev.carmonaadrian_enterthecaradmin.connection

import com.cpadridev.carmonaadrian_enterthecaradmin.model.Vehicle
import retrofit2.Call
import retrofit2.http.*

interface ApiEnterTheCarVehcicles {
    @GET("vehicles")
    fun getVehicles(): Call<ArrayList<Vehicle>>

    @FormUrlEncoded
    @POST("vehicles")
    fun saveVehicles(@Field("type") type: String,
                     @Field("price") price: Int,): Call<Vehicle>

    @PUT("vehicles/{id}")
    fun updateVehicle(@Path("id") id: Int, @Body vehicle: Vehicle): Call<Vehicle>

    @DELETE("vehicles/{id}")
    fun deleteVehicle(@Path("id") id: Int): Call<Vehicle>
}