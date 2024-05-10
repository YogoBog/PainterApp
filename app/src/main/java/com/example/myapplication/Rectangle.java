package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

// Rectangle class, represents a rectangle shape
public class Rectangle extends Shape {

    // Coordinates of the final point of the rectangle
    private float finalX;
    private float finalY;

    // Constructor to initialize the rectangle with starting coordinates and paint
    public Rectangle(float startX, float startY, Paint paint) {
        super(startX, startY, paint);
        // Initializing finalX and finalY with starting coordinates
        finalX = startX;
        finalY = startY;
    }

    // Method to draw the rectangle on a canvas
    @Override
    public void drawMe(Canvas canvas) {
        float startX = getStartX();
        float startY = getStartY();
        // Drawing the rectangle using the starting and final coordinates
        canvas.drawRect(new RectF(startX, startY, finalX, finalY), getPaint());
    }

    // Method to update the final coordinates of the rectangle
    @Override
    public void update(Float x, Float y) {
        // Updating the final coordinates
        finalX = x;
        finalY = y;
    }
}
