package com.example.food.network

import com.example.food.ui.data.model.Categories
import com.example.food.ui.data.model.Category
import com.example.food.ui.data.model.Meal
import com.example.food.ui.data.model.MealResponse

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiCalls {

    @GET("categories.php")
    suspend fun getCategories():Categories
    @GET("filter.php?")
 suspend   fun getMealsByCategory(@Query("c") category:String):MealResponse

    @GET ("random.php")
    suspend fun getRandomMeal():MealResponse
//
    @GET("lookup.php?")
 suspend   fun getMealById(@Query("i") id:String):MealResponse

//    @GET("search.php?")
//    fun getMealByName(@Query("s") s:String)

}