package com.viv.gunchung.bakewithkakgun.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viv.gunchung.bakewithkakgun.R;
import com.viv.gunchung.bakewithkakgun.models.RecipeIngredient;

import java.util.List;

/**
 * Created by Gunawan on 30/09/2017.
 */

public class RecipeIngredientAdapter extends RecyclerView.Adapter<RecipeIngredientAdapter.RecipeIngredientAdapterViewHolder> {

    private List<RecipeIngredient> mRecipeIngredientList;

    public RecipeIngredientAdapter(List<RecipeIngredient> recipeIngredientsList) {
        mRecipeIngredientList = recipeIngredientsList;
    }

    public class RecipeIngredientAdapterViewHolder extends RecyclerView.ViewHolder {

        public final TextView mIngreQuantityTextView;
        public final TextView mIngreNameTextView;

        public RecipeIngredientAdapterViewHolder(View view) {
            super(view);

            mIngreQuantityTextView = (TextView) view.findViewById(R.id.tv_ingredient_quantity_item);
            mIngreNameTextView = (TextView) view.findViewById(R.id.tv_ingredient_name_item);

        }
    }

    @Override
    public RecipeIngredientAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.recipe_ingredient_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        RecipeIngredientAdapter.RecipeIngredientAdapterViewHolder viewHolder = new RecipeIngredientAdapter.RecipeIngredientAdapterViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeIngredientAdapterViewHolder holder, int position) {
        RecipeIngredient ingredient = mRecipeIngredientList.get(position);

        String quantity = ingredient.getQuantity() + " " +
                ingredient.getMeasure().substring(0, 1).toUpperCase() + ingredient.getMeasure().substring(1).toLowerCase();
        holder.mIngreQuantityTextView.setText(quantity);

        String name = ingredient.getIngredient().substring(0, 1).toUpperCase() + ingredient.getIngredient().substring(1).toLowerCase();
        holder.mIngreNameTextView.setText(name);
    }

    @Override
    public int getItemCount() {
        if (null == mRecipeIngredientList) return 0;
        return mRecipeIngredientList.size();
    }

}
