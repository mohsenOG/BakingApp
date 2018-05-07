package com.oghbaei.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oghbaei.bakingapp.queryModel.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mohsen on 18.04.2018.
 *
 */

public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecipeRecyclerViewAdapter.ViewHolder>{

    private final ArrayList<Recipe> mRecipes;
    private final Context mContext;
    private RecipeClickListener mRecipeClickListener;

    public RecipeRecyclerViewAdapter(ArrayList<Recipe> recipes, Context context) {
        mRecipeClickListener = null;
        mRecipes = recipes;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_item_recipe, viewGroup, false);

        return new ViewHolder(v, mContext);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        viewHolder.bindingData(mRecipes.get(position));
    }

    @Override
    public int getItemCount() { return mRecipes.size(); }

    public interface RecipeClickListener {
        void onRecipeClick(String recipeId);
    }

    void setRecipeClickListener(RecipeClickListener recipeClickListener) {
        mRecipeClickListener = recipeClickListener;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
         @BindView(R.id.tv_recipe_name) TextView recipeNameTextView;
         private final Context mContext;
         private String recipeId;

        ViewHolder(View v, Context context) {
            super(v);
            ButterKnife.bind(this, v);

            recipeId = null;
            mContext = context;
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mRecipeClickListener != null && recipeId != null && !recipeId.isEmpty())
                        mRecipeClickListener.onRecipeClick(recipeId);
                }
            });
        }

        void bindingData(Recipe recipe) {
            // Set recipe name
            String recipeName = recipe.getName();
            if (recipeName != null && !recipeName.isEmpty()) {
                recipeId = recipe.getId();
                recipeNameTextView.setText(recipeName);
            }
            else
                recipeNameTextView.setText(mContext.getString(R.string.no_name));
        }
    }



}
