package com.example.food.di

import android.content.Context
import androidx.room.Room
import com.example.food.local.MyDao
import com.example.food.local.MyDatabase
import com.example.food.network.ApiCalls
import com.example.food.ui.data.repo.Repo
import com.example.food.ui.domain.repo.IRepo
import com.example.hospitalmanagementsystem.utils.Const
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MyModule {

    @Provides
    @Singleton
    fun getRetrofit():Retrofit {

        return Retrofit.Builder().baseUrl(Const.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun getApi():ApiCalls{
        return getRetrofit().create(ApiCalls::class.java)
    }

    @Provides
    @Singleton
    fun getFoodUseCase(apiCalls: ApiCalls,myDao: MyDao): IRepo {
        return Repo(apiCalls,myDao)
    }
    @Provides
    @Singleton
    fun getMyDatabase(@ApplicationContext context: Context): MyDatabase {
        return Room.databaseBuilder(context,MyDatabase::class.java,"food")
            .fallbackToDestructiveMigration()
            .build()

    }
    @Provides
    @Singleton
    fun getMyDao(myDatabase: MyDatabase): MyDao {
        return myDatabase.getMyDao()
    }

}