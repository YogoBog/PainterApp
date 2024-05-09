package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class Polygon extends Shape {
    private int edges;
    private float finalX;
    private float finalY;
    private float rotation;

    public Polygon(float startX, float startY, Paint paint, int edges) {
        super(startX, startY, paint);
        this.edges = edges;
        finalX = startX;
        finalY = startY;
        rotation = 0;
    }

    @Override
    public void drawMe(Canvas canvas) {
        float startX = getStartX();
        float startY = getStartY();
        float r = Math.abs((finalY - startY) / 2);
        double angle = 2 * Math.PI / edges;
        Path path = new Path();

        for (int i = 0; i < edges; i++) {
            float x = (float) (startX + r * Math.cos(angle * i + rotation));
            float y = (float) (startY + r * Math.sin(angle * i + rotation));

            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }

        path.close();
        canvas.drawPath(path, getPaint());
    }

    @Override
    public void update(Float x, Float y) {
        finalX = x;
        finalY = y;

        float deltaX = x - getStartX();
        float deltaY = y - getStartY();
        rotation = (float) Math.atan2(deltaY, deltaX);
    }
}

