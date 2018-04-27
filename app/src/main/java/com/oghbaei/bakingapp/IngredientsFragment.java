package com.oghbaei.bakingapp;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oghbaei.bakingapp.queryModel.Ingredient;
import com.oghbaei.bakingapp.queryModel.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class IngredientsFragment extends Fragment {

    public static final String RECIPE_KEY_DETAIL_ACT_TO_INGREDIENTS_FRAG = "RECIPE_KEY_DETAIL_ACT_TO_INGREDIENTS_FRAG";

    @BindView(R.id.tv_ingredients) TextView mIngredientsTextView;
    private Recipe mRecipe;

    public IngredientsFragment() { }

    public static IngredientsFragment newInstance(Recipe recipe) {
        IngredientsFragment fragment = new IngredientsFragment();
        Bundle args = new Bundle();
        args.putParcelable(RECIPE_KEY_DETAIL_ACT_TO_INGREDIENTS_FRAG, recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRecipe = getArguments().getParcelable(RECIPE_KEY_DETAIL_ACT_TO_INGREDIENTS_FRAG);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);
        ButterKnife.bind(this, rootView);
        setIngredientsTextView();
        return rootView;
    }

    private void setIngredientsTextView() {
        List<Ingredient> ingredients = mRecipe.getIngredients();
        StringBuilder stringBuilder = new StringBuilder();
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
    }

}
