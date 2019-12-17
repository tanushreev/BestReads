package com.tanushree.bestreads.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Results
{
    @SerializedName("books")
    @Expose
    private ArrayList<Book> books;

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Results{" +
                "books=" + books +
                '}';
    }
}
