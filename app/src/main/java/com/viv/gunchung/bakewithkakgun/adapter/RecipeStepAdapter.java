package com.viv.gunchung.bakewithkakgun.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viv.gunchung.bakewithkakgun.R;
import com.viv.gunchung.bakewithkakgun.models.RecipeStep;

import java.util.List;

/**
 * Created by Gunawan on 17/09/2017.
 */

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.RecipeStepAdapterViewHolder> {

    private List<RecipeStep> mRecipeStepList;

    private final RecipeStepAdapterOnClickHandler mClickHandler;

    public interface RecipeStepAdapterOnClickHandler {
        void onClick(RecipeStep selectedStep, int index);
    }

    public RecipeStepAdapter(RecipeStepAdapterOnClickHandler clickHandler, List<RecipeStep> recipeStepsList) {
        mClickHandler = clickHandler;
        mRecipeStepList = recipeStepsList;
    }

    public class RecipeStepAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView mStepNoTextView;
        public final TextView mStepDescTextView;
        public final CardView mStepCardView;

        public RecipeStepAdapterViewHolder(View view) {
            super(view);

            mStepNoTextView = (TextView) view.findViewById(R.id.tv_step_no_item);
            mStepDescTextView = (TextView) view.findViewById(R.id.tv_step_desc_item);
            mStepCardView = (CardView) view.findViewById(R.id.cv_step_item);

            mStepCardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            RecipeStep selectedStep = mRecipeStepList.get(adapterPosition);
            mClickHandler.onClick(selectedStep, adapterPosition);
        }
    }

    @Override
    public RecipeStepAdapter.RecipeStepAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.recipe_step_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        RecipeStepAdapter.RecipeStepAdapterViewHolder viewHolder = new RecipeStepAdapter.RecipeStepAdapterViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeStepAdapter.RecipeStepAdapterViewHolder holder, int position) {
        RecipeStep step = mRecipeStepList.get(position);

        String stepNo = String.valueOf(step.getId() + 1) + ".";
        holder.mStepNoTextView.setText(stepNo);
        holder.mStepDescTextView.setText(step.getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (null == mRecipeStepList) return 0;
        return mRecipeStepList.size();
    }

}
