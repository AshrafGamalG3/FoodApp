package com.example.food.ui.domain.repo

import com.example.food.ui.data.model.Categories
import com.example.food.ui.data.model.Meal
import com.example.food.ui.data.model.MealResponse
import retrofit2.http.Query


interface IRepo {

 suspend   fun getRandomMeal(): MealResponse

 suspend  fun insertFavorite(meal: Meal)

 suspend   fun getMealsByCategory(category:String): MealResponse

 suspend   fun getMealById(id:String):MealResponse

 suspend fun getCategories(): Categories

 suspend fun getAllFavorite():List<Meal>

 suspend fun deleteMealById(id:String)
}