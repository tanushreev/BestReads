package com.tanushree.bestreads.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.tanushree.bestreads.MainActivity;
import com.tanushree.bestreads.R;
import com.tanushree.bestreads.model.Book;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

// RemoteViewsFactory is a thin wrapper that goes around an adapter.

public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    //private static final String TAG = ListRemoteViewsFactory.class.getSimpleName();

    private Context mContext;
    private SharedPreferences mSharedPreferences;
    private List<Book> mBookList;


    public ListRemoteViewsFactory(Context applicationContext)
    {
        //Log.d(TAG, "ListRemoteViewsFactory constructor called");
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

        mSharedPreferences = mContext.getSharedPreferences(MainActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
        String booksJsonString = mSharedPreferences.getString(MainActivity.KEY_JSON, null);
        Type type = new TypeToken<List< Book >>() {}.getType();
        mBookList = new Gson().fromJson(booksJsonString, type);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(mBookList == null)
            return 0;
        return mBookList.size();
    }

    // Binds the data and view.
    @Override
    public RemoteViews getViewAt(int position) {

        //Log.d(TAG, "getViewAt() Called");

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_books_list_item);
        Book book = mBookList.get(position);
        if(book.getTitle().isEmpty())
            rv.setTextViewText(R.id.tvWidgetBook, mContext.getString(R.string.data_not_available));
        else
            rv.setTextViewText(R.id.tvWidgetBook, book.getTitle());

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
