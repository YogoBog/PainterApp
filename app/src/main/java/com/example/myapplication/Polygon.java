package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

// Polygon class, represents a polygon shape
public class Polygon extends Shape {
    // Number of edges of the polygon
    private int edges;
    
    // Coordinates of the final point of the polygon
    private float finalX;
    private float finalY;
    
    // Rotation angle of the polygon
    private float rotation;

    // Constructor to initialize the polygon with starting coordinates, paint, and number of edges
    public Polygon(float startX, float startY, Paint paint, int edges) {
        super(startX, startY, paint);
        // Initializing the number of edges, final coordinates, and rotation
        this.edges = edges;
        finalX = startX;
        finalY = startY;
        rotation = 0;
    }

    // Method to draw the polygon on a canvas
    @Override
    public void drawMe(Canvas canvas) {
        float startX = getStartX();
        float startY = getStartY();
        
        // Calculating the radius of the polygon
        float r = Math.abs((finalY - startY) / 2);
        // Calculating the angle between edges
        double angle = 2 * Math.PI / edges;
        Path path = new Path();

        // Generating the path for the polygon
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
        // Drawing the polygon using the generated path
        canvas.drawPath(path, getPaint());
    }

    // Method to update the final coordinates and rotation angle of the polygon
    @Override
    public void update(Float x, Float y) {
        // Updating the final coordinates
        finalX = x;
        finalY = y;

        // Calculating the angle of rotation based on the difference between final and starting coordinates
        float deltaX = x - getStartX();
        float deltaY = y - getStartY();
        rotation = (float) Math.atan2(deltaY, deltaX);
    }
}
