package com.tanushree.bestreads;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tanushree.bestreads.model.Book;
import com.squareup.picasso.Picasso;
import com.tanushree.bestreads.model.Book;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BooksViewHolder>
{
    private List<Book> mBookList;
    private final BooksAdapterOnClickHandler mClickHandler;

    public BooksAdapter(BooksAdapterOnClickHandler clickHandler)
    {
        mClickHandler = clickHandler;
    }

    // Inner Interface

    public interface BooksAdapterOnClickHandler
    {
        void onClick(Book book);
    }

    public void setBooksData(List<Book> bookList)
    {
        mBookList = bookList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BooksAdapter.BooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.books_grid_item, parent, false);

        ViewGroup.MarginLayoutParams layoutParams =
                (ViewGroup.MarginLayoutParams) view.getLayoutParams();

        boolean landscape = parent.getContext().getResources().getBoolean(R.bool.isLandscape);

        if (landscape)
        {
            layoutParams.width = parent.getWidth() / 3;
            layoutParams.height = parent.getHeight();
        }
        else {
            layoutParams.width = parent.getWidth() / 2;
            layoutParams.height = parent.getHeight() / 2;
        }

        return new BooksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksAdapter.BooksViewHolder holder, int position) {
        Book book = mBookList.get(position);
        holder.bindBook(book);
    }

    @Override
    public int getItemCount() {
        if(mBookList!=null)
            return mBookList.size();
        return 0;
    }

    // Inner class

    public class BooksViewHolder extends RecyclerView.ViewHolder implements OnClickListener
    {
        ImageView mBookCoverImageView;

        public BooksViewHolder(@NonNull View itemView) {
            super(itemView);

            mBookCoverImageView = itemView.findViewById(R.id.ivBook);

            itemView.setOnClickListener(this);
        }

        public void bindBook(Book book)
        {
            Picasso.get()
                    .load(book.getBookImage())
                    .placeholder(R.drawable.book_cover_error)
                    .error(R.drawable.book_cover_error)
                    .into(mBookCoverImageView);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Book book = mBookList.get(adapterPosition);
            mClickHandler.onClick(book);
        }
    }
}
