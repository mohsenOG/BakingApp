package com.oghbaei.bakingapp;


import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.oghbaei.bakingapp.queryModel.Recipe;
import com.oghbaei.bakingapp.queryModel.Step;

import java.util.Objects;

import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;


public class StepFragment extends Fragment {

    private static final String RECIPE_KEY_DETAIL_ACT_TO_STEP_FRAG = "RECIPE_KEY_DETAIL_ACT_TO_STEP_FRAG";
    private static final String STEP_ID_KEY_DETAIL_ACT_TO_STEP_FRAG = "STEP_ID_KEY_DETAIL_ACT_TO_STEP_FRAG";
    private static final String SAVE_STATE_PLAYER_PLAYED_WHEN_READY = "SAVE_STATE_PLAYER_PLAYED_WHEN_READY";
    private static final String SAVE_STATE_PAUSE_TIME = "SAVE_STATE_PAUSE_TIME";

    @BindView(R.id.sepv_video_player) SimpleExoPlayerView mExoPlayerView;
    @BindView(R.id.tv_step_description) TextView mDescriptionTextView;
    @BindView(R.id.btn_previous_step) Button mPreviousStepButton;
    @BindView(R.id.btn_next_step) Button mNextStepButton;

    private OnNextPreviousStepClickedListener mListener;
    private Recipe mRecipe;
    private String mStepId;
    private SimpleExoPlayer mSimpleExpPlayer;
    @BindBool(R.bool.isLarge) boolean mIsLargeScreen;
    private boolean mIsPlayerReady = true;
    private long mPlayerPausePosition = -1;
    private boolean mIsPlayerPaused = false;

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

        if (mIsLargeScreen) {
          mPreviousStepButton.setVisibility(View.GONE);
          mNextStepButton.setVisibility(View.GONE);
        } else {
            mPreviousStepButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int stepIdInt = Integer.valueOf(mStepId);
                    if (stepIdInt > 0) {
                        int newStepId = stepIdInt - 1;
                        mListener.onPreviousStepClicked(String.valueOf(newStepId));
                    } else {
                        Toast.makeText(getContext(), getString(R.string.no_previous_step), Toast.LENGTH_LONG).show();
                    }
                }
            });
            mNextStepButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int lastStepInt = mRecipe.listOfStepIds().get(mRecipe.listOfStepIds().size() - 1);
                    int stepIdInt = Integer.valueOf(mStepId);
                    if (stepIdInt < lastStepInt) {
                        int newStepId = stepIdInt + 1;
                        mListener.onNextStepClicked(String.valueOf(newStepId));
                    } else {
                        Toast.makeText(getContext(), getString(R.string.no_next_step), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        if (savedInstanceState != null) {
            mIsPlayerPaused = true;
            mIsPlayerReady = savedInstanceState.getBoolean(SAVE_STATE_PLAYER_PLAYED_WHEN_READY, true);
            mPlayerPausePosition = savedInstanceState.getLong(SAVE_STATE_PAUSE_TIME, -1);
        }

        initDescriptionView();
        initMediaPlayer();

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        boolean isReady = mSimpleExpPlayer.getPlayWhenReady();
        outState.putBoolean(SAVE_STATE_PLAYER_PLAYED_WHEN_READY, isReady);
        long pauseTime = mSimpleExpPlayer.getCurrentPosition();
        outState.putLong(SAVE_STATE_PAUSE_TIME, pauseTime);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mIsLargeScreen = getResources().getBoolean(R.bool.isLarge);
        if (context instanceof OnNextPreviousStepClickedListener) {
            mListener = (OnNextPreviousStepClickedListener) context;
        }
        else if (!mIsLargeScreen){
                throw new RuntimeException(context.toString() + " must implement OnNextPreviousStepClickedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mPreviousStepButton.setVisibility(View.VISIBLE);
        mNextStepButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStart() {
        super.onStart();
        initMediaPlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        initMediaPlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void releasePlayer() {
        if (mSimpleExpPlayer != null) {
            mSimpleExpPlayer.stop();
            mSimpleExpPlayer.release();
            mSimpleExpPlayer = null;
        }
    }

    private void initDescriptionView() {
        Step step = mRecipe.getStep(Integer.valueOf(mStepId));
        String description = step.getDescription();
        if (description == null || description.isEmpty()) {
            description = getString(R.string.no_description);
        }
        mDescriptionTextView.setText(description);
    }

    private void initMediaPlayer() {
        Step step = mRecipe.getStep(Integer.valueOf(mStepId));
        String video = step.getVideoURL();
        if (video == null || video.isEmpty()) {
            mExoPlayerView.setVisibility(View.GONE);
            return;
        }
        mExoPlayerView.setVisibility(View.VISIBLE);
        Uri videoUri = Uri.parse(video);
        if (mSimpleExpPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mSimpleExpPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mExoPlayerView.setPlayer(mSimpleExpPlayer);
            // Prepare media source
            String userAgent = Util.getUserAgent(getContext(), "StepVideo");
            MediaSource mediaSource = new ExtractorMediaSource(videoUri, new DefaultDataSourceFactory(
                    Objects.requireNonNull(getContext()), userAgent), new DefaultExtractorsFactory(), null, null);
            mSimpleExpPlayer.prepare(mediaSource);
            mSimpleExpPlayer.setPlayWhenReady(mIsPlayerReady);

            if (mIsPlayerPaused) {
                mSimpleExpPlayer.seekTo(mPlayerPausePosition);
            }

            // Check to show it full screen or normal
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE && !mIsLargeScreen) {
                mExoPlayerView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                mExoPlayerView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                mDescriptionTextView.setVisibility(View.GONE);
                mNextStepButton.setVisibility(View.GONE);
                mPreviousStepButton.setVisibility(View.GONE);
                Objects.requireNonNull(getActivity()).getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE);
            }
        }

    }

    public interface OnNextPreviousStepClickedListener {
        void onPreviousStepClicked(String previousStepId);
        void onNextStepClicked(String NextStepId);
    }
}