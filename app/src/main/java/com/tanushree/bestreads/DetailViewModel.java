package com.tanushree.bestreads;

import com.tanushree.bestreads.model.AppDatabase;
import com.tanushree.bestreads.model.Book;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class DetailViewModel extends ViewModel {

    private LiveData<Book> book;

    public DetailViewModel(AppDatabase database, String isbn) {
        book = database.bookDao().findBookByIsbn(isbn);
    }

    public LiveData<Book> getBook() {
        return book;
    }
}
