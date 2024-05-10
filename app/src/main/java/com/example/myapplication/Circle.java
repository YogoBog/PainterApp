package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Paint;

// Circle class extends Shape, representing a circle shape
public class Circle extends Shape {

    private float radius; // Radius of the circle

    // Constructor for Circle class
    public Circle(float startX, float startY, Paint paint) {
        super(startX, startY, paint); // Call superclass constructor
        radius = 0; // Initialize radius to 0
    }

    // Method to draw the circle on the canvas
    @Override
    public void drawMe(Canvas canvas) {
        // Retrieve starting coordinates
        float startX = getStartX();
        float startY = getStartY();
        // Draw the circle on the canvas using starting coordinates and radius, and specified paint
        canvas.drawCircle(startX, startY, radius, getPaint());
    }

    // Method to update the circle's radius based on new touch coordinates
    @Override
    public void update(Float x, Float y) {
        // Retrieve starting coordinates
        float startX = getStartX();
        float startY = getStartY();
        // Calculate the distance between the current touch coordinates and the starting coordinates
        radius = (float) Math.sqrt(Math.pow((x - startX), 2) + Math.pow((y - startY), 2));
    }
}
