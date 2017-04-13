package com.ebr163.bifacialview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import static com.ebr163.bifacialview.view.utils.BitmapUtils.dpToPx;
import static com.ebr163.bifacialview.view.utils.BitmapUtils.resizeDrawable;

/**
 * Created by ergashev on 11.04.17.
 */
public class BifacialView extends View {

    private Paint paint;

    private int delimiterPosition;
    private int width;
    private int height;
    private int materialMargin;
    private int rightTextWith;
    private int leftTextWith;
    private boolean isMove = false;

    private int delimiterColor;
    private int arrowColor;
    private boolean arrowVisible;
    private float textSize;
    private int textColor;
    private String leftText;
    private String rightText;
    private Rect textBounds = new Rect();

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
        materialMargin = dpToPx(getContext(), 16);
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
                arrowColor = a.getColor(R.styleable.BifacialView_arrowColor, Color.WHITE);
                arrowVisible = a.getBoolean(R.styleable.BifacialView_arrowVisibility, false);
                leftText = a.getString(R.styleable.BifacialView_leftText);
                rightText = a.getString(R.styleable.BifacialView_rightText);
                textColor = a.getColor(R.styleable.BifacialView_textColor, Color.WHITE);
                textSize = a.getDimensionPixelSize(R.styleable.BifacialView_textSize,
                        getContext().getResources().getDimensionPixelSize(R.dimen.text_size));
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

        paint.setTextSize(textSize);
        if (rightText != null) {
            paint.getTextBounds(rightText, 0, rightText.length(), textBounds);
            rightTextWith = textBounds.width();
        }

        if (leftText != null) {
            paint.getTextBounds(leftText, 0, leftText.length(), textBounds);
            leftTextWith = textBounds.width();
        }

        recreateArrowLeft();
        recreateArrowRight();

        setMeasuredDimension(width, height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        delimiterPosition = (int) (x / 1);
        if (arrowVisible) {
            recreateArrowLeft();
            recreateArrowRight();
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isMove = true;
                break;
            case MotionEvent.ACTION_MOVE:
                isMove = true;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isMove = false;
                break;
        }
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
            drawableLeft.draw(canvas);
        }

        paint.setColor(delimiterColor);
        paint.setStrokeWidth(3);
        canvas.drawLine(delimiterPosition, 0, delimiterPosition, height, paint);

        if (arrowVisible && !isMove) {
            paint.setColor(arrowColor);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawPath(arrowLeft, paint);
        }

        if (materialMargin * 2 + leftTextWith < delimiterPosition && leftText != null) {
            paint.setColor(textColor);
            canvas.drawText(leftText, materialMargin, height - materialMargin, paint);
        }

        if (width - delimiterPosition > 0 && drawableRight != null) {
            if (delimiterPosition < 0) {
                delimiterPosition = 0;
            }
            canvas.clipRect(delimiterPosition, 0, width, height);
            drawableRight.draw(canvas);
        }

        if (arrowVisible && !isMove) {
            paint.setColor(arrowColor);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawPath(arrowRight, paint);
        }

        if (materialMargin * 2 + rightTextWith < width - delimiterPosition && rightText != null) {
            paint.setColor(textColor);
            canvas.drawText(rightText, width - materialMargin - rightTextWith, height - materialMargin, paint);
        }
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

    public void setRightText(String text) {
        this.rightText = text;
        invalidate();
    }

    public void setLeftText(String text) {
        this.leftText = text;
        invalidate();
    }
}
