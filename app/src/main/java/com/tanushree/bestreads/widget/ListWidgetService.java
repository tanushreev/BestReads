package com.tanushree.bestreads.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

// RemoteViewsService is a service that connects a remote adapter to be able to request remote views.
// It is registered in the manifest.

public class ListWidgetService extends RemoteViewsService
{
    //private static final String TAG = ListWidgetService.class.getSimpleName();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        //Log.d(TAG, "onGetViewFactory() in RemoteViewsService called");
        return new ListRemoteViewsFactory(this.getApplicationContext());
    }
}