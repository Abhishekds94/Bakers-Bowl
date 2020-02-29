package com.abhishek.bakingapp.services;

import com.abhishek.bakingapp.model.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeService {

    //Get Recipe
    @GET("baking.json")
    Call<ArrayList<Recipe>> getRecipes();

}
