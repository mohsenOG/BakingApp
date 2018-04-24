package com.oghbaei.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oghbaei.bakingapp.queryModel.Recipe;
import com.oghbaei.bakingapp.queryModel.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mohsen on 23.04.2018.
 *
 */

public class RecipeDetailRecyclerViewAdapter extends RecyclerView.Adapter<RecipeDetailRecyclerViewAdapter.ViewHolder> {

    private List<Step> mSteps;
    private Context mContext;
    private StepClickListener mStepClickListener;

    public RecipeDetailRecyclerViewAdapter(List<Step> steps, Context context) {
        mStepClickListener = null;
        mSteps = steps;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_recipe_detail, parent, false);
        return new ViewHolder(v, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindingData(mSteps.get(position));
    }

    @Override
    public int getItemCount() { return mSteps.size(); }

    void setStepClickListener(StepClickListener StepClickListener) {
        mStepClickListener = StepClickListener;
    }

    public interface StepClickListener {
        void onStepClick(String StepId);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_step_description) TextView stepDescription;
        private Context mContext;
        private String stepId;

        public ViewHolder(View itemView, Context context) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            stepId = null;
            mContext = context;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mStepClickListener != null && !stepId.isEmpty()) {
                        mStepClickListener.onStepClick(stepId);
                    }
                }
            });
        }


        void bindingData(Step step) {
            String stepShortDescription = step.getShortDescription();
            if (stepShortDescription != null && !stepShortDescription.isEmpty()) {
                stepId = step.getId();
                stepDescription.setText(stepShortDescription);
            } else {
                stepId = null;
                stepDescription.setText(mContext.getString(R.string.no_name));
            }
        }
    }
}
