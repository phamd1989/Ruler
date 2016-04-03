package com.example.dungpham.ruler.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dungpham on 4/1/16.
 */
public class RulerView extends View {

    private final int INCH_EIGHTH = 20;
    private final int TEXT_MAGNIFY = 3;
    private final int CIRCLE_RADIUS = 200;
    // setup initial color
    private final int paintColor = Color.BLACK;
    // defines paint and canvas
    private Paint mDrawPaint;
    // distance between each marker in pixel, equivalent to 1/8 of an inch
    private int mMarkerDistanceInPx;
    // the center of the circle on screen
    private Point mPoint;

    public RulerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setupPaint();
        setupMetricsConversion();
    }

    private void setupMetricsConversion() {
        int dps = INCH_EIGHTH; // ~ 1/8 inch in real-life
        mMarkerDistanceInPx = (int) (dps * getResources().getDisplayMetrics().density);
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
        // get the number of markers that fits the canvas height
        int noOfMarkers = canvas.getHeight()/ mMarkerDistanceInPx;
        float textSize = mDrawPaint.getTextSize();
        if (mPoint == null) {
            mPoint = new Point(canvas.getWidth(), 8*mMarkerDistanceInPx);
        }
        // draw the circle to indicate the actual measurement at this point
        canvas.drawCircle(mPoint.x, mPoint.y, CIRCLE_RADIUS, mDrawPaint);
        canvas.drawLine(mPoint.x - CIRCLE_RADIUS, mPoint.y, 0, mPoint.y, mDrawPaint);
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        String inchMeasurement = df.format(mPoint.y/(8.0f*mMarkerDistanceInPx));
        mDrawPaint.setTextSize(1.5f * textSize * TEXT_MAGNIFY);
        canvas.drawText(inchMeasurement, mPoint.x - CIRCLE_RADIUS/2, mPoint.y, mDrawPaint);
        mDrawPaint.setTextSize(textSize);

        // draw the markers
        for (int i=0; i<=noOfMarkers; i++) {
            int markerLength = noOfMarkers * getMarkerLength(i);
            canvas.drawLine(0, i* mMarkerDistanceInPx, markerLength, i* mMarkerDistanceInPx, mDrawPaint);
            // get the current text size on canvas
            mDrawPaint.setTextSize(getTextSize(i, textSize * TEXT_MAGNIFY));
            canvas.drawText(getMarkerText(i), markerLength, i* mMarkerDistanceInPx + getTextSize(i, textSize * TEXT_MAGNIFY)/TEXT_MAGNIFY, mDrawPaint);
            // reset the text size to the original value
            mDrawPaint.setTextSize(textSize);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchX = (int) event.getX();
        int touchY = (int) event.getY();
        // if outside canvas, don't redraw
        if (touchY < 0 || touchY > getHeight()) return false;
        if (Math.abs(mPoint.x - touchX) <= CIRCLE_RADIUS && Math.abs(mPoint.y - touchY) <= CIRCLE_RADIUS) {
            mPoint.set(mPoint.x, touchY);
        }
        // indicate view should be redrawn
        postInvalidate();
        return true;
    }

    /**
     * return different length for each marker
     * @param i
     * @return
     */
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
                if (i==0) return "";
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
