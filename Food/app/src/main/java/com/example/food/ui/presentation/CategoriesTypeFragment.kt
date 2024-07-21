package com.example.food.ui.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.food.R
import com.example.food.databinding.FragmentCategoriesTypeBinding
import com.example.food.databinding.FragmentHomeBinding
import com.example.food.ui.presentation.adapter.CategoriesAdapter
import com.example.food.ui.presentation.adapter.MealAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesTypeFragment : Fragment() {

    private var _binding: FragmentCategoriesTypeBinding? = null
    private val binding get() = _binding!!
    private val viewModel:FoodViewModel by viewModels()
    private val mealRecyclerAdapter= MealAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories_type, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentCategoriesTypeBinding.bind(view)
        val type=CategoriesTypeFragmentArgs.fromBundle(requireArguments()).type

        getMealsByCategory(type)
        observe()
        onClick()


    }

    private fun observe(){
        viewModel.dataMealsByCategory.observe(viewLifecycleOwner){
           mealRecyclerAdapter.setCategoryList(it)
            binding.mealRecyclerview.adapter=mealRecyclerAdapter
            binding.tvCategoryCount.text= it.size.toString()
        }




    }

    private fun onClick(){
        mealRecyclerAdapter.setOnMealClickListener = object : MealAdapter.SetOnMealClickListener {
            override fun setOnClickListener(id: String) {

                    Log.e("TAG", "setOnClickListener: $")
                    val action = CategoriesTypeFragmentDirections.actionCategoriesTypeFragmentToMealDetailsFragment(id)
                    findNavController().navigate(action)
                }

        }
    }

    private fun getMealById(id: String) {
        viewModel.getMealById(id)
    }

    private fun getMealsByCategory(category:String){
        viewModel.getMealsByCategory(category)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}