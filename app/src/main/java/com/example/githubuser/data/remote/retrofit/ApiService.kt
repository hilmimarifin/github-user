package com.example.githubuser.data.remote.retrofit

import com.example.githubuser.data.remote.response.ItemsItem
import com.example.githubuser.data.remote.response.ListUserResponse
import com.example.githubuser.data.remote.response.UserDetailResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getUsers(
        @Query("q") user: String
    ): Call<ListUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username : String?
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username : String?
    ): Call<List<ItemsItem>>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username : String?
    ): Call<UserDetailResponse>
}