package com.tanushree.bestreads.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.RemoteViews;

import com.tanushree.bestreads.MainActivity;
import com.tanushree.bestreads.R;

/**
 * Implementation of App Widget functionality.
 */

public class BooksWidgetProvider extends AppWidgetProvider {

    //private static final String TAG = BooksWidgetProvider.class.getSimpleName();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.books_widget_provider);

        // Populate the TextView.
        SharedPreferences sharedPreferences = context.getSharedPreferences(MainActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
        String category = sharedPreferences.getString(MainActivity.KEY_CATEGORY, null);
        views.setTextViewText(R.id.tvWidgetCategoryName, category);

        // Populate the Books ListView.
        Intent intentListWidgetService = new Intent(context, ListWidgetService.class);
        intentListWidgetService.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intentListWidgetService.setData(Uri.parse(intentListWidgetService.toUri(Intent.URI_INTENT_SCHEME)));
        views.setRemoteAdapter(R.id.lvWidgetBooks, intentListWidgetService);
        // For ListView.
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.lvWidgetBooks);
        views.setScrollPosition(R.id.lvWidgetBooks, 0);

        // Create an Intent to launch MainActivity.
        Intent intentMainActivity = new Intent(context, MainActivity.class);
        PendingIntent pendingIntentMainActivity = PendingIntent
                .getActivity(context, 0, intentMainActivity, 0);
        views.setOnClickPendingIntent(R.id.tvWidgetCategoryName, pendingIntentMainActivity);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    // This method is called once a new widget is created, as well as every update interval.
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

