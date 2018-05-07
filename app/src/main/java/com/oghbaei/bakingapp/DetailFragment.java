package com.oghbaei.bakingapp;

import android.content.Context;
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

/**
 * Created by Mohsen on 20.04.2018.
 *
 */

public class DetailFragment extends Fragment implements DetailRecyclerViewAdapter.DetailClickListener {

    public static final String RECIPE_SAVE_INSTANCE = "RECIPE_SAVE_INSTANCE";
    public static final String RECYCLE_VIEW_SAVE_STATE = "RECYCLE_VIEW_SAVE_STATE";
    public static final String RECIPE_KEY_DETAIL_ACT_TO_DETAIL_FRAG = "RECIPE_KEY_DETAIL_ACT_TO_DETAIL_FRAG";

    @BindView(R.id.recyclerView_steps) RecyclerView mRecipeDetailRecyclerView;
    private DetailRecyclerViewAdapter mAdapter;
    private Recipe mRecipe;
    private OnDetailFragmentInteractionListener mDetailFragmentListener;

    public DetailFragment() {}

    public static DetailFragment newInstance(Recipe recipe) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(RECIPE_KEY_DETAIL_ACT_TO_DETAIL_FRAG, recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mRecipe = getArguments().getParcelable(RECIPE_KEY_DETAIL_ACT_TO_DETAIL_FRAG);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mRecipeDetailRecyclerView.getLayoutManager().onRestoreInstanceState(savedInstanceState.getParcelable(RECYCLE_VIEW_SAVE_STATE));
            mRecipe = savedInstanceState.getParcelable(RECIPE_SAVE_INSTANCE);
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, rootView);
        // RecyclerView
        initRecyclerView();
        return rootView;
    }

    @Override
    public void onStepClick(String stepId) {
        // Send back data to DetailActivity.
        mDetailFragmentListener.onDetailPassData(stepId);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        savedInstanceState.putParcelable(RECYCLE_VIEW_SAVE_STATE, mRecipeDetailRecyclerView.getLayoutManager().onSaveInstanceState());
        savedInstanceState.putParcelable(RECIPE_SAVE_INSTANCE, mRecipe);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDetailFragmentInteractionListener) {
            mDetailFragmentListener = (OnDetailFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnDetailFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mDetailFragmentListener = null;
    }

    private void initRecyclerView() {
        mRecipeDetailRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecipeDetailRecyclerView.setHasFixedSize(true);
        List<Step> steps = mRecipe.getSteps();
        mAdapter = new DetailRecyclerViewAdapter(steps, getContext());
        mAdapter.setStepClickListener(this);
        mRecipeDetailRecyclerView.setAdapter(mAdapter);
    }

    public interface OnDetailFragmentInteractionListener {
        public void onDetailPassData(String StepId);
    }}
