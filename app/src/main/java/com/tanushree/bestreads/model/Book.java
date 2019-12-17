package com.tanushree.bestreads.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "book_wish")
public class Book implements Parcelable
{

    @PrimaryKey
    @NonNull
    @SerializedName("primary_isbn13")
    @Expose
    private String isbn = "0";

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("author")
    @Expose
    private String author;

    @ColumnInfo(name = "book_image")
    @SerializedName("book_image")
    @Expose
    private String bookImage;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("rank")
    @Expose
    private int rank;

    @ColumnInfo(name = "weeks_on_list")
    @SerializedName("weeks_on_list")
    @Expose
    private int weeksOnList;

    @ColumnInfo(name = "amazon_url)")
    @SerializedName("amazon_product_url")
    @Expose
    private String amazonUrl;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getWeeksOnList() {
        return weeksOnList;
    }

    public void setWeeksOnList(int weeksOnList) {
        this.weeksOnList = weeksOnList;
    }

    public String getAmazonUrl() {
        return amazonUrl;
    }

    public void setAmazonUrl(String amazonUrl) {
        this.amazonUrl = amazonUrl;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", bookImage='" + bookImage + '\'' +
                ", description='" + description + '\'' +
                ", rank=" + rank +
                ", weeksOnList=" + weeksOnList +
                ", amazonUrl='" + amazonUrl + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {

        parcel.writeString(isbn);
        parcel.writeString(title);
        parcel.writeString(author);
        parcel.writeString(bookImage);
        parcel.writeString(description);
        parcel.writeInt(rank);
        parcel.writeInt(weeksOnList);
        parcel.writeString(amazonUrl);
    }

    // Default Constructor (for Parcelable).

    @Ignore
    public Book()
    {
    }

    private Book(Parcel parcel)
    {
        isbn = parcel.readString();
        title = parcel.readString();
        author = parcel.readString();
        bookImage = parcel.readString();
        description = parcel.readString();
        rank = parcel.readInt();
        weeksOnList = parcel.readInt();
        amazonUrl = parcel.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel parcel) {
            return new Book(parcel);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    // Constructor (for Room).

    public Book(String isbn, String title, String author, String bookImage, String description, int rank, int weeksOnList, String amazonUrl) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.bookImage = bookImage;
        this.description = description;
        this.rank = rank;
        this.weeksOnList = weeksOnList;
        this.amazonUrl = amazonUrl;
    }
}