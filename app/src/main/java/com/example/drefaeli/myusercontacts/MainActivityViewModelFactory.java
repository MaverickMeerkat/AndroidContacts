package com.example.drefaeli.myusercontacts;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

public class MainActivityViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private ContactsProvider provider;

    public MainActivityViewModelFactory(ContactsProvider provider) {
        this.provider = provider;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MainActivityViewModel(provider);
    }
}