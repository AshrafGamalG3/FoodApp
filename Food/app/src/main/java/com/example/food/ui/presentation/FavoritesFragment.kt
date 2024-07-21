package com.example.food.ui.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.food.R
import com.example.food.databinding.FragmentFavoritesBinding
import com.example.food.ui.data.model.Meal
import com.example.food.ui.presentation.adapter.MealAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FoodViewModel by viewModels()
    private val mealRecyclerAdapter = MealAdapter()
    private var recentlyDeletedMeal: Meal? = null  // Track recently deleted meal

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        getAllFavorite()
        observe()

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                val meal = mealRecyclerAdapter.getMealByPosition(pos)
                recentlyDeletedMeal = meal  // Track the deleted meal

                // Remove meal from adapter and ViewModel
                mealRecyclerAdapter.removeMeal(meal)
                viewModel.deleteMealById(meal.idMeal)
                showDeleteSnackBar()
            }
        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.favRecView)
    }

    private fun setupRecyclerView() {
        binding.favRecView.adapter = mealRecyclerAdapter
    }

    private fun showDeleteSnackBar() {
        Snackbar.make(requireView(), "Meal was deleted", Snackbar.LENGTH_LONG)
            .setAction("Undo") {
                recentlyDeletedMeal?.let { meal ->
                    viewModel.insertFavorite(meal)  // Restore meal in ViewModel
                    mealRecyclerAdapter.setCategoryList(viewModel.dataMealsByCategory.value ?: listOf())
                }
            }
            .show()
    }

    private fun observe() {
        viewModel.dataMealsByCategory.observe(viewLifecycleOwner) { meals ->
            mealRecyclerAdapter.setCategoryList(meals)
        }
    }

    private fun getAllFavorite() {
        viewModel.getAllFavorite()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
