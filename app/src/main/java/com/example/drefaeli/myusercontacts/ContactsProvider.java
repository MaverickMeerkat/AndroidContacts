package com.example.drefaeli.myusercontacts;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.text.TextUtils;

import java.util.ArrayList;

public class ContactsProvider {

    private Context context;

    public ContactsProvider(Context context){
        this.context = context;
    }

    /**
     * Loads all contacts from phone and returns it in a list
     * @return
     */
    public ArrayList<String> loadContacts() {
        ArrayList<String> arrayListContacts = new ArrayList<>();
        String[] projection = { ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection, null, null, null);
        while (phones.moveToNext()) {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            arrayListContacts.add(TextUtils.join(" ", new String[] {name, phoneNumber}));
        }
        phones.close();
        return arrayListContacts;
    }
}
