package com.ebr163.bifacialview.custom;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebr163.bifacialview.R;
import com.ebr163.bifacialview.recyclerview.CustomRVAdapter;

/**
 * Created by Bakht on 11.10.2017.
 */

public class CustomAdapter extends PagerAdapter {

    private Context mContext;

    public CustomAdapter(Context context) {
        mContext = context;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_custom, collection, false);

        RecyclerView recyclerView = ((RecyclerView) layout.findViewById(R.id.recyclerview));
        recyclerView.setAdapter(new CustomRVAdapter());

        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Title";
    }
}