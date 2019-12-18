package com.tanushree.bestreads;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.tanushree.bestreads.model.Book;
import com.tanushree.bestreads.model.JSONData;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String BASE_URL = "https://api.nytimes.com/svc/books/v3/lists/";
    private static final String api_key = BuildConfig.NYTIMES_API_KEY;
    private static final String ITEM_POSITION_EXTRA = "item_position";

    private static final String categoryHardcoverFiction = "hardcover-fiction";
    private static final String categoryPaperbackFiction = "trade-fiction-paperback";
    private static final String categoryHardcoverNonfiction = "hardcover-nonfiction";
    private static final String categoryPaperbackNonfiction = "paperback-nonfiction";
    private static final String categoryHardcoverAdvice = "hardcover-advice";
    private static final String categoryPaperbackAdvice = "paperback-advice";
    private static final String categoryBusiness = "business-books";
    private static final String categoryScience = "science";
    private static final String categoryChildrenBooks = "picture-books";
    private static final String categoryWishList = "wish-list";

    public static final String KEY_JSON = "json_string";
    public static final String KEY_CATEGORY = "books_category";

    private static String mCategory = null;

    private RecyclerView mBooksRv;
    private BooksAdapter mBooksAdapter;
    // Persists the RecyclerView scroll position.
    private int mFirstVisibleItemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBooksRv = findViewById(R.id.rvBooks);

        boolean landscape = getResources().getBoolean(R.bool.isLandscape);

        int gridColumns;

        if(landscape)
            gridColumns = 3;
        else
            gridColumns = 2;

        GridLayoutManager layoutManager = new GridLayoutManager(this, gridColumns,
                GridLayoutManager.VERTICAL, false);

        mBooksRv.setLayoutManager(layoutManager);
        mBooksRv.setHasFixedSize(true);

        mBooksAdapter = new BooksAdapter();
        mBooksRv.setAdapter(mBooksAdapter);

        final String default_category = categoryHardcoverFiction;

        if (mCategory == null)
            mCategory = default_category;

        loadBooksData();
    }

    private void loadBooksData() {
        //Log.d (TAG, "loadBooksData called");

        setTheTitle();

        if (mCategory.equals(categoryWishList)) {

        }

        else {
            if (isNetworkAvailable()) {

                // Create a Retrofit object.
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                NyTimesClientAPI nyTimesClientAPI = retrofit.create(NyTimesClientAPI.class);

                Call<JSONData> call = nyTimesClientAPI.getData(mCategory, api_key);

                call.enqueue(new Callback<JSONData>() {
                    @Override
                    public void onResponse(Call<JSONData> call, Response<JSONData> response) {
                        //Log.d(TAG, "onResponse: Server Response: " + response.toString());
                        //Log.d(TAG, "onResponse: received information: " + response.body().toString());

                        ArrayList<Book> bookList = response.body().getResults().getBooks();

                    /*for (int i = 0; i < bookList.size(); i++) {
                        Log.d(TAG, "onResponse: \n" +
                                "isbn: " + bookList.get(i).getIsbn() + "\n" +
                                "title: " + bookList.get(i).getTitle() + "\n" +
                                "author: " + bookList.get(i).getAuthor() + "\n" +
                                "rank: " + bookList.get(i).getRank() + "\n" +
                                "description: " + bookList.get(i).getDescription() + "\n" +
                                "--------------------------------------------------------\n\n");
                        }*/

                        mBooksAdapter.setBooksData(bookList);

                        // Restore RecyclerView Scroll Position.
                        // Note: Must be called AFTER the adapter data has been set.
                        mBooksRv.getLayoutManager().scrollToPosition(mFirstVisibleItemPosition);
                    }

                    @Override
                    public void onFailure(Call<JSONData> call, Throwable t) {
                        //Log.e(TAG, "onFailure: Something went wrong: " + t.getMessage());
                        Toast.makeText(MainActivity.this, R.string.no_response_from_server,
                                Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText
                        (this, R.string.network_unavailable_message, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void setTheTitle ()
    {
        //Log.d (TAG, "setTheTitle called");
        //SharedPreferences.Editor editor = mSharedPreferences.edit();
        switch(mCategory)
        {
            case categoryHardcoverFiction:
                setTitle(R.string.main_menu_hardcover_fiction);
                //editor.putString(KEY_CATEGORY, getString(R.string.main_menu_hardcover_fiction));
                break;

            case categoryPaperbackFiction:
                setTitle(R.string.main_menu_paperback_fiction);
                //editor.putString(KEY_CATEGORY, getString(R.string.main_menu_paperback_fiction));
                break;

            case categoryHardcoverNonfiction:
                setTitle(R.string.main_menu_hardcover_nonfiction);
                //editor.putString(KEY_CATEGORY, getString(R.string.main_menu_hardcover_nonfiction));
                break;

            case categoryPaperbackNonfiction:
                setTitle(R.string.main_menu_paperback_nonfiction);
                //editor.putString(KEY_CATEGORY, getString(R.string.main_menu_paperback_nonfiction));
                break;

            case categoryHardcoverAdvice:
                setTitle(R.string.main_menu_hardcover_advice);
                //editor.putString(KEY_CATEGORY, getString(R.string.main_menu_hardcover_advice));
                break;

            case categoryPaperbackAdvice:
                setTitle(R.string.main_menu_paperback_advice);
                //editor.putString(KEY_CATEGORY, getString(R.string.main_menu_paperback_advice));
                break;

            case categoryBusiness:
                setTitle(R.string.main_menu_business);
                //editor.putString(KEY_CATEGORY, getString(R.string.main_menu_business));
                break;

            case categoryScience:
                setTitle(R.string.main_menu_science);
                //editor.putString(KEY_CATEGORY, getString(R.string.main_menu_science));
                break;

            case categoryChildrenBooks:
                setTitle(R.string.main_menu_children_books);
                //editor.putString(KEY_CATEGORY, getString(R.string.main_menu_children_books));
                break;

            case categoryWishList:
                setTitle(R.string.main_menu_wish_list);
                //editor.putString(KEY_CATEGORY, getString(R.string.main_menu_wish_list));
                break;

            default:
                setTitle("Best Reads");
                //editor.putString(KEY_CATEGORY, getString(R.string.app_name));
        }
        //editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //Log.d (TAG, "OnOptionsItemSelected called");

        mFirstVisibleItemPosition = 0;

        int id = item.getItemId();

        switch(id)
        {
            case R.id.action_hardcoverFiction:
                mCategory = categoryHardcoverFiction;
                mBooksAdapter.setBooksData(null);
                loadBooksData();
                return true;

            case R.id.action_paperbackFiction:
                mCategory = categoryPaperbackFiction;
                mBooksAdapter.setBooksData(null);
                loadBooksData();
                return true;

            case R.id.action_hardcoverNonfiction:
                mCategory = categoryHardcoverNonfiction;
                mBooksAdapter.setBooksData(null);
                loadBooksData();
                return true;

            case R.id.action_paperbackNonfiction:
                mCategory = categoryPaperbackNonfiction;
                mBooksAdapter.setBooksData(null);
                loadBooksData();
                return true;

            case R.id.action_hardcoverAdvice:
                mCategory = categoryHardcoverAdvice;
                mBooksAdapter.setBooksData(null);
                loadBooksData();
                return true;

            case R.id.action_paperbackAdvice:
                mCategory = categoryPaperbackAdvice;
                mBooksAdapter.setBooksData(null);
                loadBooksData();
                return true;

            case R.id.action_business:
                mCategory = categoryBusiness;
                mBooksAdapter.setBooksData(null);
                loadBooksData();
                return true;

            case R.id.action_science:
                mCategory = categoryScience;
                mBooksAdapter.setBooksData(null);
                loadBooksData();
                return true;

            case R.id.action_childrenBooks:
                mCategory = categoryChildrenBooks;
                mBooksAdapter.setBooksData(null);
                loadBooksData();
                return true;

            case R.id.action_wishList:
                mCategory = categoryWishList;
                mBooksAdapter.setBooksData(null);
                loadBooksData();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return (activeNetworkInfo != null && activeNetworkInfo.isConnected());

    }

    @Override
    protected void onPause() {
        super.onPause();

        // Save the RecyclerView scroll position (& restore it in loadBooksData method).
        mFirstVisibleItemPosition = ((GridLayoutManager)mBooksRv.getLayoutManager())
                .findFirstVisibleItemPosition();

        //Log.d(TAG, "Visible position: " + mFirstVisibleItemPosition);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ITEM_POSITION_EXTRA, mFirstVisibleItemPosition);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState!=null) {
            mFirstVisibleItemPosition = savedInstanceState.getInt(ITEM_POSITION_EXTRA);
            //Log.d(TAG, "Position in onRestore method: " + mFirstVisibleItemPosition);
        }
    }
}