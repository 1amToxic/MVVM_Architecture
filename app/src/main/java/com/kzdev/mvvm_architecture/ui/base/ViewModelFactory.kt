package com.kzdev.mvvm_architecture.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.kzdev.mvvm_architecture.data.api.ApiHelper
import com.kzdev.mvvm_architecture.data.respository.MainRespository
import com.kzdev.mvvm_architecture.ui.main.MainViewModel

class ViewModelFactory (private val apiHelper: ApiHelper) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(MainRespository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}