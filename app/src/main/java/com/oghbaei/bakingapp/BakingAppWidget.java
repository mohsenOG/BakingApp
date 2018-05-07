package com.oghbaei.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

    public static final String ACTION_INGREDIENTS_CHANGED = "ACTION_INGREDIENTS_CHANGED";
    private String mIngredients;


    public static void updateAppWidget(Context context,AppWidgetManager appWidgetManager, int appWidgetId, String ingredients) {
                RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
                if (ingredients != null && !ingredients.isEmpty()) {
                    remoteViews.setTextViewText(R.id.tv_appwidget, ingredients);
                } else {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("MyPref", 0);
                    ingredients = sharedPreferences.getString(context.getString(R.string.shared_preference_ingredients), context.getString(R.string.no_widget_text));
                    remoteViews.setTextViewText(R.id.tv_appwidget, ingredients);
                }
                // Click listener
                Intent clickIntent = new Intent(context, RecipeActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, clickIntent, 0);
                remoteViews.setOnClickPendingIntent(R.id.tv_appwidget, pendingIntent);

                appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction().equals(ACTION_INGREDIENTS_CHANGED)) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("MyPref", 0);
            mIngredients = sharedPreferences.getString(context.getString(R.string.shared_preference_ingredients), context.getString(R.string.no_widget_text));
        } else {
            super.onReceive(context, intent);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, mIngredients);
        }
    }

    @Override
    public void onEnabled(Context context) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPref", 0);
        String ingredients = sharedPreferences.getString(context.getString(R.string.shared_preference_ingredients), context.getString(R.string.no_widget_text));
        remoteViews.setTextViewText(R.id.tv_appwidget, ingredients);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

