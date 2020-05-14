package com.kzdev.mvvm_architecture.data.respository

import com.kzdev.mvvm_architecture.data.api.ApiHelper
import com.kzdev.mvvm_architecture.data.model.User
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

class MainRespository(private val apiHelper: ApiHelper){
    fun getUsersFromApi() : Single<List<User>>{
        return apiHelper.getUsers()
    }
}