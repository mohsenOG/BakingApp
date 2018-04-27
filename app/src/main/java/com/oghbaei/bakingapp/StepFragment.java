package com.oghbaei.bakingapp;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oghbaei.bakingapp.queryModel.Recipe;

import butterknife.ButterKnife;


public class StepFragment extends Fragment {

    public static final String RECIPE_KEY_DETAIL_ACT_TO_STEP_FRAG = "RECIPE_KEY_DETAIL_ACT_TO_STEP_FRAG";
    public static final String STEP_ID_KEY_DETAIL_ACT_TO_STEP_FRAG = "STEP_ID_KEY_DETAIL_ACT_TO_STEP_FRAG";

    private Recipe mRecipe;
    private String mStepId;

    public StepFragment() {}

    public static StepFragment newInstance(Recipe recipe, String stepId) {
        StepFragment fragment = new StepFragment();
        Bundle args = new Bundle();
        args.putParcelable(RECIPE_KEY_DETAIL_ACT_TO_STEP_FRAG, recipe);
        args.putString(STEP_ID_KEY_DETAIL_ACT_TO_STEP_FRAG, stepId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRecipe = getArguments().getParcelable(RECIPE_KEY_DETAIL_ACT_TO_STEP_FRAG);
            mStepId = getArguments().getString(STEP_ID_KEY_DETAIL_ACT_TO_STEP_FRAG);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

}
