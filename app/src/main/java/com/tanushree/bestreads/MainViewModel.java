package com.tanushree.bestreads;

import android.app.Application;

import com.tanushree.bestreads.model.AppDatabase;
import com.tanushree.bestreads.model.Book;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MainViewModel extends AndroidViewModel {

    //private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<Book>> bookList;

    public MainViewModel(@NonNull Application application) {
        super(application);
        //Log.d(TAG, "Actively retrieving wishlist books from the Database");
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        bookList = database.bookDao().loadAllWishlistBooks();
    }

    public LiveData<List<Book>> getBookList() {
        return bookList;
    }
}
