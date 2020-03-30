package com.abhishek.bakingapp.widget;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.widget.RemoteViews;

import com.abhishek.bakingapp.R;
import com.abhishek.bakingapp.RecipeDetailsActivity;
import com.abhishek.bakingapp.model.Ingredient;
import com.abhishek.bakingapp.model.Recipe;
import com.abhishek.bakingapp.utils.ConstantsUtil;
import com.google.gson.Gson;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

public class Service extends IntentService {

    public static final String ACTION_OPEN_RECIPE =
            "com.abhishek.bakingapp.widget";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public Service(String name) {
        super(name);
    }

    public Service() {
        super("BakersBowlService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "abc";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Bakers Bowl",
                    NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("")
                    .setContentText("").build();

            startForeground(1, notification);
        }
    }

    @Override
    protected void onHandleIntent(@NonNull Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_OPEN_RECIPE.equals(action)) {
                handleActionOpenRecipe();
            }
        }
    }

    private void handleActionOpenRecipe() {
        SharedPreferences sharedpreferences =
                getSharedPreferences(ConstantsUtil.SHARED_PREF,MODE_PRIVATE);
        String jsonRecipe = sharedpreferences.getString(ConstantsUtil.JSON_RESULT_EXTRA, "");
        StringBuilder stringBuilder = new StringBuilder();
        Gson gson = new Gson();
        Recipe recipe = gson.fromJson(jsonRecipe, Recipe.class);
        int id= recipe.getId();
        int imgResId = ConstantsUtil.recipeIcons[id-1];
        List<Ingredient> ingredientList = recipe.getIngredients();
        for(Ingredient ingredient : ingredientList){
            String quantity = String.valueOf(ingredient.getQuantity());
            String measure = ingredient.getMeasure();
            String ingredientName = ingredient.getIngredient();
            String line = quantity + " " + measure + " " + ingredientName;
            stringBuilder.append( line + "\n");
        }
        String ingredientsString = stringBuilder.toString();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, Provider.class));
        Provider.updateWidgetRecipe(this, ingredientsString, imgResId, appWidgetManager, appWidgetIds);
    }

    public static void startActionOpenRecipe(Context context) {
        Intent intent = new Intent(context, Service.class);
        intent.setAction(ACTION_OPEN_RECIPE);
        context.startService(intent);
    }

    public static void startActionOpenRecipeO(Context context){
        Intent intent = new Intent(context, Service.class);
        intent.setAction(ACTION_OPEN_RECIPE);
        ContextCompat.startForegroundService(context,intent);
    }
}