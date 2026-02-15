package com.example.gmapandroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

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
    private int mMode = 0;
    private List<List<Note>> notePositions;
    private int noteImageSize = 42; // change to percent of fret width
    private int noteImageYOffset = 42;
    Dictionary<String, Bitmap> images;

    public FullGuitarMap(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(Color.BLACK);
    }

    public void setImages(Dictionary<String, Bitmap> imageSet) {
        images = imageSet;
    }

    public void updateSettings(int key, int scale, int mode) {
        mKey = key;
        mScale = scale;
        mMode = mode;
        buildNotePositions();
        invalidate(); // Redraw the view with new notes
    }

    public List<List<Integer>> getFretboard() {
        List<List<Integer>> fretboard = new ArrayList<>();
        for (int i = 0; i < numStrings; i++) {
            List<Integer> strCol = new ArrayList<>();
            int n = NotesAndScales.defaultOpenNoteTuning[i];

            for (int j = 0; j < numFrets; j++) {
                int physicalNote = (n + j) % 12;
                int interval = (physicalNote - mKey + 12) % 12;
                int scaleDegree = 0;
                switch (mMode) {
                    case 0: // Ionian
                        if (mScale == 1) scaleDegree = NotesAndScales.pentatonicIonianScale[interval];
                        else if (mScale == 2) scaleDegree = NotesAndScales.triadIonianScale[interval];
                        else scaleDegree = NotesAndScales.diatonicIonianScale[interval];
                        break;
                    case 1: // Dorian
                        if (mScale == 1) scaleDegree = NotesAndScales.pentatonicDorianScale[interval];
                        else if (mScale == 2) scaleDegree = NotesAndScales.triadDorianScale[interval];
                        else scaleDegree = NotesAndScales.diatonicDorianScale[interval];
                        break;
                    case 2: // Phrygian
                        if (mScale == 1) scaleDegree = NotesAndScales.pentatonicPhrygianScale[interval];
                        else if (mScale == 2) scaleDegree = NotesAndScales.triadPhrygianScale[interval];
                        else scaleDegree = NotesAndScales.diatonicPhrygianScale[interval];
                        break;
                    case 3: // Lydian
                        if (mScale == 1) scaleDegree = NotesAndScales.pentatonicLydianScale[interval];
                        else if (mScale == 2) scaleDegree = NotesAndScales.triadLydianScale[interval];
                        else scaleDegree = NotesAndScales.diatonicLydianScale[interval];
                        break;
                    case 4: // Mixolydian
                        if (mScale == 1) scaleDegree = NotesAndScales.pentatonicMixolydianScale[interval];
                        else if (mScale == 2) scaleDegree = NotesAndScales.triadMixolydianScale[interval];
                        else scaleDegree = NotesAndScales.diatonicMixolydianScale[interval];
                        break;
                    case 5: // Aeolian
                        if (mScale == 1) scaleDegree = NotesAndScales.pentatonicAeolianScale[interval];
                        else if (mScale == 2) scaleDegree = NotesAndScales.triadAeolianScale[interval];
                        else scaleDegree = NotesAndScales.diatonicAeolianScale[interval];
                        break;
                    case 6: // Locrian
                        if (mScale == 1) scaleDegree = NotesAndScales.pentatonicLocrianScale[interval];
                        else if (mScale == 2) scaleDegree = NotesAndScales.triadLocrianScale[interval];
                        else scaleDegree = NotesAndScales.diatonicLocrianScale[interval];
                        break;
                    default: // Default to Ionian Diatonic
                        scaleDegree = NotesAndScales.diatonicIonianScale[interval];
                        break;
                }
                strCol.add(scaleDegree);
            }
            fretboard.add(strCol);
        }
        return fretboard;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        buildNotePositions();
    }

    private void buildNotePositions() {
        if (width == 0 || height == 0) return;
        notePositions = new ArrayList<>();
        List<List<Integer>> fb = getFretboard();
        for(int i = 0; i < numStrings; i++) {
            float x = (float) (i * width / numStrings + ((width / numStrings) / 2));
            List<Note> string = new ArrayList<>();
            for(int j = 0; j < numFrets; j++) {
                int sn = fb.get(i).get(j);
                float y = (float) (j * height / numFrets + ((height / numFrets) / 2));
                Note note = new Note((int)x, (int)y, sn);
                string.add(note);
            }
            notePositions.add(string);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();
            if (notePositions == null) return false;
            for (List<Note> string : notePositions) {
                for (Note note : string) {
                    if (note.getNoteValue() > 0) {
                        if (x > note.getXPos() - noteImageSize && x < note.getXPos() + noteImageSize &&
                                y > note.getYPos() - noteImageSize && y < note.getYPos() + noteImageSize) {

                            playNote(note);
                            note.setPressed(true);
                            invalidate();

                            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                note.setPressed(false);
                                invalidate();
                            }, 400);
                            return true;
                        }
                    }
                }
            }
        }
        return super.onTouchEvent(event);
    }

    public void playNote(Note note) {
        Log.d("FullGuitarMap", "Playing note: " + note.getNoteValue());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width * 6;
        setMeasuredDimension(width, height);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (images == null || notePositions == null) return;

        @SuppressLint("DrawAllocation")
        Rect srcRect = new Rect(0, 0, images.get("rosewood").getWidth(), images.get("rosewood").getHeight());
        Rect destRect = new Rect(0, 0, width, height);
        canvas.drawBitmap(images.get("rosewood"), srcRect, destRect, paint);

        float headstocky = (int) ((height / numFrets) / 2);
        paint.setColor(Color.BLACK);
        Rect headstockSrcRect = new Rect(0, 0, images.get("headstock").getWidth(), images.get("headstock").getHeight());
        Rect headstockDestRect = new Rect(0, 0, width, (int)headstocky);
        canvas.drawBitmap(images.get("headstock"), headstockSrcRect, headstockDestRect, paint);

        paint.setColor(Color.WHITE);
        Rect fretSrcRect = new Rect(0, 0, images.get("fret").getWidth(), images.get("fret").getHeight());
        for (int i = 0; i < numFrets; i++) {
            headstocky = (int) (i * height / numFrets + ((height / numFrets) / 2));
            if( i == 0) {
                canvas.drawRect(0, (int)headstocky-24, width, (int)headstocky+6, paint);
            } else {
                Rect fretDestRect = new Rect(0, (int)headstocky, width, (int)headstocky+8);
                canvas.drawBitmap(images.get("fret"), fretSrcRect, fretDestRect, paint);
            }
        }

        paint.setColor(Color.LTGRAY);
        for (int i = 0; i < numStrings; i++) {
            float x = (float) (i * width / numStrings + ((width / numStrings) / 2));
            canvas.drawLine(x, 0, x, height, paint);
        }

        paint.setColor(Color.RED);
        paint.setTextSize(12);
        for (List<Note> string : notePositions) {
            for (Note note : string) {
                if (note.getNoteValue() > 0) {
                    Bitmap noteImage;
                    String imageSuffix = note.isPressed() ? "On" : "Off";
                    switch(note.getNoteValue()) {
                        case 1: noteImage = images.get("root" + imageSuffix); break;
                        case 2: noteImage = images.get("second" + imageSuffix); break;
                        case 3: noteImage = images.get("third" + imageSuffix); break;
                        case 4: noteImage = images.get("fourth" + imageSuffix); break;
                        case 5: noteImage = images.get("fifth" + imageSuffix); break;
                        case 6: noteImage = images.get("sixth" + imageSuffix); break;
                        case 7: noteImage = images.get("seventh" + imageSuffix); break;
                        default: noteImage = images.get("rootOff"); break;
                    }
                    if (noteImage == null) continue;
                    Rect noteSrcRect = new Rect(0, 0, noteImage.getWidth(), noteImage.getHeight());
                    Rect noteDestRect = new Rect((int)note.getXPos() - (noteImageSize/2), (int)(note.getYPos() - noteImageYOffset) - (noteImageSize/2), (int)note.getXPos() + (noteImageSize/2), (int)(note.getYPos() - noteImageYOffset) + (noteImageSize/2));
                    canvas.drawBitmap(noteImage, noteSrcRect, noteDestRect, paint);
                }
            }
        }
    }
}
