package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Paint;

// Abstract class representing a shape
public abstract class Shape {
    // Coordinates of the starting point of the shape
    private float startX;
    private float startY;
    
    // Paint object to define the appearance of the shape
    private Paint paint;
    
    // Constructor to initialize the shape with starting coordinates and paint
    public Shape(float startX, float startY, Paint paint) {
        this.startX = startX;
        this.startY = startY;
        this.paint = paint;
    }
    
    // Getter method for the starting X coordinate
    public float getStartX() {
        return startX;
    }
    
    // Getter method for the starting Y coordinate
    public float getStartY() {
        return startY;
    }
    
    // Getter method for the paint object
    public Paint getPaint() {
        return paint;
    }
    
    // Abstract method to draw the shape on a canvas
    public abstract void drawMe(Canvas canvas);
    
    // Abstract method to update the shape's position
    public abstract void update(Float x, Float y);
}
