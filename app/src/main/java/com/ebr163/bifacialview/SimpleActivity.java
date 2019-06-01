package com.ebr163.bifacialview;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ebr163.bifacialview.view.BifacialView;

public class SimpleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        final BifacialView view = findViewById(R.id.view);
        view.setDelimiterPosition(90);
        Glide.with(this)
                .load("https://files4.adme.ru/files/news/part_149/1494765/29530665-262592-3-0-1491487588-1491487592-650-bf845cd25e-1491558012.jpg")
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        view.setDrawableRight(resource);
                        return false;
                    }
                }).preload();

        Glide.with(this)
                .load("https://files3.adme.ru/files/news/part_149/1494765/29530265-262592-33-0-1491489159-1491489163-650-8edb1df01d-1491558012.jpg")
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        view.setDrawableLeft(resource);
                        return false;
                    }
                }).preload();
    }
}
