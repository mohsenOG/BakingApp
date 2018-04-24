package com.oghbaei.bakingapp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.oghbaei.bakingapp.queryModel.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.oghbaei.bakingapp.RecipeFragment.EXTRA_RECIPE;
import static com.oghbaei.bakingapp.RecipeFragment.EXTRA_RECIPE_ID;

public class RecipeDetailActivity extends AppCompatActivity {


    @BindView(R.id.fl_recipe_detail) FrameLayout mRecipeDetailFrameLayout;

    private Recipe mRecipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            //TODO Show some error and abort everything.
            return;
        }
        mRecipe = extras.getParcelable(EXTRA_RECIPE);
        String title = mRecipe.getName();
        if (title != null && !title.isEmpty()) {
            this.setTitle(mRecipe.getName());
        }

        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_RECIPE, mRecipe);
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        recipeDetailFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fl_recipe_detail, recipeDetailFragment)
                .commit();
    }
}
