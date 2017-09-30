package com.viv.gunchung.bakewithkakgun.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.viv.gunchung.bakewithkakgun.R;
import com.viv.gunchung.bakewithkakgun.models.Recipe;
import com.viv.gunchung.bakewithkakgun.utilities.BakingUtils;

import java.util.List;

/**
 * Created by gunawan on 17/09/17.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {

    private Context mContext;
    private List<Recipe> mRecipeList;

    private final RecipeAdapterOnClickHandler mClickHandler;

    public interface RecipeAdapterOnClickHandler {
        void onClick(Recipe selectedRecipe);
    }

    public RecipeAdapter(Context context, RecipeAdapterOnClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
    }

    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView mPosterImageView;
        public final TextView mRecipeTitleTextView;

        public RecipeAdapterViewHolder(View view) {
            super(view);
            mRecipeTitleTextView = (TextView) view.findViewById(R.id.tv_recipe_title_item);
            mPosterImageView = (ImageView) view.findViewById(R.id.iv_recipe_poster_item);

            mPosterImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Recipe selectedRecipe = mRecipeList.get(adapterPosition);
            mClickHandler.onClick(selectedRecipe);
        }
    }

    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.recipe_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        RecipeAdapterViewHolder viewHolder = new RecipeAdapterViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeAdapterViewHolder holder, int position) {
        Recipe recipe = mRecipeList.get(position);

        holder.mRecipeTitleTextView.setText(recipe.getName());

        if (BakingUtils.isValidUrl(recipe.getImage())) {
            Picasso.with(mContext).load(recipe.getImage()).into(holder.mPosterImageView);
        } else {
            holder.mPosterImageView.setBackgroundColor(Color.parseColor("#FFD5D3D5"));
        }
    }

    @Override
    public int getItemCount() {
        if (null == mRecipeList) return 0;
        return mRecipeList.size();
    }

    public void setRecipeList(List<Recipe> recipeList) {
        mRecipeList = recipeList;
        notifyDataSetChanged();
    }

}
