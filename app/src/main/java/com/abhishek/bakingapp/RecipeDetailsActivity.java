package com.abhishek.bakingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.abhishek.bakingapp.adapter.RecipeDetailsAdapter;
import com.abhishek.bakingapp.model.Ingredient;
import com.abhishek.bakingapp.model.Recipe;
import com.abhishek.bakingapp.model.Step;
import com.abhishek.bakingapp.services.RecipeClient;
import com.abhishek.bakingapp.services.RecipeService;
import com.abhishek.bakingapp.utils.ConstantsUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsActivity extends AppCompatActivity {

    public static final String LIST_STATE = "details_list";
    public static final String JSON_STATE = "json_list";

    @BindView(R.id.rv_ingredients_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.btn_tutorial)
    Button mButtonTutorial;

    RecipeDetailsAdapter mRecipeDetailsAdapter;
    String mJsonResult;
    ArrayList<Step> mStepArrayList;
    ArrayList<Recipe> mRecipeArrayList;
    List<Ingredient> mIngredientList;

    private boolean ifTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipedetails);
        ButterKnife.bind(this);

        // Up navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(findViewById(R.id.recipedetails_tablet) != null){
            ifTablet = true;
        }
        else{
            ifTablet = false;
        }


        if(getIntent().getStringExtra(ConstantsUtil.WIDGET_EXTRA) != null){
            SharedPreferences sharedpreferences =
                    getSharedPreferences(ConstantsUtil.SHARED_PREF,MODE_PRIVATE);
            String jsonRecipe = sharedpreferences.getString(ConstantsUtil.JSON_RESULT_EXTRA, "");
            mJsonResult = jsonRecipe;

            Gson gson = new Gson();
            Recipe recipe = gson.fromJson(jsonRecipe, Recipe.class);

            mStepArrayList = (ArrayList<Step>) recipe.getSteps();
            mIngredientList = recipe.getIngredients();
        }
        else{
            if(savedInstanceState != null)
            {
                mRecipeArrayList = savedInstanceState.getParcelableArrayList(LIST_STATE);
                mStepArrayList = (ArrayList<Step>) mRecipeArrayList.get(0).getSteps();
                mIngredientList = mRecipeArrayList.get(0).getIngredients();
                mJsonResult = savedInstanceState.getString(JSON_STATE);
            }
            else{
                Intent recipeIntent = getIntent();
                mJsonResult = recipeIntent.getStringExtra(ConstantsUtil.JSON_RESULT_EXTRA);
                mStepArrayList = (ArrayList<Step>) mRecipeArrayList.get(0).getSteps();
                mIngredientList = mRecipeArrayList.get(0).getIngredients();
                mRecipeArrayList = recipeIntent.getParcelableArrayListExtra(ConstantsUtil.RECIPE_INTENT_EXTRA);
            }
        }

        mRecipeDetailsAdapter = new RecipeDetailsAdapter(this, mIngredientList);

        RecyclerView.LayoutManager mLayoutManager;
        if(ifTablet){
            mLayoutManager = new GridLayoutManager(this, 2);
        }
        else{
            mLayoutManager = new LinearLayoutManager(this);
        }
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mRecipeDetailsAdapter);

        mButtonTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeDetailsActivity.this, TutorialsActivity.class);
                intent.putParcelableArrayListExtra(ConstantsUtil.STEP_INTENT_EXTRA, mStepArrayList);
                intent.putExtra(ConstantsUtil.JSON_RESULT_EXTRA, mJsonResult);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(LIST_STATE, mRecipeArrayList);
        outState.putString(JSON_STATE, mJsonResult);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}