package com.example.githubuser.data.remote.response

import com.google.gson.annotations.SerializedName

data class GithubListUserResponse(

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("login")
	val login: String
)
