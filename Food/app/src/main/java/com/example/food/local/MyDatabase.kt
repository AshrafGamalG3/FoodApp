package com.example.food.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.food.ui.data.model.Meal

@Database(entities = [Meal::class] , version = 2)
abstract class MyDatabase : RoomDatabase() {


    abstract fun getMyDao():MyDao

}