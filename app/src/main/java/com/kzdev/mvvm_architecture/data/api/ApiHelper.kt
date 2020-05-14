package com.kzdev.mvvm_architecture.data.api

class ApiHelper (private val apiServiceImpl: ApiServiceImpl){
    fun getUsers() = apiServiceImpl.getUserService()
}