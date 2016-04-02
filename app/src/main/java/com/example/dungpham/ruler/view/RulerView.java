package com.example.dungpham.ruler.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dungpham on 4/1/16.
 */
public class RulerView extends View {
    // setup initial color
    private final int paintColor = Color.BLACK;
    // defines paint and canvas
    private Paint mDrawPaint;
    // distance between each marker in pixel, equivalent to 1/8 of an inch
    private int mPx;

    public RulerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setupPaint();
        setupMetricsConversion();
    }

    private void setupMetricsConversion() {
        int dps = 20; // ~ 1/8 inch in real-life
        mPx = (int) (dps * getResources().getDisplayMetrics().density);
    }

    // Setup paint with color and stroke styles
    private void setupPaint() {
        mDrawPaint = new Paint();
        mDrawPaint.setColor(paintColor);
        mDrawPaint.setAntiAlias(true);
        mDrawPaint.setStrokeWidth(5);
        mDrawPaint.setStyle(Paint.Style.STROKE);
        mDrawPaint.setStrokeJoin(Paint.Join.ROUND);
        mDrawPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawLine(0, 0, 0, canvas.getHeight(), mDrawPaint);

        int constantDist = canvas.getHeight()/mPx;
        for (int i=0; i<=constantDist; i++) {
            int dist = constantDist * getMarkerLength(i);
            canvas.drawLine(0, i*mPx, dist, i*mPx, mDrawPaint);
            float textSize = mDrawPaint.getTextSize();
            mDrawPaint.setTextSize(getTextSize(i, textSize * 3));
            canvas.drawText(getMarkerText(i), dist, i*mPx + getTextSize(i, textSize * 3)/3, mDrawPaint);
            mDrawPaint.setTextSize(textSize);
        }
    }

    private int getMarkerLength(int i) {
        if (i%8 == 0) return 8;
        else if (i%4 == 0) return 4;
        else if (i%2 == 0) return 2;
        else return 1;
    }

    private float getTextSize(int i, float textSize) {
        if (i%8 == 0) return 4*textSize;
        else if (i%4 == 0) return 2*textSize;
        else if (i%2 == 0) return 1.5f*textSize;
        else return 1*textSize;
    }

    private String getMarkerText(int i) {
        switch (i%8) {
            case 0:
                return Integer.toString(i/8);
            case 1:
                return "1/8";
            case 2:
                return "1/4";
            case 3:
                return "3/8";
            case 4:
                return "1/2";
            case 5:
                return "5/8";
            case 6:
                return "3/4";
            case 7:
                return "7/8";
            default:
                return "0";
        }
    }
}
