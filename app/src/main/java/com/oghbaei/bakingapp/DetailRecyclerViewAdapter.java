package com.oghbaei.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oghbaei.bakingapp.queryModel.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mohsen on 23.04.2018.
 *
 */

public class DetailRecyclerViewAdapter extends RecyclerView.Adapter<DetailRecyclerViewAdapter.ViewHolder> {

    private List<Step> mSteps;
    private Context mContext;
    private DetailClickListener mDetailClickListener;

    public DetailRecyclerViewAdapter(List<Step> steps, Context context) {
        mDetailClickListener = null;
        mSteps = steps;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_detail, parent, false);
        return new ViewHolder(v, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindingData(mSteps.get(position));
    }

    @Override
    public int getItemCount() { return mSteps.size(); }

    void setStepClickListener(DetailClickListener DetailClickListener) {
        mDetailClickListener = DetailClickListener;
    }

    public interface DetailClickListener {
        void onStepClick(String StepId);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_step_description) TextView mStepDescription;
        private Context mContext;
        private String stepId;

        public ViewHolder(final View itemView, Context context) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            stepId = null;
            mContext = context;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDetailClickListener != null && !stepId.isEmpty()) {
                        mDetailClickListener.onStepClick(stepId);
                    }
                }
            });
        }

        void bindingData(Step step) {
            // TODO changing background color
            String stepShortDescription = step.getShortDescription();
            if (stepShortDescription != null && !stepShortDescription.isEmpty()) {
                stepId = step.getId();
                mStepDescription.setText(stepShortDescription);
            } else {
                stepId = null;
                mStepDescription.setText(mContext.getString(R.string.no_name));
            }
        }
    }
}