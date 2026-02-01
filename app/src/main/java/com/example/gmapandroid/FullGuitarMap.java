package com.example.gmapandroid;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;


public class FullGuitarMap extends View {

    private Paint paint = new Paint();
    private int width;
    private int height;
    private int numFrets = 22;
    private int numStrings = 6;
    private List<List<Note>> notePositions;
    private Fretboard mFretboard;



    public FullGuitarMap(Context context, Fretboard fretboard) {
        super(context);
        mFretboard = fretboard;
        //numFrets = fretboard.getNumFrets();
        //numStrings = fretboard.getNumStrings();
        //buildNotePositions();
    }

    private void buildNotePositions() {

        width = getWidth();
        height = getHeight();

        // Build note objects
        // calculate note positions before drawing
        notePositions = new ArrayList();
        List<List<Integer>> fb = mFretboard.getFretboard();

        for(int i =0; i < numStrings; i++) {

            float x = (float) (i * width / numStrings + ((width / numStrings) / 2));
            List<Note> string = new ArrayList();
            Log.d(":", "string: " + i);
            for(int j =0; j < numFrets; j++) {
                int sn = fb.get(i).get(j);
                Log.d(":", "fret: " + j + " note: " + sn);

                float y = (float) (j * height / numFrets + ((height / numFrets) / 2));
                Note note = new Note((int)x, (int)y, sn);
                string.add(note);
                //Log.d(":","y:" + y);
            }
            notePositions.add(string);
        }
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


        buildNotePositions();
        // draw notes
        paint.setColor(Color.RED);
        paint.setTextSize(12);
        for (List<Note> string : notePositions) {
            //Log.d("Note: ", " for each string");
            for (Note note : string) {
                //Log.d("Note:", "for each note X:" + note.getXPos() + " - Y:" + note.getYPos());
                if (note.getNoteValue() > 0) {
                    switch(note.getNoteValue()) {
                        case 1:
                            paint.setColor(Color.RED);
                            break;
                        case 2:
                            paint.setColor(Color.YELLOW);
                            break;
                        case 3:
                            paint.setColor(Color.YELLOW);
                            break;
                        case 4:
                            paint.setColor(Color.MAGENTA);
                            break;
                        case 5:
                            paint.setColor(Color.GREEN);
                            break;
                        case 6:
                            paint.setColor(Color.MAGENTA);
                            break;
                        case 7:
                            paint.setColor(Color.LTGRAY);
                            break;
                        default:
                            paint.setColor(Color.BLACK);
                            break;
                    }
                    canvas.drawCircle(note.getXPos(), note.getYPos(), 20, paint);
                }
            }
        }

        // end of drawing
    }
}
