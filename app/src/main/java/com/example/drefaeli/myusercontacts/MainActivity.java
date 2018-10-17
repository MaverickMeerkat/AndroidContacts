package com.example.drefaeli.myusercontacts;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 31000;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private MainActivityViewModel viewModel;
    private ContactsProvider contactsProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get RecyclerView
        recyclerView = findViewById(R.id.contacts_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // set contactsProvider and view-model
        contactsProvider = new ContactsProvider(getApplicationContext());
        viewModel = ViewModelProviders.of(this, new MainActivityViewModelFactory(contactsProvider)).get(MainActivityViewModel.class);

        // observe the contacts object in the view model and update UI if it changes
        // this will also persist data in case of configuration changes (e.g. screen rotation)
        viewModel.getContactsObject().observe(this, contacts -> {
            adapter = new RecyclerViewAdapter(contacts);
            recyclerView.setAdapter(adapter);
        });
    }

    /**
     * Loads contacts when user clicks on "Show Contacts" button
     * @param view
     */
    public void showContacts(View view) {
        if (isPermissionGranted()) {
            viewModel.loadContacts();
        } else {
            requestPermission();
        }
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_CONTACTS)) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.myCoordinatorLayout), "We need your contacts",
                    Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("Ok", view -> doRequestPermission());
            snackbar.show();
        } else {
            doRequestPermission();
        }
    }

    private void doRequestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_CONTACTS},
                MY_PERMISSIONS_REQUEST_READ_CONTACTS);
    }

    private boolean isPermissionGranted() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    viewModel.loadContacts();
                } else {
                    Toast.makeText(this, "No permission. Abort", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
