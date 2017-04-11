package com.ebr163.bifacialview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ebr163.bifacialview.view.BifacialView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final BifacialView view = (BifacialView) findViewById(R.id.view);
        Glide.with(this)
                .load("http://sevizm.mos.ru/presscenter/%D0%90%D1%82%D1%82%D1%80%D0%B0%D0%BA%D1%86%D0%B8%D0%BE%D0%BD%D1%8B%20%D0%B2%20%D0%BF%D0%B0%D1%80%D0%BA%D0%B0%D1%85%20%D1%81%20%D1%81%D0%B0%D0%B9%D1%82%D0%B0%20pasmi.ru.jpg")
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

        Glide.with(this)
                .load("http://s1.fotokto.ru/photo/full/181/1818010.jpg")
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
    }
}
