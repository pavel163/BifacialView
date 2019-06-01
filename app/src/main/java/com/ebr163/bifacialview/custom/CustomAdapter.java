package com.ebr163.bifacialview.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.ebr163.bifacialview.R;
import com.ebr163.bifacialview.recyclerview.CustomRVAdapter;

/**
 * Created by Bakht on 11.10.2017.
 */

class CustomAdapter extends PagerAdapter {

    private Context mContext;

    CustomAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_custom, collection, false);

        RecyclerView recyclerView = layout.findViewById(R.id.recyclerview);
        recyclerView.setAdapter(new CustomRVAdapter());

        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup collection, int position, @NonNull Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Title";
    }
}