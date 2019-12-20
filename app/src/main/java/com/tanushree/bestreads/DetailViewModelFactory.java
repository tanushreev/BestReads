package com.tanushree.bestreads;

import com.tanushree.bestreads.model.AppDatabase;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class DetailViewModelFactory extends ViewModelProvider.NewInstanceFactory
{
    private final AppDatabase mDatabase;
    private final String mBookIsbn;

    public DetailViewModelFactory(AppDatabase database, String bookIsbn) {
        mDatabase = database;
        mBookIsbn = bookIsbn;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DetailViewModel(mDatabase, mBookIsbn);
    }
}
