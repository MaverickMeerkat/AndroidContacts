package com.example.drefaeli.myusercontacts;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<String> contactsList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(View v) {
            super(v);
            textView = itemView.findViewById(R.id.myTextView);
        }
    }

    public RecyclerViewAdapter(ArrayList<String> contacts) {
        contactsList = contacts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(contactsList.get(position));
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }
}
