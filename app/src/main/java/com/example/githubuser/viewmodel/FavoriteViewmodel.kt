package com.example.githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.local.entity.FavoriteUser
import com.example.githubuser.repository.UserRepository

class FavoriteViewmodel(application: Application) :ViewModel(){
    private val mFavoriteUserRepository : UserRepository = UserRepository(application)

    fun getAllFavoriteUser(): LiveData<List<FavoriteUser>> = mFavoriteUserRepository.getAllFavoriteUsers()
}