package com.viv.gunchung.bakewithkakgun;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String recipeName, String recipeIng) {

        // Create an Intent to launch MainActivity when clicked
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget);

        if (TextUtils.isEmpty(recipeName)) {
            views.setViewVisibility(R.id.widget_select_recipe, View.VISIBLE);
            views.setViewVisibility(R.id.widget_recipe_name, View.GONE);
            views.setViewVisibility(R.id.widget_recipe_ingredients, View.GONE);

            // Widgets allow click handlers to only launch pending intents
            views.setOnClickPendingIntent(R.id.widget_select_recipe, pendingIntent);
        } else {
            views.setViewVisibility(R.id.widget_select_recipe, View.GONE);
            views.setViewVisibility(R.id.widget_recipe_name, View.VISIBLE);
            views.setViewVisibility(R.id.widget_recipe_ingredients, View.VISIBLE);

            views.setTextViewText(R.id.widget_recipe_name, recipeName);
            views.setTextViewText(R.id.widget_recipe_ingredients, recipeIng);
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updatePlantWidgets(Context context, AppWidgetManager appWidgetManager,
                                          int[] appWidgetIds, String recipeName, String recipeIng) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipeName, recipeIng);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        BakingWidgetService.startActionUpdateBakingWidgets(context);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // Perform any action when one or more AppWidget instances have been deleted
    }

    @Override
    public void onEnabled(Context context) {
        // Perform any action when an AppWidget for this provider is instantiated
        BakingWidgetService.startActionUpdateBakingWidgets(context);
    }

    @Override
    public void onDisabled(Context context) {
        // Perform any action when the last AppWidget instance for this provider is deleted
    }
}
