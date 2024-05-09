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

    private int edges;
    private Shape shape;
    private List<Shape> shapes;
    private Paint mFramePaint;
    private Paint cPaint;
    private int mBackgroundColor;
    private Canvas mExtraCanvas;
    private Bitmap mExtraBitmap;
    private Rect mFrame;
    private float prevX, prevY;
    private int drawColor;
    private int cDrawColor;
    private boolean isDrawn;
    private boolean isFinished;

    MyCanvasView(Context context) {
        this(context, null);
    }

    public MyCanvasView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        mBackgroundColor =
                ResourcesCompat.getColor(getResources(), R.color.teal_200, null);
        drawColor = ResourcesCompat.getColor(getResources(), R.color.black, null);
        int frameColor = ResourcesCompat.getColor(getResources(), R.color.colorAccent, null);

        mFramePaint = new Paint();
        mFramePaint.setColor(frameColor);
        mFramePaint.setStyle(Paint.Style.STROKE);
        mFramePaint.setStrokeWidth(14);

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

    @Override
    protected void onSizeChanged(int width, int height,
                                 int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        mExtraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mExtraCanvas = new Canvas(mExtraBitmap);
        mExtraCanvas.drawColor(mBackgroundColor);

        int inset = 40;
        mFrame = new Rect(inset, inset, width - inset, height - inset);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mExtraCanvas.drawColor(mBackgroundColor);

        for (Shape s : shapes) {
            s.drawMe(mExtraCanvas);
        }

        if (isFinished && shape != null) {
            shape.drawMe(mExtraCanvas);
        }

        canvas.drawBitmap(mExtraBitmap, 0, 0, null);
        canvas.drawRect(mFrame, mFramePaint);
    }

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

    private void touchStart(float x, float y) {
        prevX = x;
        prevY = y;

        if (pathActive)
            shape = new MyPath(prevX, prevY, cPaint);
        else if (circActive)
            shape = new Circle(prevX, prevY, cPaint);
        else if (recActive)
            shape = new Rectangle(prevX, prevY, cPaint);
        else if (polyActive)
            shape = new Polygon(prevX, prevY, cPaint, edges);

    }

    private void touchMove(float x, float y) {
        float dx = Math.abs(x - prevX);
        float dy = Math.abs(y - prevY);

        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            shape.update(x, y);
            invalidate();
            isFinished = true;
        }
    }

    private void touchUp() {
        if (isFinished) {
            shapes.add(shape);
            isDrawn = true;
            isFinished = false;
            invalidate();
        }
    }

    public void setEdges(int nEdges) {
        edges = nEdges;
    }

    public void reset() {
        cPaint.setColor(ResourcesCompat.getColor(getResources(), R.color.black, null));
        shapes.clear();
        mExtraCanvas.drawColor(mBackgroundColor);
        invalidate();
    }

    public void setDrawColor(int color) {
        if (isDrawn) {
            cPaint = new Paint(cPaint);
            isDrawn = false;
        }
        cPaint.setColor(color);
    }

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