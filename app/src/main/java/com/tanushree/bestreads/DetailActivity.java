package com.tanushree.bestreads;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
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
    private Toolbar mToolbar;

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
        mToolbar = findViewById(R.id.toolbarDetailActivity);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intentThatStartedThisActivity = getIntent();

        if(intentThatStartedThisActivity!=null) {
            if (intentThatStartedThisActivity.hasExtra(MainActivity.KEY_BOOK_DATA)) {
                mBook = intentThatStartedThisActivity.getParcelableExtra(MainActivity.KEY_BOOK_DATA);

                setTitle(R.string.detail_activity_title);

                populateUI();
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
    }

}
