package com.oghbaei.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.oghbaei.bakingapp.queryModel.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mohsen on 18.04.2018.
 * The main concept is taken from https://developer.android.com/samples/RecyclerView/src/com.example.android.recyclerview/RecyclerViewFragment.html
 */

public class RecipeFragment extends Fragment implements RecipeRecyclerViewAdapter.RecipeClickListener {

    public static final String ALL_RECIPES_KEY_BUNDLE = "ALL_RECIPES_KEY_BUNDLE";
    public static final String EXTRA_RECIPE_ID = "EXTRA_RECIPE_ID";
    public static final String EXTRA_RECIPE = "EXTRA_RECIPE";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    protected LayoutManagerType mCurrentLayoutManagerType;

    @BindView(R.id.linear_layout_rb) protected RadioButton mLinearLayoutRadioButton;
    @BindView(R.id.grid_layout_rb) protected RadioButton mGridLayoutRadioButton;

    @BindView(R.id.recyclerView_recipe) protected RecyclerView mRecipeRecyclerView;
    protected RecipeRecyclerViewAdapter mRecyclerViewAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected ArrayList<Recipe> mRecipes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle receivedBundle = getArguments();
        if (receivedBundle != null) {
            mRecipes = receivedBundle.getParcelableArrayList(ALL_RECIPES_KEY_BUNDLE);
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe, container, false);
        ButterKnife.bind(this, rootView);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState.getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        mRecyclerViewAdapter = new RecipeRecyclerViewAdapter(mRecipes, getContext());
        mRecyclerViewAdapter.setRecipeClickListener(this);
        mRecipeRecyclerView.setAdapter(mRecyclerViewAdapter);

        mLinearLayoutRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRecyclerViewLayoutManager(LayoutManagerType.LINEAR_LAYOUT_MANAGER);
            }
        });

        mGridLayoutRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRecyclerViewLayoutManager(LayoutManagerType.GRID_LAYOUT_MANAGER);
            }
        });

        return rootView;
    }

    @Override
    public void onRecipeClick(String recipeId) {
        //TODO show the recipe Activity/Fragment here based on the device and structure.
        Intent intent = new Intent(getContext(), RecipeDetailActivity.class);
        intent.putExtra(EXTRA_RECIPE, mRecipes.get(Integer.parseInt(recipeId) - 1));
        startActivity(intent);
    }


    /**
     * Set RecyclerView's LayoutManager to the one given.
     *
     * @param layoutManagerType Type of layout manager to switch to.
     */
    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecipeRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecipeRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        mRecipeRecyclerView.setLayoutManager(mLayoutManager);
        mRecipeRecyclerView.scrollToPosition(scrollPosition);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }
}
