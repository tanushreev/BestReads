package com.tanushree.bestreads;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.tanushree.bestreads.model.Book;
import com.tanushree.bestreads.model.JSONData;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String BASE_URL = "https://api.nytimes.com/svc/books/v3/lists/";
    private static final String api_key = BuildConfig.NYTIMES_API_KEY;

    private static final String categoryHardcoverFiction = "hardcover-fiction";

    public static final String KEY_JSON = "json_string";
    public static final String KEY_CATEGORY = "books_category";

    private static String mCategory = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String default_category = categoryHardcoverFiction;

        if (mCategory == null)
            mCategory = default_category;

        loadBooksData();
    }

    private void loadBooksData() {
        //Log.d (TAG, "loadBooksData called");

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

    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return (activeNetworkInfo != null && activeNetworkInfo.isConnected());

    }
}