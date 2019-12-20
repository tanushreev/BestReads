package com.tanushree.bestreads.model;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface BookDao {

    @Query("SELECT * FROM book_wish")
    LiveData<List<Book>> loadAllWishlistBooks();

    @Query("SELECT * FROM book_wish WHERE isbn = :isbn")
    LiveData<Book> findBookByIsbn(String isbn);

    @Insert
    void insertBook(Book book);

    @Delete
    void deleteBook(Book book);
}
