package com.example.food.ui.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.food.ui.data.model.Categories
import com.example.food.ui.data.model.Category
import com.example.food.ui.data.model.Meal
import com.example.food.ui.data.model.MealResponse

import com.example.food.ui.domain.usecase.FoodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
@HiltViewModel
class FoodViewModel @Inject constructor(
    private val foodUseCase: FoodUseCase
):ViewModel() {

    private var _dataRandomMeal=MutableLiveData<MealResponse>()
    val dataRandomMeal get() = _dataRandomMeal

    private var _dataMealsByCategory=MutableLiveData<List<Meal>>()
    val dataMealsByCategory get() = _dataMealsByCategory

    private var _dataMeal=MutableLiveData<MealResponse>()
    val dataMeal get() = _dataMeal

    private var _dataCategories=MutableLiveData<List<Category>>()
    val dataCategories get() = _dataCategories





    fun getRandomMeal(){
        viewModelScope.launch(IO) {
            try {
                val data=foodUseCase.getRandomMeal()
                withContext(Main){

                    _dataRandomMeal.value=data

                }
            }
            catch (e:Exception){
                throw e
            }


        }
    }
    fun insertFavorite(meal: Meal) {
        viewModelScope.launch(IO) {
            try {
                foodUseCase.insertFavorite(meal)
                getAllFavorite()
            } catch (e: Exception) {
                throw e
            }
        }
    }
    fun getMealsByCategory(category:String){
        viewModelScope.launch {
            try {
                val data= foodUseCase.getMealsByCategory(category)
                withContext(Main){
                    _dataMealsByCategory.value=data.meals
                }

            }catch (e:Exception){
                throw e
            }
        }
    }


       fun getMealById(id:String) {
        viewModelScope.launch(IO) {
            try {
                val data =foodUseCase.getMealById(id)
                withContext(Main) {
                    _dataMeal.value= data

                }
            } catch (e: Exception) {
                throw e
            }

        }
    }

     fun getCategories(){
        viewModelScope.launch(IO) {
            try {
                val data =foodUseCase.getCategories()
                withContext(Main) {
                    _dataCategories.value= data.categories

                }
            } catch (e: Exception) {
                throw e
            }

        }
    }

    fun getAllFavorite(){
        viewModelScope.launch(IO) {
            try {
                val data =foodUseCase.getAllFavorite()
                withContext(Main) {
                    dataMealsByCategory.value= data

                }
            } catch (e: Exception) {
                throw e
            }

        }
    }

    fun deleteMealById(id:String){

        viewModelScope.launch(IO) {
            try {
                foodUseCase.deleteMealById(id)
                getAllFavorite()
            } catch (e: Exception) {
                throw e
            }
        }
    }

}