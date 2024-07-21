package com.example.food.ui.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.food.R
import com.example.food.databinding.FragmentHomeBinding
import com.example.food.databinding.FragmentMealDetailsBinding
import com.example.food.ui.data.model.Meal

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MealDetailsFragment : Fragment() {

    private var _binding: FragmentMealDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel:FoodViewModel by viewModels()
    private var ytUrl = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_meal_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentMealDetailsBinding.bind(view)
        val meal = MealDetailsFragmentArgs.fromBundle(requireArguments()).meal
getDataOfMeal(meal)
        observe()


    }
    private fun onClick(meal: Meal){
        binding.imgYoutube.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(ytUrl)))
        }
        binding.btnSave.setOnClickListener {
            viewModel.insertFavorite(meal)

        }
    }

    private fun observe(){
        viewModel.dataMeal.observe(viewLifecycleOwner){
            setDataOfRandomImage(it.meals[0])
            onClick(it.meals[0])
        }
    }

    private fun getDataOfMeal(id:String){
        viewModel.getMealById(id)
    }
    private fun setDataOfRandomImage(meal: Meal){
        Glide.with(this@MealDetailsFragment)
            .load(meal.strMealThumb)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.imgMealDetail)
        ytUrl = meal.strYoutube
        binding.apply {
            collapsingToolbar.title=meal.strMeal
            tvInstructions.text = "- Instructions : "
            tvContent.text = meal.strInstructions
            tvAreaInfo.visibility = View.VISIBLE
            tvCategoryInfo.visibility = View.VISIBLE
            tvAreaInfo.text = tvAreaInfo.text.toString() + meal.strArea
            tvCategoryInfo.text = tvCategoryInfo.text.toString() + meal.strCategory
            imgYoutube.visibility = View.VISIBLE
        }

    }


}