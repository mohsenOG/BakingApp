package com.oghbaei.bakingapp.Utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.oghbaei.bakingapp.BakingAppWidget;
import com.oghbaei.bakingapp.queryModel.Ingredient;
import com.oghbaei.bakingapp.queryModel.Recipe;

import java.util.List;


/**
 * Created by Mohsen on 19.04.2018.
 *
 */

public class Utils {
    private Utils() {}

    /**
     * @return true if online otherwise false.
     * @see https://stackoverflow.com/a/4009133/6072457
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) return false;
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    /**
     *
     * @param ingredients
     * @return string consist of ingredients with line break at the end of each ingredient.
     */
    public static String getIngredients(List<Ingredient> ingredients) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Ingredient i : ingredients) {
            String ingredientName = i.getIngredient();
            if (ingredientName != null && !ingredientName.isEmpty()) {
                stringBuilder.append(ingredientName).append(" ");
                String measure = i.getMeasure();
                if (measure != null && !measure.isEmpty()) {
                    stringBuilder.append(measure).append(" ");
                }
                String quantity = i.getQuantity();
                if (quantity != null && !quantity.isEmpty()) {
                    stringBuilder.append(quantity);
                }
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    public static void sendRecipeToWidget(Context context, Recipe recipe) {
        Intent intent = new Intent(BakingAppWidget.ACTION_INGREDIENTS_CHANGED);
        //intent.putExtra(RECIPE_KEY_FROM_EVERYWHERE_TO_WIDGET, recipe);
        context.sendBroadcast(intent);
    }
}
