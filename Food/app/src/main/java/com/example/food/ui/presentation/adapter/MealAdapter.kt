package com.example.food.ui.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food.databinding.MealCardBinding
import com.example.food.ui.data.model.Meal

class MealAdapter : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    private var mealList: MutableList<Meal> = mutableListOf()
    var setOnMealClickListener: SetOnMealClickListener? = null

    fun setCategoryList(mealList: List<Meal>) {
        this.mealList = mealList.toMutableList()
        notifyDataSetChanged()  // Notify that the data set has changed
    }

    fun getMealByPosition(position: Int): Meal {
        return mealList[position]
    }

    fun removeMeal(meal: Meal) {
        val position = mealList.indexOf(meal)
        if (position != -1) {
            mealList.removeAt(position)
            notifyItemRemoved(position)  // Notify the adapter of item removal
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val binding = MealCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(mealList[position])
    }

    inner class MealViewHolder(private val binding: MealCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(meal: Meal) {
            binding.apply {
                tvMealName.text = meal.strMeal
                Glide.with(root.context)
                    .load(meal.strMealThumb)
                    .into(imgMeal)
            }
            binding.root.setOnClickListener {
                setOnMealClickListener?.setOnClickListener(meal.idMeal)
            }
        }
    }

    interface SetOnMealClickListener {
        fun setOnClickListener(id: String)
    }
}
