package com.tanushree.bestreads;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    //private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String RATING_BASE_URL = "https://www.goodreads.com/book/review_counts.json";

    private static final String KEY_PARAM_API = "key";

    private static final String KEY_PARAM_ISBNS = "isbns";

    private static final String api_key_value = BuildConfig.GOODREADS_API_KEY;

    public static URL buildUrl(String isbn)
    {
        Uri builtUri = Uri.parse(RATING_BASE_URL).buildUpon()
                .appendQueryParameter(KEY_PARAM_API, api_key_value)
                .appendQueryParameter(KEY_PARAM_ISBNS, isbn)
                .build();

        URL url = null;
        try
        {
            url = new URL(builtUri.toString());
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }

        //Log.d(TAG, "Built url" + url);

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException
    {
        //Log.d(TAG, "In method getResponseFromHttpUrl");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        }
        finally {
            urlConnection.disconnect();
        }
    }
}