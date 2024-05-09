package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Shape {
    private float startX;
    private float startY;
    private Paint paint;
    public Shape(float startX, float startY, Paint paint) {
        this.startX = startX;
        this.startY = startY;
        this.paint = paint;
    }
    public float getStartX() {
        return startX;
    }
    public float getStartY() {
        return startY;
    }
    public Paint getPaint() {
        return paint;
    }
    public abstract void drawMe(Canvas canvas);
    public abstract void update(Float x, Float y);
}
