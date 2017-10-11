package com.ebr163.bifacialview.custom;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.ebr163.bifacialview.CustomPagerAdapter;
import com.ebr163.bifacialview.R;

public class CustomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ViewPager view = (ViewPager) findViewById(R.id.viewpager);
        view.setAdapter(new CustomAdapter(this));
    }
}
