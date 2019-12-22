package com.tanushree.bestreads;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.squareup.picasso.Picasso;
import com.tanushree.bestreads.model.AppDatabase;
import com.tanushree.bestreads.model.AppExecutors;
import com.tanushree.bestreads.model.Book;

public class DetailActivity extends AppCompatActivity {

    private Book mBook;

    private ImageView mBookCoverIv;
    private TextView mRankTv;
    private TextView mWeeksOnListTv;
    private TextView mRatingTv;
    private Button mWishListButton;
    private TextView mTitleTv;
    private TextView mAuthorTv;
    private TextView mDescriptionTv;
    private Button mAmazonButton;
    private AdView mAdView;
    private Toolbar mToolbar;

    // Member variable for the database.
    private AppDatabase mDatabase;
    private boolean mWishlistFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mBookCoverIv = findViewById(R.id.ivBookCover);
        mRankTv = findViewById(R.id.tvRank);
        mWeeksOnListTv = findViewById(R.id.tvWeeksOnList);
        mRatingTv = findViewById(R.id.tvRating);
        mWishListButton = findViewById(R.id.bWishList);
        mTitleTv = findViewById(R.id.tvTitle);
        mAuthorTv = findViewById(R.id.tvAuthor);
        mDescriptionTv = findViewById(R.id.tvDescription);
        mAmazonButton = findViewById(R.id.bAmazon);
        mAdView = findViewById(R.id.adView);
        mToolbar = findViewById(R.id.toolbarDetailActivity);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize Mobile Ads SDK
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mDatabase = AppDatabase.getInstance(getApplicationContext());

        Intent intentThatStartedThisActivity = getIntent();

        if(intentThatStartedThisActivity!=null) {
            if (intentThatStartedThisActivity.hasExtra(MainActivity.KEY_BOOK_DATA)) {
                mBook = intentThatStartedThisActivity.getParcelableExtra(MainActivity.KEY_BOOK_DATA);

                setTitle(R.string.detail_activity_title);

                populateUI();

                final String bookIsbn = mBook.getIsbn();

                // Create an instance of ViewModelFactory.
                DetailViewModelFactory factory = new DetailViewModelFactory(mDatabase, bookIsbn);
                // Create the ViewModel.
                final DetailViewModel viewModel =
                        ViewModelProviders.of(this, factory).get(DetailViewModel.class);

                viewModel.getBook().observe(this, new Observer<Book>() {
                    @Override
                    public void onChanged(Book book) {
                        //Log.d(TAG, "Receiving database update from LiveData");
                        // If the book is found in the database table.
                        if(book!=null)
                        {
                            mWishlistFlag = true;
                            mWishListButton.setText(R.string.detail_remove_wishlist_button_text);
                        }
                    }
                });

                mAmazonButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mBook.getAmazonUrl().equals(""))
                            Toast.makeText(DetailActivity.this, R.string.amazon_link_not_found, Toast.LENGTH_SHORT).show();
                        else {
                            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mBook.getAmazonUrl()));
                            startActivity(webIntent);
                        }
                    }
                });

                mWishListButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(mWishlistFlag == false)
                        {
                            // Insert in the database table.
                            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                @Override
                                public void run() {
                                    mDatabase.bookDao().insertBook(mBook);
                                }
                            });
                            mWishlistFlag = true;
                            Toast.makeText(DetailActivity.this, R.string.added_to_wish_list, Toast.LENGTH_LONG).show();

                            mWishListButton.setText(R.string.detail_remove_wishlist_button_text);
                        }

                        else
                        {
                            // Delete from the database table.
                            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                @Override
                                public void run() {
                                    mDatabase.bookDao().deleteBook(mBook);
                                }
                            });
                            mWishlistFlag = false;
                            Toast.makeText(DetailActivity.this, R.string.removed_from_wish_list,Toast.LENGTH_LONG).show();

                            mWishListButton.setText(R.string.detail_add_wishlist_button_text);
                        }
                    }
                });
            }
        }

    }

    private void populateUI()
    {
        // Book cover
        Picasso.get()
                .load(mBook.getBookImage())
                .error(R.drawable.book_cover_error)
                .into(mBookCoverIv);

        // Rank
        String rank = "Rank: " + mBook.getRank();
        mRankTv.setText(rank);

        // Weeks on list
        String weeksOnList = "Weeks on list: " + mBook.getWeeksOnList();
        mWeeksOnListTv.setText(weeksOnList);

        // Book title
        mTitleTv.setText(mBook.getTitle());
        // Author
        mAuthorTv.setText(mBook.getAuthor());

        // Book description
        if(mBook.getDescription().equals("")) {
            //mDescriptionTv.setText("Description Not Found");
            mDescriptionTv.setVisibility(View.GONE);
            Toast.makeText(this, R.string.description_not_found, Toast.LENGTH_SHORT).show();
        }
        else
            mDescriptionTv.setText(mBook.getDescription());

        // Ad
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if(item.getItemId() == R.id.action_share)
        {
            String string = "Book: " + mBook.getTitle() + "\nAuthor: " + mBook.getAuthor() + "\n" + mBook.getAmazonUrl();
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, string);
            startActivity(Intent.createChooser(intent, null));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}