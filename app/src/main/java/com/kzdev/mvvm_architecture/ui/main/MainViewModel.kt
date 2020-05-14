package com.kzdev.mvvm_architecture.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kzdev.mvvm_architecture.data.model.User
import com.kzdev.mvvm_architecture.data.respository.MainRespository
import com.kzdev.mvvm_architecture.utils.Resource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel (private val respository: MainRespository) : ViewModel(){
    private val users = MutableLiveData<Resource<List<User>>>()
    private val disposable = CompositeDisposable()
    init{
        fetchUsersData()
    }
    private fun fetchUsersData(){
        users.postValue(Resource.loading(null))
        disposable.add(
            respository.getUsersFromApi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    userList -> users.postValue(Resource.success(userList))
                },{
                    throwable ->
                    users.postValue(Resource.error(null,"Something went wrong"))
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
    fun getUsers() : LiveData<Resource<List<User>>>{
        return users
    }
}