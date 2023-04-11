package com.example.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.remote.response.ItemsItem
import com.example.githubuser.data.remote.response.ListUserResponse
import com.example.githubuser.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListUserViewmodel : ViewModel() {

    private val _listUser = MutableLiveData<List<ItemsItem>?>()
    val listUser : LiveData<List<ItemsItem>?> = _listUser
    private val _isLoadingUsers = MutableLiveData<Boolean>()
    val isLoadingUsers : LiveData<Boolean> = _isLoadingUsers

    fun getUsers(username: String,client: ListItem ){
        _isLoadingUsers.value = true
        val apiService = ApiConfig.getApiService()
        if(client == ListItem.USER) {
            val call : Call<ListUserResponse> = apiService.getUsers(username)
            call.enqueue(object : Callback<ListUserResponse> {
                override fun onResponse(
                    call: Call<ListUserResponse>,
                    response: Response<ListUserResponse>
                ) {
                    _isLoadingUsers.value = false
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {

                            _listUser.value = responseBody.items
                        }
                    } else {
                        Log.e("MainActivity", "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<ListUserResponse>, t: Throwable) {
                    _isLoadingUsers.value = false
                    Log.e("MainActivity", "onFailure: ${t.message}")
                }
            })
        } else {
            val call : Call<List<ItemsItem>> = if (client == ListItem.FOLLOWERS) {
                apiService.getFollowers(username)
            } else {
                apiService.getFollowing(username)
            }
            call.enqueue(object : Callback<List<ItemsItem>> {
                override fun onResponse(
                    call: Call<List<ItemsItem>>,
                    response: Response<List<ItemsItem>>
                ) {
                    _isLoadingUsers.value = false
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                            _listUser.value = responseBody
                    } else {
                        Log.e("MainActivity", "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                    _isLoadingUsers.value = false
                    Log.e("MainActivity", "onFailure: ${t.message}")
                }
            })
        }
    }
}
enum class ListItem {
    FOLLOWERS,
    FOLLOWING,
    USER
}