package com.example.githubuser.data.remote.retrofit

import com.example.githubuser.data.remote.response.ItemsItem
import com.example.githubuser.data.remote.response.ListUserResponse
import com.example.githubuser.data.remote.response.UserDetailResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUsers(
        @Query("q") user: String
    ): Call<ListUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username : String?
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username : String?
    ): Call<List<ItemsItem>>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(
        @Path("username") username : String?
    ): Call<UserDetailResponse>
}