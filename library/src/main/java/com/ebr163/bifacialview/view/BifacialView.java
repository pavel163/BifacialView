package com.ebr163.bifacialview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import static com.ebr163.bifacialview.view.utils.BitmapUtils.dpToPx;
import static com.ebr163.bifacialview.view.utils.BitmapUtils.resizeDrawable;

/**
 * Created by ergashev on 11.04.17.
 * This view displays a clipped drawable on the right over another drawable
 * Sliding the delimiter to the right reveals more of the left view
 */
public class BifacialView extends View {

    public enum TouchMode {
        ALL, DELIMITER
    }

    private Paint paint;

    private TouchMode touchMode = TouchMode.ALL;

    private int delimiterPosition = -1;
    private int percentDelimiterPosition = 50;

    private int width;
    private int height;
    private int materialMargin;
    private int rightTextWidth;
    private int leftTextWidth;
    private boolean isMove = false;

    private int delimiterColor;
    private int delimiterWidth;
    private int delimiterPadding;
    private int arrowColor;
    private boolean arrowVisible;
    private int arrowWidth;
    private int arrowHeight;
    private int arrowStrokeWidth;
    private boolean arrowFill;
    private int arrowMargin;
    private float textSize;
    private int textColor;
    private String leftText;
    private String rightText;
    private Rect textBounds = new Rect();

    private Drawable drawableLeft;
    private Drawable drawableRight;
    private Path arrowLeft;
    private Path arrowRight;
    private CornerPathEffect cornerPathEffect;

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
        setSaveEnabled(true);
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
            int arrowCornerRadius;
            try {
                drawableLeft = a.getDrawable(R.styleable.BifacialView_drawableLeft);
                drawableRight = a.getDrawable(R.styleable.BifacialView_drawableRight);
                delimiterColor = a.getColor(R.styleable.BifacialView_delimiterColor, Color.WHITE);
                delimiterWidth = a.getDimensionPixelSize(R.styleable.BifacialView_delimiterWidth, 3);
                delimiterPadding = a.getDimensionPixelSize(R.styleable.BifacialView_delimiterPadding,
                        20);
                arrowColor = a.getColor(R.styleable.BifacialView_arrowColor, Color.WHITE);
                arrowVisible = a.getBoolean(R.styleable.BifacialView_arrowVisibility, false);
                leftText = a.getString(R.styleable.BifacialView_leftText);
                rightText = a.getString(R.styleable.BifacialView_rightText);
                textColor = a.getColor(R.styleable.BifacialView_textColor, Color.WHITE);
                textSize = a.getDimensionPixelSize(R.styleable.BifacialView_textSize,
                        getContext().getResources().getDimensionPixelSize(R.dimen.text_size));
                arrowWidth = a.getDimensionPixelSize(R.styleable.BifacialView_arrowWidth, dpToPx(getContext(), 12));
                arrowHeight = a.getDimensionPixelSize(R.styleable.BifacialView_arrowHeight, dpToPx(getContext(), 10));
                arrowMargin = a.getDimensionPixelSize(R.styleable.BifacialView_arrowMargin, dpToPx(getContext(), 5));
                arrowStrokeWidth = a.getDimensionPixelSize(R.styleable.BifacialView_arrowStrokeWidth, 5);
                arrowFill = a.getBoolean(R.styleable.BifacialView_arrowFill, true);
                arrowCornerRadius = a.getDimensionPixelSize(R.styleable.BifacialView_arrowCornerRadius, 0);

                if (a.getInteger(R.styleable.BifacialView_touchMode, 0) == 0) {
                    touchMode = TouchMode.ALL;
                } else {
                    touchMode = TouchMode.DELIMITER;
                }
            } finally {
                a.recycle();
            }

            cornerPathEffect = new CornerPathEffect(arrowCornerRadius);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = View.MeasureSpec.getSize(widthMeasureSpec);
        height = View.MeasureSpec.getSize(heightMeasureSpec);

        delimiterPosition = percentDelimiterPosition * width / 100;

        if (drawableLeft != null) {
            drawableLeft = resizeDrawable(drawableLeft, width, height);
        }

        if (drawableRight != null) {
            drawableRight = resizeDrawable(drawableRight, width, height);
        }

        paint.setTextSize(textSize);
        if (rightText != null) {
            paint.getTextBounds(rightText, 0, rightText.length(), textBounds);
            rightTextWidth = textBounds.width();
        }

        if (leftText != null) {
            paint.getTextBounds(leftText, 0, leftText.length(), textBounds);
            leftTextWidth = textBounds.width();
        }

        recreateArrowLeft();
        recreateArrowRight();

        setMeasuredDimension(width, height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        if (arrowVisible) {
            recreateArrowLeft();
            recreateArrowRight();
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // for ViewPager and RecyclerView
                if (touchMode == TouchMode.DELIMITER) {
                    if (x > delimiterPosition + delimiterPadding || x < delimiterPosition - delimiterPadding) {
                        return super.onTouchEvent(event);
                    } else {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }
            case MotionEvent.ACTION_MOVE:
                isMove = true;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isMove = false;
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        delimiterPosition = (int) (x / 1);
        percentDelimiterPosition = delimiterPosition * 100 / width;
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
        paint.setStrokeWidth(delimiterWidth);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawLine(delimiterPosition, 0, delimiterPosition, height, paint);

        if (arrowVisible && !isMove) {
            paint.setColor(arrowColor);
            paint.setStyle(arrowFill ? Paint.Style.FILL : Paint.Style.STROKE);
            paint.setStrokeWidth(arrowStrokeWidth);
            paint.setPathEffect(cornerPathEffect);
            paint.setAntiAlias(true);
            canvas.drawPath(arrowLeft, paint);
            paint.setPathEffect(null);
        }

        if (materialMargin * 2 + leftTextWidth < delimiterPosition && leftText != null) {
            paint.setColor(textColor);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawText(leftText, materialMargin, height - materialMargin, paint);
        }

        if (width - delimiterPosition > 0 && drawableRight != null) {
            if (delimiterPosition < 0) {
                delimiterPosition = 0;
            }
            canvas.clipRect(delimiterPosition + delimiterWidth / 2, 0, width, height);
            drawableRight.draw(canvas);
        }

        if (arrowVisible && !isMove) {
            paint.setColor(arrowColor);
            paint.setStyle(arrowFill ? Paint.Style.FILL : Paint.Style.STROKE);
            paint.setStrokeWidth(arrowStrokeWidth);
            paint.setPathEffect(cornerPathEffect);
            paint.setAntiAlias(true);
            canvas.drawPath(arrowRight, paint);
            paint.setPathEffect(null);
        }

        if (materialMargin * 2 + rightTextWidth < width - delimiterPosition && rightText != null) {
            paint.setColor(textColor);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawText(rightText, width - materialMargin - rightTextWidth, height - materialMargin, paint);
        }
    }

    private void recreateArrowLeft() {
        arrowLeft.rewind();
        arrowLeft.moveTo(delimiterPosition - delimiterWidth / 2 - arrowMargin - arrowWidth, height / 2);
        arrowLeft.lineTo(delimiterPosition - delimiterWidth / 2 - arrowMargin, height / 2 - arrowHeight / 2);
        arrowLeft.lineTo(delimiterPosition - delimiterWidth / 2 - arrowMargin, height / 2 + arrowHeight / 2);
        arrowLeft.close();
    }

    private void recreateArrowRight() {
        arrowRight.rewind();
        arrowRight.moveTo(delimiterPosition + delimiterWidth / 2 + arrowMargin + arrowWidth, height / 2);
        arrowRight.lineTo(delimiterPosition + delimiterWidth / 2 + arrowMargin, height / 2 - arrowHeight / 2);
        arrowRight.lineTo(delimiterPosition + delimiterWidth / 2 + arrowMargin, height / 2 + arrowHeight / 2);
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

    public void setDelimiterPosition(int percent) {
        this.percentDelimiterPosition = percent;
        invalidate();
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("superState", super.onSaveInstanceState());
        bundle.putInt("value", percentDelimiterPosition);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            percentDelimiterPosition = bundle.getInt("value");
            state = bundle.getParcelable("superState");
        }
        super.onRestoreInstanceState(state);
    }
}
