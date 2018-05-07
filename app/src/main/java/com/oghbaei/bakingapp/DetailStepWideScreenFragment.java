package com.oghbaei.bakingapp;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oghbaei.bakingapp.queryModel.Recipe;


public class DetailStepWideScreenFragment extends Fragment {

    private static final String RECIPE_KEY_DETAIL_ACT_TO_WRAP_FRAG = "RECIPE_KEY_DETAIL_ACT_TO_WRAP_FRAG";
    private static final String STEP_ID_KEY_DETAIL_ACT_TO_WRAP_FRAG = "STEP_ID_KEY_DETAIL_ACT_TO_WRAP_FRAG";


    private Recipe mRecipe;
    private String mStepId;


    public DetailStepWideScreenFragment() {}

    public static DetailStepWideScreenFragment newInstance(Recipe recipe, String stepId) {
        DetailStepWideScreenFragment fragment = new DetailStepWideScreenFragment();
        Bundle args = new Bundle();
        args.putParcelable(RECIPE_KEY_DETAIL_ACT_TO_WRAP_FRAG, recipe);
        args.putString(STEP_ID_KEY_DETAIL_ACT_TO_WRAP_FRAG, stepId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRecipe = getArguments().getParcelable(RECIPE_KEY_DETAIL_ACT_TO_WRAP_FRAG);
            mStepId = getArguments().getString(STEP_ID_KEY_DETAIL_ACT_TO_WRAP_FRAG);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_step_wide_screen, container, false);

        DetailFragment detailFragment = DetailFragment.newInstance(mRecipe);
        StepFragment stepFragment = StepFragment.newInstance(mRecipe, mStepId);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.fl_detail_fragment_wide_screen, detailFragment);
        transaction.add(R.id.fl_step_fragment_wide_screen, stepFragment);
        transaction.commit();

        return view;
    }

    public void replaceStepFragment(String stepId) {
        mStepId = stepId;
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        StepFragment stepFragment = StepFragment.newInstance(mRecipe, mStepId);
        transaction.replace(R.id.fl_step_fragment_wide_screen, stepFragment);
        transaction.commit();
    }

}
