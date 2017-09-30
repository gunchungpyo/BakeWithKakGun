package com.viv.gunchung.bakewithkakgun.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.viv.gunchung.bakewithkakgun.R;
import com.viv.gunchung.bakewithkakgun.models.Recipe;
import com.viv.gunchung.bakewithkakgun.utilities.BakingUtils;

import org.parceler.Parcels;

import timber.log.Timber;

/**
 * Created by Gunawan on 17/09/2017.
 */

public class RecipeIntroFragment extends Fragment {

    private Recipe mSelected;

    public RecipeIntroFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Timber.d("onCreateView start");

        if (savedInstanceState != null) {
            String parcelKey = BakingUtils.SELECTED_RECIPE_KEY;
            mSelected = Parcels.unwrap(savedInstanceState.getParcelable(parcelKey));
        }

        final View rootView = inflater.inflate(R.layout.fragment_recipe_intro, container, false);
        final ImageView imageView = (ImageView) rootView.findViewById(R.id.iv_recipe_poster);

        final TextView ingredients = (TextView) rootView.findViewById(R.id.tv_recipe_ingredient);
        final TextView steps = (TextView) rootView.findViewById(R.id.tv_recipe_step);
        final TextView serving = (TextView) rootView.findViewById(R.id.tv_recipe_serving);

        if (mSelected != null) {

            if (BakingUtils.isValidUrl(mSelected.getImage())) {
                Context context = getActivity().getApplicationContext();
                Picasso.with(context).load(mSelected.getImage()).into(imageView);
            }

            String totalIngredients = String.valueOf(mSelected.getIngredients().size());
            ingredients.setText(totalIngredients);

            String totalSteps = String.valueOf(mSelected.getSteps().size());
            steps.setText(totalSteps);

            String totalServing = String.valueOf(mSelected.getServings());
            serving.setText(totalServing);

        } else {
            Timber.e("Empty bundle !!!");
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        String parcelKey = BakingUtils.SELECTED_RECIPE_KEY;
        currentState.putParcelable(parcelKey, Parcels.wrap(mSelected));
    }

    public void setSelectedRecipe(Recipe mSelected) {
        this.mSelected = mSelected;
    }
}
