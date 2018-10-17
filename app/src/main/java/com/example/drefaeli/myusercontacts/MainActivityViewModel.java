package com.example.drefaeli.myusercontacts;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;


import java.util.ArrayList;

public class MainActivityViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<String>> contacts = new MutableLiveData<>();
    private ContactsProvider contactsProvider;

    public MainActivityViewModel(ContactsProvider provider) {
        contactsProvider = provider;
    }

    public LiveData<ArrayList<String>> getContactsObject() {
        return contacts;
    }

    public void loadContacts() {
        contacts.postValue(contactsProvider.loadContacts());
    }

}
