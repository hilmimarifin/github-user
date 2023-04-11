package com.example.githubuser.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.local.entity.FavoriteUser
import com.example.githubuser.data.remote.response.UserDetailResponse
import com.example.githubuser.data.remote.retrofit.ApiConfig
import com.example.githubuser.repository.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewmodel(application: Application): ViewModel() {
    private val mFavoriteUserRepository: UserRepository = UserRepository(application)
    private val _detailUser = MutableLiveData<UserDetailResponse?>()
    val detailUser : LiveData<UserDetailResponse?> = _detailUser
    private val _isLoadingUsers = MutableLiveData<Boolean>()
    val isLoadingUsers : LiveData<Boolean> = _isLoadingUsers

    fun insert(favoriteUser: FavoriteUser){
        mFavoriteUserRepository.insert(favoriteUser)
    }

    fun delete(favoriteUser: FavoriteUser){
        mFavoriteUserRepository.delete(favoriteUser)
    }

    fun getAllFavorite(){
        mFavoriteUserRepository.getAllFavoriteUsers()
    }

    fun getDetailUser(username: String?){
        _isLoadingUsers.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                _isLoadingUsers.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _detailUser.value = responseBody
                    }
                } else {
                    Log.e("DetailActivity", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                _isLoadingUsers.value = false
                Log.e("DetailActivity", "onFailure: ${t.message}")
            }
        })
    }


}