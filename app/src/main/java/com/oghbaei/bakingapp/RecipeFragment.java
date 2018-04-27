package com.oghbaei.bakingapp;

import android.content.Context;
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

import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mohsen on 18.04.2018.
 * The main concept is taken from https://developer.android.com/samples/RecyclerView/src/com.example.android.recyclerview/RecyclerViewFragment.html
 */

public class RecipeFragment extends Fragment implements RecipeRecyclerViewAdapter.RecipeClickListener {

    public static final String RECIPES_KEY_RECIPE_ACT_TO_RECIPE_FRAG = "RECIPES_KEY_RECIPE_ACT_TO_RECIPE_FRAG";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 3;

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    @BindView(R.id.recyclerView_recipe) protected RecyclerView mRecipeRecyclerView;
    @BindBool(R.bool.isLarge) protected boolean mIsLargeScreen;
    private LayoutManagerType mCurrentLayoutManagerType;
    private RecipeRecyclerViewAdapter mRecyclerViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Recipe> mRecipes;
    private OnRecipeFragmentInteractionListener mRecipeFragmentListener;

    public static RecipeFragment newInstance(ArrayList<Recipe> recipes) {
        RecipeFragment fragment = new RecipeFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(RECIPES_KEY_RECIPE_ACT_TO_RECIPE_FRAG, recipes);
        fragment.setArguments(args);
        return fragment;
    }

    public RecipeFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mRecipes = getArguments().getParcelableArrayList(RECIPES_KEY_RECIPE_ACT_TO_RECIPE_FRAG);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe, container, false);
        ButterKnife.bind(this, rootView);

        mLayoutManager = new LinearLayoutManager(getActivity());
        if (mIsLargeScreen) {
            mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
        } else {
            mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState.getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        mRecyclerViewAdapter = new RecipeRecyclerViewAdapter(mRecipes, getContext());
        mRecyclerViewAdapter.setRecipeClickListener(this);
        mRecipeRecyclerView.setHasFixedSize(true);
        mRecipeRecyclerView.setAdapter(mRecyclerViewAdapter);

        return rootView;
    }

    @Override
    public void onRecipeClick(String recipeId) {
        mRecipeFragmentListener.onRecipePassData(mRecipes.get(Integer.parseInt(recipeId) - 1));
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecipeFragmentInteractionListener) {
            mRecipeFragmentListener = (OnRecipeFragmentInteractionListener) context;
        }
        else {
            throw new RuntimeException(context.toString() + " must implement OnRecipeFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mRecipeFragmentListener = null;
    }

    public interface OnRecipeFragmentInteractionListener {
        public void onRecipePassData(Recipe recipe);
    }
}
