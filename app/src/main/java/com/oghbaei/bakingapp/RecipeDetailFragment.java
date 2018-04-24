package com.oghbaei.bakingapp;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oghbaei.bakingapp.queryModel.Ingredient;
import com.oghbaei.bakingapp.queryModel.Recipe;
import com.oghbaei.bakingapp.queryModel.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.oghbaei.bakingapp.RecipeFragment.EXTRA_RECIPE;

/**
 * Created by Mohsen on 20.04.2018.
 *
 */

public class RecipeDetailFragment extends Fragment implements RecipeDetailRecyclerViewAdapter.StepClickListener {

    @BindView(R.id.tv_ingredients) TextView mIngredientsTextView;
    @BindView(R.id.recyclerView_steps) RecyclerView mRecipeDetailRecyclerView;
    RecipeDetailRecyclerViewAdapter mAdapter;
    Recipe mRecipe;
    private Parcelable mRecyclerViewState;

    public static final String STEPS_RECYCLER_VIEW_STATE = "STEPS_RECYCLER_VIEW_STATE";
    public static final String RECIPE_SAVE_INSTANCE = "RECIPE_SAVE_INSTANCE";



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle receiveBundle = getArguments();
        if (receiveBundle != null) {
            mRecipe = receiveBundle.getParcelable(EXTRA_RECIPE);
        }
        if (savedInstanceState != null) {
            mRecyclerViewState = savedInstanceState.getParcelable(STEPS_RECYCLER_VIEW_STATE);
            if (mRecipe == null) {
                mRecipe = savedInstanceState.getParcelable(RECIPE_SAVE_INSTANCE);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        ButterKnife.bind(this, rootView);

        // RecyclerView
        mRecipeDetailRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Step> steps = mRecipe.getSteps();
        mAdapter = new RecipeDetailRecyclerViewAdapter(steps, getContext());
        mAdapter.setStepClickListener(this);
        mRecipeDetailRecyclerView.setAdapter(mAdapter);
        if (mRecyclerViewState != null) {
            mRecipeDetailRecyclerView.getLayoutManager().onRestoreInstanceState(mRecyclerViewState);
        }

        // Ingredients
        List<Ingredient> ingredients = mRecipe.getIngredients();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getString(R.string.ingredients)).append(":").append("\n\n");
        for (Ingredient i : ingredients) {
            String ingredientName = i.getIngredient();
            if (ingredientName != null && !ingredientName.isEmpty()) {
                stringBuilder.append(ingredientName).append(" ");
                String measure = i.getMeasure();
                if (measure != null && !measure.isEmpty()) {
                    stringBuilder.append(measure).append(" ");
                }
                String quantity = i.getQuantity();
                if (quantity != null && !quantity.isEmpty()) {
                    stringBuilder.append(quantity);
                }
                stringBuilder.append("\n");
            }
        }
        mIngredientsTextView.setText(stringBuilder.toString());

        return rootView;
    }

    @Override
    public void onStepClick(String StepId) {
        //TODO Handle the Step Detail here.
        // mRecipe is here available so no need to be send via interface.
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelable(STEPS_RECYCLER_VIEW_STATE, mRecipeDetailRecyclerView.getLayoutManager().onSaveInstanceState());
        savedInstanceState.putParcelable(RECIPE_SAVE_INSTANCE, mRecipe);
    }
}
