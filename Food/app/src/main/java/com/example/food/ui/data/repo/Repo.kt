package com.example.food.ui.data.repo

import com.example.food.local.MyDao
import com.example.food.network.ApiCalls
import com.example.food.ui.data.model.Categories
import com.example.food.ui.data.model.Meal
import com.example.food.ui.data.model.MealResponse

import com.example.food.ui.domain.repo.IRepo
import javax.inject.Inject

class Repo @Inject constructor (private val apiCalls: ApiCalls,private val myDao: MyDao) :IRepo{
    override suspend fun getRandomMeal(): MealResponse {
        return apiCalls.getRandomMeal()
    }

    override suspend fun insertFavorite(meal: Meal) {
        myDao.insertFavorite(meal)
    }

    override suspend fun getMealsByCategory(category: String): MealResponse {
        return apiCalls.getMealsByCategory(category)
    }

    override suspend fun getMealById(id: String): MealResponse {
        return apiCalls.getMealById(id)
    }

    override suspend fun getCategories(): Categories {
        return apiCalls.getCategories()
    }

    override suspend fun getAllFavorite(): List<Meal> {
        return myDao.getAllFavorite()
    }

    override suspend fun deleteMealById(id: String) {
        myDao.deleteMealById(id)
    }


}