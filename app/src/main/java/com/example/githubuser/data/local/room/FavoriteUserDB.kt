package com.example.githubuser.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubuser.data.local.entity.FavoriteUser

@Database(entities = [FavoriteUser::class], version = 1)
abstract class FavoriteUserDB :RoomDatabase() {

    abstract fun favoriteUserDao(): FavoriteUserDAO

    companion object{
        @Volatile
        private var INSTANCE: FavoriteUserDB? = null

        @JvmStatic
        fun getDatabase(context: Context): FavoriteUserDB {
            if (INSTANCE == null){
                synchronized(FavoriteUserDB::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, FavoriteUserDB::class.java, "favoriteUser_database")
                        .build()
                }
            }
            return INSTANCE as FavoriteUserDB
        }
    }
}