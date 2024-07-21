package com.example.food.ui.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MealResponse(
    val meals: List<Meal>
): Parcelable
