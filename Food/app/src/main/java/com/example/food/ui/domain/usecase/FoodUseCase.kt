package com.example.food.ui.domain.usecase

import com.example.food.local.MyDao
import com.example.food.ui.data.model.Categories
import com.example.food.ui.data.model.Meal
import com.example.food.ui.data.model.MealResponse

import com.example.food.ui.domain.repo.IRepo
import javax.inject.Inject

class FoodUseCase @Inject constructor(private val iRepo: IRepo,private val myDao: MyDao) {

    suspend fun getRandomMeal():MealResponse{
        return iRepo.getRandomMeal()

    }
   suspend fun insertFavorite(meal: Meal){
       myDao.insertFavorite(meal)
   }
    suspend   fun getMealsByCategory(category:String): MealResponse{
        return iRepo.getMealsByCategory(category)
    }
    suspend   fun getMealById(id:String):MealResponse{
        return iRepo.getMealById(id)
    }
    suspend fun getCategories(): Categories{
        return iRepo.getCategories()
    }

  suspend   fun getAllFavorite():List<Meal>{
      return iRepo.getAllFavorite()
  }

    suspend fun deleteMealById(id:String){
        iRepo.deleteMealById(id)
    }
}