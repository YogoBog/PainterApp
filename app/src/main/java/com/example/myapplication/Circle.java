package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Circle extends Shape {

    private float radius;

    public Circle(float startX, float startY, Paint paint) {
        super(startX, startY, paint);
        radius = 0;
    }

    @Override
    public void drawMe(Canvas canvas) {
        float startX = getStartX();
        float startY = getStartY();
        canvas.drawCircle(startX, startY, radius, getPaint());
    }

    @Override
    public void update(Float x, Float y) {
        float startX = getStartX();
        float startY = getStartY();
        radius = (float) Math.sqrt(Math.pow((x - startX), 2) + Math.pow((y - startY), 2));
    }
}
