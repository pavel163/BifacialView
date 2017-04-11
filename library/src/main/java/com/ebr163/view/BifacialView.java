package com.ebr163.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import static com.ebr163.view.utils.BitmapUtils.drawableToBitmap;
import static com.ebr163.view.utils.BitmapUtils.getResizedBitmap;

/**
 * Created by ergashev on 11.04.17.
 */
public class BifacialView extends View {

    private Paint paint;

    private int delimiterPosition;
    private int width;
    private int height;

    private Drawable drawableLeft;
    private Drawable drawableRight;

    public BifacialView(Context context) {
        super(context);
        init();
    }

    public BifacialView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        initAttrs(attrs);
    }

    public BifacialView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initAttrs(attrs);
    }

    private void init() {
        paint = new Paint();
    }

    private void initAttrs(AttributeSet attrs){
        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.BifacialView,
                    0, 0);
            try {
                drawableLeft = a.getDrawable(R.styleable.BifacialView_drawableLeft);
                drawableRight = a.getDrawable(R.styleable.BifacialView_drawableRight);
            } finally {
                a.recycle();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = View.MeasureSpec.getSize(widthMeasureSpec);
        height = View.MeasureSpec.getSize(heightMeasureSpec);
        delimiterPosition = width / 2;

        if (drawableLeft != null) {
            drawableLeft = new BitmapDrawable(getResources(), getResizedBitmap(drawableToBitmap(drawableLeft), width, height));
        }

        if (drawableRight != null) {
            drawableRight = new BitmapDrawable(getResources(), getResizedBitmap(drawableToBitmap(drawableRight), width, height));
        }

        setMeasuredDimension(width, height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        delimiterPosition = (int) (x / 1);
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setFilterBitmap(true);

        if (delimiterPosition > 0 && drawableLeft != null) {
            if (width - delimiterPosition < 0) {
                delimiterPosition = width;
            }
            Bitmap croppedBmp = Bitmap.createBitmap(drawableToBitmap(drawableLeft), 0, 0, delimiterPosition, height);
            canvas.drawBitmap(croppedBmp, 0, 0, paint);
            croppedBmp.recycle();
        }

        if (width - delimiterPosition > 0 && drawableRight != null) {
            if (delimiterPosition < 0) {
                delimiterPosition = 0;
            }
            Bitmap croppedBmp2 = Bitmap.createBitmap(drawableToBitmap(drawableRight), delimiterPosition, 0, width - delimiterPosition, height);
            canvas.drawBitmap(croppedBmp2, delimiterPosition, 0, paint);
            croppedBmp2.recycle();
        }

//        Rect mRect = new Rect(0, 0, delimiterPosition, height);
//        drawableLeft.setBounds(0, 0, width, height);
//        canvas.clipRect(mRect);
//        drawableLeft.draw(canvas);
//
//        Rect mRect1 = new Rect(delimiterPosition, 0, width, height);
//        drawableRight.setBounds(0, 0, width, height);
//        canvas.clipRect(mRect1);
//        drawableRight.draw(canvas);


    }

    public void setDrawableLeft(Drawable drawableLeft) {
        this.drawableLeft = new BitmapDrawable(getResources(), getResizedBitmap(drawableToBitmap(drawableLeft), width, height));
        invalidate();
    }

    public void setDrawableRight(Drawable drawableRight) {
        this.drawableRight = new BitmapDrawable(getResources(), getResizedBitmap(drawableToBitmap(drawableRight), width, height));
        invalidate();
    }

    public void setBitmapLeft(Bitmap bitmapLeft) {
        this.drawableLeft = new BitmapDrawable(getResources(), getResizedBitmap(bitmapLeft, width, height));
        invalidate();
    }

    public void setBitmapRight(Bitmap bitmapRight) {
        this.drawableRight = new BitmapDrawable(getResources(), getResizedBitmap(bitmapRight, width, height));
        invalidate();
    }
}
