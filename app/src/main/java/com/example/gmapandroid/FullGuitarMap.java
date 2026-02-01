package com.example.gmapandroid;

import android.util.AttributeSet;
import android.view.View;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


public class FullGuitarMap extends View {

    private Paint paint = new Paint();
    private int width;
    private int height;

    private int numFrets = 22;
    private int numStrings = 6;


    public FullGuitarMap(Context context, Fretboard fretboard) {
        super(context);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        setMeasuredDimension(width, width * 3);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Get the width and height of the view
        width = getWidth();
        height = getHeight();

        // fill background black
        canvas.drawColor(Color.BLACK);


        // Draw frets
        paint.setColor(Color.WHITE);
        for (int i = 0; i < 22; i++) {
            float y = (float) (i * height / numFrets + ((height / numFrets) / 2));
            canvas.drawLine(0, y, width, y, paint);
        }

        // Draw the guitar strings
        paint.setColor(Color.LTGRAY);
        for (int i = 0; i < 6; i++) {
            float x = (float) (i * width / numStrings + ((width / numStrings) / 2));
            canvas.drawLine(x, 0, x, height, paint);
        }




    }

}
