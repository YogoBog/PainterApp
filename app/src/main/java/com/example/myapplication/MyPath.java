package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

// MyPath class extends Shape
public class MyPath extends Shape {
    private Path path; // Path object to define the shape's path

    // Constructor taking starting coordinates and paint
    public MyPath(float startX, float startY, Paint paint) {
        super(startX, startY, paint); // Call to superclass constructor
        path = new Path(); // Initialize the Path object
        path.moveTo(startX, startY); // Move the starting point of the path
    }

    // Method to draw the shape
    @Override
    public void drawMe(Canvas canvas) {
        canvas.drawPath(path, getPaint()); // Draw the path on the canvas using the specified paint
    }

    // Method to update the shape's path with new coordinates
    @Override
    public void update(Float x, Float y) {
        path.lineTo(x, y); // Add a line segment to the path from the current point to the specified point
    }
}
