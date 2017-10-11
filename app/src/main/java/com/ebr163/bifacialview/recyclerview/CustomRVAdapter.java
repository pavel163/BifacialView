package com.ebr163.bifacialview.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebr163.bifacialview.R;

/**
 * Created by Bakht on 11.10.2017.
 */

public class CustomRVAdapter extends RecyclerView.Adapter<CustomRVAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
        }
    }

    public CustomRVAdapter() {
    }

    @Override
    public CustomRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}