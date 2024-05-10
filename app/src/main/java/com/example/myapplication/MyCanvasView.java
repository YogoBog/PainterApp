package com.example.myapplication;

import static com.example.myapplication.MainActivity.circActive;
import static com.example.myapplication.MainActivity.pathActive;
import static com.example.myapplication.MainActivity.polyActive;
import static com.example.myapplication.MainActivity.recActive;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.List;

public class MyCanvasView extends View {

    private int edges; // Number of edges for polygon
    private Shape shape; // Current shape being drawn
    private List<Shape> shapes; // List to hold all drawn shapes
    private Paint mFramePaint; // Paint for the frame
    private Paint cPaint; // Paint for drawing shapes
    private int mBackgroundColor; // Background color of the canvas
    private Canvas mExtraCanvas; // Canvas for drawing shapes off-screen
    private Bitmap mExtraBitmap; // Bitmap for off-screen drawing
    private Rect mFrame; // Frame surrounding the canvas
    private float prevX, prevY; // Previous touch coordinates
    private int drawColor; // Default drawing color
    private int cDrawColor; // Current drawing color
    private boolean isDrawn; // Flag to check if shape is drawn
    private boolean isFinished; // Flag to check if shape drawing is finished

    // Constructor
    MyCanvasView(Context context) {
        this(context, null);
    }

    // Constructor with AttributeSet
    public MyCanvasView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        // Initialize colors
        mBackgroundColor = ResourcesCompat.getColor(getResources(), R.color.teal_200, null);
        drawColor = ResourcesCompat.getColor(getResources(), R.color.black, null);
        int frameColor = ResourcesCompat.getColor(getResources(), R.color.colorAccent, null);

        // Initialize frame paint
        mFramePaint = new Paint();
        mFramePaint.setColor(frameColor);
        mFramePaint.setStyle(Paint.Style.STROKE);
        mFramePaint.setStrokeWidth(14);

        // Initialize drawing paint
        cPaint = new Paint();
        cPaint.setColor(drawColor);
        cPaint.setAntiAlias(true);
        cPaint.setDither(true);
        cPaint.setStyle(Paint.Style.STROKE);
        cPaint.setStrokeJoin(Paint.Join.ROUND);
        cPaint.setStrokeCap(Paint.Cap.ROUND);
        cPaint.setStrokeWidth(12);

        cDrawColor = drawColor;

        isDrawn = false;
        isFinished = false;

        shapes = new ArrayList<>();
    }

    // Called when the size of the view changes
    @Override
    protected void onSizeChanged(int width, int height,
                                 int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);

        // Create off-screen bitmap and canvas
        mExtraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mExtraCanvas = new Canvas(mExtraBitmap);
        mExtraCanvas.drawColor(mBackgroundColor);

        // Define the frame surrounding the canvas
        int inset = 40;
        mFrame = new Rect(inset, inset, width - inset, height - inset);
    }

    // Called to draw the view
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw all shapes on the off-screen canvas
        mExtraCanvas.drawColor(mBackgroundColor);
        for (Shape s : shapes) {
            s.drawMe(mExtraCanvas);
        }

        // If a shape is being drawn and finished, draw it on the off-screen canvas
        if (isFinished && shape != null) {
            shape.drawMe(mExtraCanvas);
        }

        // Draw the off-screen canvas on the main canvas
        canvas.drawBitmap(mExtraBitmap, 0, 0, null);

        // Draw the frame around the canvas
        canvas.drawRect(mFrame, mFramePaint);
    }

    // Called when a touch event is detected
    private static final float TOUCH_TOLERANCE = 4;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchStart(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                invalidate();
                break;
            default:
        }
        return true;
    }

    // Method called when touch begins
    private void touchStart(float x, float y) {
        prevX = x;
        prevY = y;

        // Determine the type of shape to draw based on user selection
        if (pathActive)
            shape = new MyPath(prevX, prevY, cPaint);
        else if (circActive)
            shape = new Circle(prevX, prevY, cPaint);
        else if (recActive)
            shape = new Rectangle(prevX, prevY, cPaint);
        else if (polyActive)
            shape = new Polygon(prevX, prevY, cPaint, edges);

    }

    // Method called when touch moves
    private void touchMove(float x, float y) {
        float dx = Math.abs(x - prevX);
        float dy = Math.abs(y - prevY);

        // If touch movement is significant, update the shape being drawn
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            shape.update(x, y);
            invalidate();
            isFinished = true;
        }
    }

    // Method called when touch ends
    private void touchUp() {
        // If drawing is finished, add the shape to the list of shapes drawn
        if (isFinished) {
            shapes.add(shape);
            isDrawn = true;
            isFinished = false;
            invalidate();
        }
    }

    // Method to set the number of edges for polygon
    public void setEdges(int nEdges) {
        edges = nEdges;
    }

    // Method to reset the canvas
    public void reset() {
        cPaint.setColor(ResourcesCompat.getColor(getResources(), R.color.black, null));
        shapes.clear();
        mExtraCanvas.drawColor(mBackgroundColor);
        invalidate();
    }

    // Method to set the drawing color
    public void setDrawColor(int color) {
        if (isDrawn) {
            cPaint = new Paint(cPaint);
            isDrawn = false;
        }
        cPaint.setColor(color);
    }

    // Method to undo the last drawn shape
    public void undo() {
        if (!shapes.isEmpty()) {
            shapes.remove(shapes.size() - 1);
            mExtraCanvas.drawColor(mBackgroundColor);
            invalidate();
            if (!shapes.isEmpty())
                for (Shape shape : shapes)
                    shape.drawMe(mExtraCanvas);
        }
    }

}
