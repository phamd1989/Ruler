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
        float dps = 20; // ~ 1 inch in real-life
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

        int dist = canvas.getHeight()/mPx;
        for (int i=0; i<dist; i++) {
            canvas.drawLine(0, i*dist, dist, i*dist, mDrawPaint);
            mDrawPaint.setColor(Color.RED);
        }
    }
}
