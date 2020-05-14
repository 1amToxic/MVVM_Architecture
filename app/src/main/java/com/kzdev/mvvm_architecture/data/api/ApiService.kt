package com.kzdev.mvvm_architecture.data.api

import android.util.Log
import com.kzdev.mvvm_architecture.data.model.User
import io.reactivex.rxjava3.core.Single
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface ApiService {
    @GET("users")
    fun getUsers() : Call<List<User>>
}
class ApiServiceImpl{
    var instance: Retrofit? = null
    fun fetchUsersApi() : Retrofit{
        if(instance == null){
            var client = OkHttpClient.Builder()
                .connectTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .callTimeout(10,TimeUnit.SECONDS)
                .build()
            instance = Retrofit.Builder()
                .baseUrl("https://5e510330f2c0d300147c034c.mockapi.io")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
        return instance!!
    }
    fun getUserService() : Single<List<User>>{
        var list : Single<List<User>>? = null
        val api = fetchUsersApi().create(ApiService::class.java).getUsers()
        api.enqueue(object : Callback<List<User>>{
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.d("AppLog","Failed fetch API")
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
//                val list  = response.body()
                list = response.body() as Single<List<User>>
            }

        })
        return list!!
    }

}