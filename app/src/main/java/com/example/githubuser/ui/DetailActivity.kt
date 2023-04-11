package com.example.githubuser.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.data.local.entity.FavoriteUser
import com.example.githubuser.data.remote.response.UserDetailResponse
import com.example.githubuser.databinding.ActivityDetailBinding
import com.example.githubuser.viewmodel.DetailViewmodel
import com.example.githubuser.viewmodel.ViewmodelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetailBinding
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailViewmodel = obtainViewModel(this@DetailActivity)
        val username = intent.extras?.getString(HomeFragment.EXTRA_USERNAME)
        detailViewmodel.getDetailUser(username)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, username)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
        var favorite: FavoriteUser? = null
        detailViewmodel.detailUser.observe(this) { detailUser ->
            if (detailUser != null) {
                setUserData(detailUser)
                favorite?.avatar = detailUser.avatarUrl
                favorite?.username = detailUser.login
            }
        }
        detailViewmodel.isLoadingUsers.observe(this) { loading ->
            showLoading(
                loading
            )
        }

        binding?.addToFav?.setOnClickListener{
            favorite?.let { it1 -> detailViewmodel.insert(it1) }
            Toast.makeText(this, "Added to favorite", Toast.LENGTH_SHORT).show()
        }

    }

    private fun setUserData(data: UserDetailResponse) {
        binding.displayName.text = data.name
        binding.userName.text = data.login
        binding.following.text = "${data.following.toString()} Following"
        binding.followers.text = "${data.followers.toString()} Followers"
        Glide.with(this@DetailActivity)
            .load(data.avatarUrl)
            .into(binding.avatar)

    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewmodel {
        val factory = ViewmodelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailViewmodel::class.java]
    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}
