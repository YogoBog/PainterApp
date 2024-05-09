package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class MyPath extends Shape {
    private Path path;

    public MyPath(float startX, float startY, Paint paint) {
        super(startX, startY, paint);
        path = new Path();
        path.moveTo(startX, startY);
    }

    @Override
    public void drawMe(Canvas canvas) {
        canvas.drawPath(path, getPaint());
    }

    @Override
    public void update(Float x, Float y) {
        path.lineTo(x, y);
    }
}
