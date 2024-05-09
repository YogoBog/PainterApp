package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class Rectangle extends Shape {

    private float finalX;
    private float finalY;

    public Rectangle(float startX, float startY, Paint paint) {
        super(startX, startY, paint);
        finalX = startX;
        finalY = startY;
    }

    @Override
    public void drawMe(Canvas canvas) {
        float startX = getStartX();
        float startY = getStartY();
        canvas.drawRect(new RectF(startX, startY, finalX, finalY), getPaint());
    }

    @Override
    public void update(Float x, Float y) {
        finalX = x;
        finalY = y;
    }
}
