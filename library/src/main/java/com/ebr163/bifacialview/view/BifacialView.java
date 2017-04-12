package com.ebr163.bifacialview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import static com.ebr163.bifacialview.view.utils.BitmapUtils.dpToPx;
import static com.ebr163.bifacialview.view.utils.BitmapUtils.drawableToBitmap;
import static com.ebr163.bifacialview.view.utils.BitmapUtils.resizeDrawable;

/**
 * Created by ergashev on 11.04.17.
 */
public class BifacialView extends View {

    private Paint paint;

    private int delimiterPosition;
    private int width;
    private int height;
    private int delimiterColor;

    private Drawable drawableLeft;
    private Drawable drawableRight;
    private Path arrowLeft;
    private Path arrowRight;

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
        arrowLeft = new Path();
        arrowRight = new Path();
    }

    private void initAttrs(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.BifacialView,
                    0, 0);
            try {
                drawableLeft = a.getDrawable(R.styleable.BifacialView_drawableLeft);
                drawableRight = a.getDrawable(R.styleable.BifacialView_drawableRight);
                delimiterColor = a.getColor(R.styleable.BifacialView_delimiterColor, Color.WHITE);
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
            drawableLeft = resizeDrawable(drawableLeft, width, height);
        }

        if (drawableRight != null) {
            drawableRight = resizeDrawable(drawableRight, width, height);
        }

        recreateArrowLeft();
        recreateArrowRight();

        setMeasuredDimension(width, height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        delimiterPosition = (int) (x / 1);
        recreateArrowLeft();
        recreateArrowRight();
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (delimiterPosition > 0 && drawableLeft != null) {
            if (width - delimiterPosition < 0) {
                delimiterPosition = width;
            }
            Bitmap croppedBmp = Bitmap.createBitmap(drawableToBitmap(drawableLeft, width, height), 0, 0, delimiterPosition, height);
            canvas.drawBitmap(croppedBmp, 0, 0, paint);
            croppedBmp.recycle();
        }

        if (width - delimiterPosition > 0 && drawableRight != null) {
            if (delimiterPosition < 0) {
                delimiterPosition = 0;
            }
            Bitmap croppedBmp = Bitmap.createBitmap(drawableToBitmap(drawableRight, width, height), delimiterPosition, 0, width - delimiterPosition, height);
            canvas.drawBitmap(croppedBmp, delimiterPosition, 0, paint);
            croppedBmp.recycle();
        }

        paint.setColor(delimiterColor);
        paint.setStrokeWidth(3);
        canvas.drawLine(delimiterPosition, 0, delimiterPosition, height, paint);

        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(arrowLeft, paint);
        canvas.drawPath(arrowRight, paint);
    }

    private void recreateArrowLeft() {
        arrowLeft.rewind();
        arrowLeft.moveTo(delimiterPosition - dpToPx(getContext(), 12), height / 2);
        arrowLeft.lineTo(delimiterPosition - dpToPx(getContext(), 5), height / 2 - dpToPx(getContext(), 5));
        arrowLeft.lineTo(delimiterPosition - dpToPx(getContext(), 5), height / 2 + dpToPx(getContext(), 5));
        arrowLeft.close();
    }

    private void recreateArrowRight() {
        arrowRight.rewind();
        arrowRight.moveTo(delimiterPosition + dpToPx(getContext(), 12), height / 2);
        arrowRight.lineTo(delimiterPosition + dpToPx(getContext(), 5), height / 2 - dpToPx(getContext(), 5));
        arrowRight.lineTo(delimiterPosition + dpToPx(getContext(), 5), height / 2 + dpToPx(getContext(), 5));
        arrowRight.close();
    }

    public void setDrawableLeft(Drawable drawableLeft) {
        if (width > 0 && height > 0) {
            this.drawableLeft = resizeDrawable(drawableLeft, width, height);
        } else {
            this.drawableLeft = drawableLeft;
        }
        invalidate();
    }

    public void setDrawableRight(Drawable drawableRight) {
        if (width > 0 && height > 0) {
            this.drawableRight = resizeDrawable(drawableRight, width, height);
        } else {
            this.drawableRight = drawableRight;
        }
        invalidate();
    }
}
