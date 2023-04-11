package com.example.githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewmodelFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewmodelFactory? = null
        @JvmStatic
        fun getInstance(application: Application): ViewmodelFactory {
            if (INSTANCE == null) {
                synchronized(ViewmodelFactory::class.java) {
                    INSTANCE = ViewmodelFactory(application)
                }
            }
            return INSTANCE as ViewmodelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewmodel::class.java)) {
            return DetailViewmodel(mApplication) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewmodel::class.java)) {
            return FavoriteViewmodel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}