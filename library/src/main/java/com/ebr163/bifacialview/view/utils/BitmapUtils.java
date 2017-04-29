package com.ebr163.bifacialview.view.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

/**
 * Created by ergashev on 11.04.17.
 */

public class BitmapUtils {

    public static Drawable resizeDrawable(Drawable drawable, int newWidth, int newHeight) {
        Bitmap img = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(img);
        drawable.setBounds(0, 0, img.getWidth(), img.getHeight());
        drawable.draw(canvas);
        return drawable;
    }

    public static int dpToPx(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
