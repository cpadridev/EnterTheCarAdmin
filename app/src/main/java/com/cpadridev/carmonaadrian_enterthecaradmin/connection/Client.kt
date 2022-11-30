package com.cpadridev.carmonaadrian_enterthecaradmin.connection

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Client {
    companion object{
        private const val URL:String = "https://heliotrope-dog-soccer.glitch.me/"
        // To see the code
        // https://glitch.com/edit/#!/heliotrope-dog-soccer
        var retrofit: Retrofit?= null

        fun getClient(): Retrofit? {
            if(retrofit == null){
                retrofit = Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            }

            return retrofit
        }
    }
}