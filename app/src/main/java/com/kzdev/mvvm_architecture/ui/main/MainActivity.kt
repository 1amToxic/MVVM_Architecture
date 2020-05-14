package com.kzdev.mvvm_architecture.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.kzdev.mvvm_architecture.R
import com.kzdev.mvvm_architecture.data.api.ApiHelper
import com.kzdev.mvvm_architecture.data.api.ApiServiceImpl
import com.kzdev.mvvm_architecture.data.model.User
import com.kzdev.mvvm_architecture.databinding.ActivityMainBinding
import com.kzdev.mvvm_architecture.ui.base.ViewModelFactory
import com.kzdev.mvvm_architecture.utils.Status
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels(
        factoryProducer = {ViewModelFactory(ApiHelper(ApiServiceImpl()))}
    )
    private lateinit var adapter: UserAdapter
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
    }
    fun setupBinding(){
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    fun setupUi(){
        adapter = UserAdapter(this)
        adapter.updateList(arrayListOf())
        binding.recyclerUser.apply {
            adapter  = this@MainActivity.adapter
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(applicationContext)
            setHasFixedSize(true)
        }
    }
    fun setupObserver(){
        mainViewModel.getUsers().observe(this, Observer {
            when(it.status){
                Status.SUCCESS ->{
                    progress_bar.visibility = View.GONE
                    it.data?.let { users -> updateList(users) }
                    recycler_user.visibility = View.VISIBLE
                }
                Status.LOADING ->{
                    recycler_user.visibility = View.GONE
                    progress_bar.visibility = View.VISIBLE
                }
                Status.ERROR ->{
                    progress_bar.visibility = View.GONE
                    Toast.makeText(this,it.msg,Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    fun updateList(list : List<User>){
        adapter.updateList(list)
    }
}
