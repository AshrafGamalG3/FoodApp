package com.example.food.ui.presentation


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.food.R

import com.example.food.databinding.FragmentHomeBinding
import com.example.food.ui.data.model.Meal
import com.example.food.ui.data.model.MealResponse
import com.example.food.ui.presentation.adapter.CategoriesAdapter
import com.example.food.ui.presentation.adapter.MealAdapter


import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel:FoodViewModel by viewModels()
    private var job: Job? = null

    private var randomMealId: String? = null
    private var meal: Meal? = null

    private val mealRecyclerAdapter=MealAdapter()
    private val categoriesRecyclerAdapter=CategoriesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentHomeBinding.bind(view)
        startRepeatingTask()
        getMealsByCategory("Seafood")
        getCategories()
        observe()
        onClick()
    }

    private fun onClick() {
        binding.imgRandomMeal.setOnClickListener {

            val action = HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(meal?.idMeal.toString())
            findNavController().navigate(action)
        }

        mealRecyclerAdapter.setOnMealClickListener = object : MealAdapter.SetOnMealClickListener {
            override fun setOnClickListener(id: String) {

                        val action = HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(id)
                        findNavController().navigate(action)
                    }

        }





        categoriesRecyclerAdapter.onItemClicked(object :CategoriesAdapter.OnItemCategoryClicked{
            override fun onClickListener(type: String) {
                val action = HomeFragmentDirections.actionHomeFragmentToCategoriesTypeFragment(type)
                Log.e("TAG", "onClickListener: $type", )
                findNavController().navigate(action)
            }

        })




    }
    private fun getRandomMeal(){
        viewModel.getRandomMeal()
    }

     private fun getCategories(){
         viewModel.getCategories()
     }

    private fun observe() {
        viewModel.dataRandomMeal.observe(viewLifecycleOwner) { response ->
            response?.let {
                if (it.meals.isNotEmpty()) {
                    val mealImage = binding.imgRandomMeal
                    val imageUrl = it.meals[0].strMealThumb
                    randomMealId = it.meals[0].idMeal
                    meal = it.meals[0]

                    animateImageViewBackground(mealImage) {
                        Glide.with(this@HomeFragment)
                            .load(imageUrl)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(mealImage)
                    }
                }
            }
        }

        viewModel.dataMealsByCategory.observe(viewLifecycleOwner) { mealList ->
            mealList?.let {
                mealRecyclerAdapter.setCategoryList(it)
                binding.recViewMealsPopular.adapter=mealRecyclerAdapter

            }

        }


        viewModel.dataCategories.observe(viewLifecycleOwner){
            categoriesRecyclerAdapter.setCategoryList(it)
            binding.recyclerView.adapter=categoriesRecyclerAdapter
        }






    }


    private fun animateImageViewBackground(imageView: ImageView, onAnimationEnd: () -> Unit) {

        imageView.animate()
            .alpha(0f)
            .setDuration(500)
            .withEndAction {
                onAnimationEnd()

                imageView.alpha = 1f
            }
    }

    private fun startRepeatingTask() {
        job = viewLifecycleOwner.lifecycleScope.launch {
            while (true) {
                getRandomMeal()
                delay(10000)
            }
        }
    }

       private fun getMealsByCategory(category:String){
           viewModel.getMealsByCategory(category)
       }






    private fun stopRepeatingTask() {
        job?.cancel()
    }


    override fun onDestroy() {
        super.onDestroy()
        stopRepeatingTask()
    }
}