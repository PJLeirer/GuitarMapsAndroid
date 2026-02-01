package com.example.gmapandroid;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
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
        setMeasuredDimension(width, width * 6);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Get the width and height of the view
        width = getWidth();
        height = getHeight();

        // fill background black
        //canvas.drawColor(Color.BLACK);
        @SuppressLint("DrawAllocation")
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.rosewood1);
        Rect srcRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Rect destRect = new Rect(0, 0, width, height);
        canvas.drawBitmap(bitmap, srcRect, destRect, paint);


        Bitmap fretImage = BitmapFactory.decodeResource(getResources(), R.drawable.silver_fret);
        Rect fretSrcRect = new Rect(0, 0, fretImage.getWidth(), fretImage.getHeight());

        // draw headstock
        float headstocky = (int) ((height / numFrets) / 2);
        paint.setColor(Color.BLACK);
        //canvas.drawRect(0, 0, width, (int)headstocky, paint);
        Bitmap headstockImage = BitmapFactory.decodeResource(getResources(), R.drawable.headstock_lower);
        Rect headstockSrcRect = new Rect(0, 0, headstockImage.getWidth(), headstockImage.getHeight());
        Rect headstockDestRect = new Rect(0, 0, width, (int)headstocky);
        canvas.drawBitmap(headstockImage, headstockSrcRect, headstockDestRect, paint);


        // Draw frets
        paint.setColor(Color.WHITE);
        for (int i = 0; i < 22; i++) {
            headstocky = (int) (i * height / numFrets + ((height / numFrets) / 2));
            if( i == 0) {
                //draw nut
                canvas.drawRect(0, (int)headstocky-24, width, (int)headstocky+6, paint);
            } else {
                //draw fret
                Rect fretDestRect = new Rect(0, (int)headstocky, width, (int)headstocky+8);
                canvas.drawBitmap(fretImage, fretSrcRect, fretDestRect, paint);
            }


        }

        // Draw the guitar strings
        paint.setColor(Color.LTGRAY);
        for (int i = 0; i < 6; i++) {
            float x = (float) (i * width / numStrings + ((width / numStrings) / 2));
            canvas.drawLine(x, 0, x, height, paint);
        }




    }

}
