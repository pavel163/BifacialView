package com.ebr163.bifacialview.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ebr163.bifacialview.R;

/**
 * Created by Bakht on 11.10.2017.
 */

public class CustomRVAdapter extends RecyclerView.Adapter<CustomRVAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View v) {
            super(v);
        }
    }

    public CustomRVAdapter() {
    }

    @NonNull
    @Override
    public CustomRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}