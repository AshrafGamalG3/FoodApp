package com.example.food.local


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.food.ui.data.model.Meal

@Dao
interface MyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(meal: Meal)

    @Query("select * from Meal")
    suspend fun getAllFavorite():List<Meal>

    @Query("delete from Meal where idMeal=:id")
    suspend fun deleteMealById(id:String)
}