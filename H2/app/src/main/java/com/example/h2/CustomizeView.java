package com.example.h2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;

public class CustomizeView extends View {

    // Parameters for hour, minute, and second hands
    private final float mHourPointWidth = 20f;
    private final float mMinutePointWidth = 12f;
    private final float mSecondPointWidth = 6f;
    private final float mPointRange = 25F;

    // Parameters for clock face
    private final float mNumberSpace = 15f;
    private final float mCircleWidth = 6.0F;
    private final int scaleMax = 60;
    private final int scaleMin = 30;
    // Paint and Rect for drawing
    private final Paint mPaint = new Paint();
    private final Rect mRect = new Rect();
    private int mWidth = 0;
    private int mHeight = 0;
    private float radius = 300.0F;

    public CustomizeView(Context context) {
        super(context);
        init();
    }

    public CustomizeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomizeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint.setTextSize(40F);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = onMeasuredSpec(widthMeasureSpec) + (int) (mCircleWidth * 2);
        mHeight = onMeasuredSpec(heightMeasureSpec) + (int) (mCircleWidth * 2);
        radius = (mWidth - mCircleWidth * 2) / 2;
        setMeasuredDimension(mWidth, mHeight);
    }

    private int onMeasuredSpec(int measureSpec) {
        int specViewSize = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            specViewSize = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            specViewSize = Math.min((int) (radius * 2), specSize);
        }
        return specViewSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float centerX = (float) (mWidth / 2);
        float centerY = (float) (mHeight / 2);
        canvas.translate(centerX, centerY);

        // Draw clock face
        drawClock(canvas);
        // Draw clock scale
        drawClockScale(canvas);
        // Draw pointers
        drawPointer(canvas);

        postInvalidateDelayed(1000);
    }

    private void drawClock(Canvas canvas) {
        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setColor(Color.DKGRAY);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(0F, 0F, radius, mPaint);
    }

    private void drawClockScale(Canvas canvas) {
        for (int index = 1; index <= 60; index++) {
            canvas.rotate(6F, 0F, 0F);
            if (index % 5 == 0) {
                mPaint.setStrokeWidth(5.0F);
                mPaint.setColor(Color.BLACK);
                canvas.drawLine(0F, -radius, 0F, -radius + scaleMax, mPaint);
                drawClockNumber(canvas, index / 5);
            } else {
                mPaint.setStrokeWidth(3.0F);
                mPaint.setColor(Color.GRAY);
                canvas.drawLine(0F, -radius, 0F, -radius + scaleMin, mPaint);
            }
        }
    }

    private void drawClockNumber(Canvas canvas, int number) {
        mPaint.setStrokeWidth(1.0F);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
        String numberText = Integer.toString(number);
        mPaint.getTextBounds(numberText, 0, numberText.length(), mRect);
        canvas.save();
        canvas.translate(0F, -radius + mNumberSpace + scaleMax + (mRect.height() / 2));
        canvas.rotate((float) (number * -30));
        canvas.drawText(numberText, -mRect.width() / 2F, mRect.height() / 2F, mPaint);
        canvas.restore();
    }

    private void drawPointer(Canvas canvas) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        float angleHour = (hour + minute / 60F) * 360 / 12;
        float angleMinute = (minute + second / 60F) * 360 / 60;
        float angleSecond = second * 360 / 60;

        // Draw hour hand
        canvas.save();
        canvas.rotate(angleHour, 0F, 0F);
        RectF rectHour = new RectF(-mHourPointWidth / 2, -radius / 2, mHourPointWidth / 2, radius / 6);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(rectHour, mPointRange, mPointRange, mPaint);
        canvas.restore();

        // Draw minute hand
        canvas.save();
        canvas.rotate(angleMinute, 0F, 0F);
        RectF rectMinute = new RectF(-mMinutePointWidth / 2, -radius * 3.5f / 5, mMinutePointWidth / 2, radius / 6);
        mPaint.setColor(Color.BLACK);
        canvas.drawRoundRect(rectMinute, mPointRange, mPointRange, mPaint);
        canvas.restore();

        // Draw second hand
        canvas.save();
        canvas.rotate(angleSecond, 0F, 0F);
        RectF rectSecond = new RectF(-mSecondPointWidth / 2, -radius + 10, mSecondPointWidth / 2, radius / 6);
        mPaint.setColor(Color.RED);
        canvas.drawRoundRect(rectSecond, mPointRange, mPointRange, mPaint);
        canvas.restore();

        // Draw center circle
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(0F, 0F, mSecondPointWidth * 4, mPaint);
    }
}
