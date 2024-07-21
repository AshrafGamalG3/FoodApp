# Food Application

## Overview

This project is a food application for Android that allows users to browse meals, view details, and manage their favorite meals. The app includes features like meal categories, random meal suggestions, and the ability to swipe to delete favorite meals with undo functionality.

## Features

- **Meal Categories:** Browse meals by categories like "Seafood".
- **Random Meal Suggestion:** Get a random meal suggestion every few seconds.
- **Favorites Management:** Add meals to favorites and manage them with swipe-to-delete functionality.
- **Undo Deletion:** Restore deleted favorite meals with a Snackbar action.

## Technologies Used

- **Kotlin:** Primary language for Android development.
- **AndroidX:** Libraries for modern Android development.
- **Dagger Hilt:** Dependency injection framework.
- **Glide:** Image loading and caching library.
- **Room Database:** Local database for storing favorite meals.
- **LiveData & ViewModel:** For managing UI-related data in a lifecycle-conscious way.
- **RecyclerView:** For displaying lists of meals.
- **ItemTouchHelper:** For implementing swipe-to-delete functionality.

## Project Structure

- **`com.example.food.ui.presentation`:** Contains fragments and adapters for the UI.
  - `HomeFragment`: Displays meal categories, random meal suggestions, and manages clicks.
  - `FavoritesFragment`: Manages favorite meals, supports swipe-to-delete, and undo functionality.
- **`com.example.food.ui.data.model`:** Contains data models for meals and meal responses.
- **`com.example.food.ui.presentation.adapter`:** Contains adapters for RecyclerViews.

## Video

https://github.com/user-attachments/assets/cbd9177d-8563-47bb-a86d-f667f492242b


