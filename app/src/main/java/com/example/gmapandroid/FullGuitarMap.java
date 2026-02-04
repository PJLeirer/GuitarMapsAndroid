package com.example.gmapandroid;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;


public class FullGuitarMap extends View {

    private Paint paint = new Paint();
    private int width;
    private int height;
    private int numFrets = 22;
    private int numStrings = 6;
    private int mKey = 0;
    private int mScale = 0;
    private List<List<Note>> notePositions;
    private int noteImageSize = 42; // change to percent of fret width
    private int noteImageYOffset = 42;
    Dictionary<String, Bitmap> images;

    public FullGuitarMap(Context context, Dictionary<String, Bitmap> imageSet, int frets, int strings, int key, int scale) {
        super(context);
        //mFretboard = fretboard;
        numFrets = frets;
        numStrings = strings;
        mKey = key;
        mScale = scale;
        images = imageSet;
        setBackgroundColor(Color.BLACK); // DOESNT WORK!!! ??
    }

    public List<List<Integer>> getFretboard() {
        List<List<Integer>> fretboard = new ArrayList<>();
        for (int i = 0; i < numStrings; i++) {
            List<Integer> strCol = new ArrayList<>();
            int n = NotesAndScales.defaultOpenNoteTuning[i];

            for (int j = 0; j < numFrets; j++) {
                // Calculate the physical note on the fretboard, regardless of key
                int physicalNote = (n + j) % 12;

                // Calculate the note's interval relative to the current key
                int interval = (physicalNote - mKey + 12) % 12;

                int scaleDegree = 0;
                switch (mScale) {
                    case 0:
                        // Diatonic
                        scaleDegree = NotesAndScales.diatonicScale[interval];
                        break;
                    case 1:
                        // Pentatonic
                        scaleDegree = NotesAndScales.pentatonicScale[interval];
                        break;
                    case 2:
                        // Triad
                        scaleDegree = NotesAndScales.triadScale[interval];
                        break;
                    default:
                        // Default to diatonic
                        scaleDegree = NotesAndScales.diatonicScale[interval];
                        break;
                }
                strCol.add(scaleDegree);
            }
            fretboard.add(strCol);
        }
        return fretboard;
    }

    private void buildNotePositions() {

        width = getWidth();
        height = getHeight();

        // Build note objects
        // calculate note positions before drawing
        notePositions = new ArrayList();
        List<List<Integer>> fb = getFretboard();

        for(int i =0; i < numStrings; i++) {

            float x = (float) (i * width / numStrings + ((width / numStrings) / 2));
            List<Note> string = new ArrayList();
            //Log.d(":", "string: " + i);
            for(int j =0; j < numFrets; j++) {
                int sn = fb.get(i).get(j);
                //Log.d(":", "fret: " + j + " note: " + sn);

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

        Rect srcRect = new Rect(0, 0, images.get("rosewood").getWidth(), images.get("rosewood").getHeight());
        Rect destRect = new Rect(0, 0, width, height);
        canvas.drawBitmap(images.get("rosewood"), srcRect, destRect, paint);




        // draw headstock
        float headstocky = (int) ((height / numFrets) / 2);
        paint.setColor(Color.BLACK);
        //canvas.drawRect(0, 0, width, (int)headstocky, paint);

        Rect headstockSrcRect = new Rect(0, 0, images.get("headstock").getWidth(), images.get("headstock").getHeight());
        Rect headstockDestRect = new Rect(0, 0, width, (int)headstocky);
        canvas.drawBitmap(images.get("headstock"), headstockSrcRect, headstockDestRect, paint);


        // Draw frets
        paint.setColor(Color.WHITE);
        Rect fretSrcRect = new Rect(0, 0, images.get("fret").getWidth(), images.get("fret").getHeight());
        for (int i = 0; i < 22; i++) {
            headstocky = (int) (i * height / numFrets + ((height / numFrets) / 2));
            if( i == 0) {
                //draw nut
                canvas.drawRect(0, (int)headstocky-24, width, (int)headstocky+6, paint);
            } else {
                //draw fret
                Rect fretDestRect = new Rect(0, (int)headstocky, width, (int)headstocky+8);
                canvas.drawBitmap(images.get("fret"), fretSrcRect, fretDestRect, paint);
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
                    Bitmap noteImage;
                    switch(note.getNoteValue()) {
                        case 1:
                            paint.setColor(Color.RED);
                            noteImage = images.get("rootOff");
                            break;
                        case 2:
                            paint.setColor(Color.YELLOW);
                            noteImage = images.get("secondOff");
                            break;
                        case 3:
                            paint.setColor(Color.YELLOW);
                            noteImage = images.get("thirdOff");
                            break;
                        case 4:
                            paint.setColor(Color.MAGENTA);
                            noteImage = images.get("fourthOff");
                            break;
                        case 5:
                            paint.setColor(Color.GREEN);
                            noteImage = images.get("fifthOff");
                            break;
                        case 6:
                            paint.setColor(Color.MAGENTA);
                            noteImage = images.get("sixthOff");
                            break;
                        case 7:
                            paint.setColor(Color.LTGRAY);
                            noteImage = images.get("seventhOff");
                            break;
                        default:
                            paint.setColor(Color.BLACK);
                            noteImage = images.get("rootOff");
                            break;
                    }
                    //canvas.drawCircle(note.getXPos(), note.getYPos(), 20, paint);
                    Rect noteSrcRect = new Rect(0, 0, noteImage.getWidth(), noteImage.getHeight());
                    Rect noteDestRect = new Rect((int)note.getXPos() - (noteImageSize/2), (int)(note.getYPos() - noteImageYOffset) - (noteImageSize/2), (int)note.getXPos() + (noteImageSize/2), (int)(note.getYPos() - noteImageYOffset) + (noteImageSize/2));
                    canvas.drawBitmap(noteImage, noteSrcRect, noteDestRect, paint);

                }
            }
        }

        // end of drawing
    }
}
