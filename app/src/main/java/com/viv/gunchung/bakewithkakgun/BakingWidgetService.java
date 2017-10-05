package com.viv.gunchung.bakewithkakgun;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.viv.gunchung.bakewithkakgun.utilities.BakingUtils;

/**
 * Created by Gunawan on 05/10/2017.
 */

public class BakingWidgetService extends IntentService {

    public static final String ACTION_UPDATE_BAKING_WIDGETS = "com.viv.gunchung.bakewithkakgun.action.update_baking_widgets";

    public BakingWidgetService() {
        super("BakingWidgetService");
    }

    public static void startActionUpdateBakingWidgets(Context context) {
        Intent intent = new Intent(context, BakingWidgetService.class);
        intent.setAction(ACTION_UPDATE_BAKING_WIDGETS);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_BAKING_WIDGETS.equals(action)) {
                handleActionUpdateBakingWidgets();
            }
        }
    }

    private void handleActionUpdateBakingWidgets() {

        String preferenceName = BakingUtils.BAKING_PREFERENCE;
        SharedPreferences shared = getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        String recipeName = (shared.getString(BakingUtils.BAKING_PREFERENCE_RECIPE_NAME, "name"));
        String recipeIng = (shared.getString(BakingUtils.BAKING_PREFERENCE_RECIPE_ING, "bahan-bahan"));

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingWidget.class));
        //Now update all widgets
        BakingWidget.updatePlantWidgets(this, appWidgetManager, appWidgetIds, recipeName, recipeIng);
    }


}
