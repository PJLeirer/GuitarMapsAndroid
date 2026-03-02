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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.List;


public class FullGuitarMap extends View {

    private Paint paint;
    private int width;
    private int height;
    private final int numFrets = 22;
    private final int numStrings = 6;
    private int mKey = 0;
    private int mScale = 0;
    private int mMode = 0;
    private int mPosition = 0;
    private List<List<Note>> notePositions;
    private final int noteImageSize = 42; // change to percent of fret width
    private final int dotImageSize = 60;
    private final int noteImageYOffset = 42;
    Dictionary<String, Bitmap> images;
    Integer[] currentScale;

    public FullGuitarMap(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(Color.BLACK);
        paint = new Paint();
    }

    public void setImages(Dictionary<String, Bitmap> imageSet) {
        images = imageSet;
    }

    public void updateSettings(int key, int scale, int mode, int position) {
        mKey = key;
        mScale = scale;
        mMode = mode;
        mPosition = position;
        buildNotePositions();
        invalidate(); // Redraw the view with new notes
    }

    private void setCurrentScale() {
        Integer[] scale;
        switch (mMode) {
            case 0: // Ionian
                if (mScale == 1) scale = NotesAndScales.pentatonicIonianScale;
                else if (mScale == 2) scale = NotesAndScales.triadIonianScale;
                else scale = NotesAndScales.diatonicIonianScale;
                break;
            case 1: // Dorian
                if (mScale == 1) scale = NotesAndScales.pentatonicDorianScale;
                else if (mScale == 2) scale = NotesAndScales.triadDorianScale;
                else scale = NotesAndScales.diatonicDorianScale;
                break;
            case 2: // Phrygian
                if (mScale == 1) scale = NotesAndScales.pentatonicPhrygianScale;
                else if (mScale == 2) scale = NotesAndScales.triadPhrygianScale;
                else scale = NotesAndScales.diatonicPhrygianScale;
                break;
            case 3: // Lydian
                if (mScale == 1) scale = NotesAndScales.pentatonicLydianScale;
                else if (mScale == 2) scale = NotesAndScales.triadLydianScale;
                else scale = NotesAndScales.diatonicLydianScale;
                break;
            case 4: // Mixolydian
                if (mScale == 1) scale = NotesAndScales.pentatonicMixolydianScale;
                else if (mScale == 2) scale = NotesAndScales.triadMixolydianScale;
                else scale = NotesAndScales.diatonicMixolydianScale;
                break;
            case 5: // Aeolian
                if (mScale == 1) scale = NotesAndScales.pentatonicAeolianScale;
                else if (mScale == 2) scale = NotesAndScales.triadAeolianScale;
                else scale = NotesAndScales.diatonicAeolianScale;
                break;
            case 6: // Locrian
                if (mScale == 1) scale = NotesAndScales.pentatonicLocrianScale;
                else if (mScale == 2) scale = NotesAndScales.triadLocrianScale;
                else scale = NotesAndScales.diatonicLocrianScale;
                break;
            default: // Default to Ionian Diatonic
                scale = NotesAndScales.diatonicIonianScale;
                break;
        }
        currentScale = scale;
    }

    private String getNoteName(int noteValue) {
        String n = "";
        // get the note position in currentsCale
        int pos = 0;
        try {
            pos = Arrays.asList(currentScale).indexOf(noteValue);
            n = NotesAndScales.noteNames[pos];
        } catch (Exception e) {
            Log.d("FullGuitarMap", "Error getting note name from value... " + noteValue + ", and pos: " + pos);
        }
        //int index = Arrays.asList(currentScale).indexOf(noteValue);

        return n;
    }

    public List<List<Integer>> getFretboard() {
        setCurrentScale();
        List<List<Integer>> fretboard = new ArrayList<>();
        for (int i = 0; i < numStrings; i++) {
            List<Integer> strCol = new ArrayList<>();
            int n = NotesAndScales.defaultOpenNoteTuning[i];

            for (int j = 0; j < numFrets; j++) {
                int physicalNote = (n + j) % 12;
                int interval = (physicalNote - mKey + 12) % 12;
                int scaleDegree = 0;
                scaleDegree = currentScale[interval];
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
        int[] openStringOctaves = {1, 1, 2, 2, 2, 3}; // Starting octaves for each string (EADGBe)

        for(int i = 0; i < numStrings; i++) {
            float x = (float) (i * width / numStrings + ((width / numStrings) / 2));
            List<Note> string = new ArrayList<>();
            int openNote = NotesAndScales.defaultOpenNoteTuning[i];

            for(int j = 0; j < numFrets; j++) {
                int physicalNote = (openNote + j);
                int octave = openStringOctaves[i] + (physicalNote / 12);
                int sn = fb.get(i).get(j);
                // get note name
                String noteName;
                if(sn !=0) {
                    noteName = getNoteName(sn-1);
                } else {
                    noteName = "";
                }
                // get note value
                int noteValue = sn;
                float y = (float) (j * height / numFrets + ((height / numFrets) / 2));
                Note note = new Note((int)x, (int)y, sn, noteName, octave, i);
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

                            note.playNote();
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
        Log.d("FullGuitarMap", "Playing note: " + note.getNoteValue() + " : " + note.getNoteName());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width * 6;
        setMeasuredDimension(width, height);
    }

    @Override
    public void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        if (images == null || notePositions == null) return;

        // draw fingerboard
        @SuppressLint("DrawAllocation")
        Rect srcRect = new Rect(0, 0, images.get("rosewood").getWidth(), images.get("rosewood").getHeight());
        @SuppressLint("DrawAllocation") Rect destRect = new Rect(0, 0, width, height);
        canvas.drawBitmap(images.get("rosewood"), srcRect, destRect, paint);

        // draw headstock
        float headstocky = (int) ((height / numFrets) / 2);
        paint.setColor(Color.BLACK);
        @SuppressLint("DrawAllocation") Rect headstockSrcRect = new Rect(0, 0, images.get("headstock").getWidth(), images.get("headstock").getHeight());
        @SuppressLint("DrawAllocation") Rect headstockDestRect = new Rect(0, 0, width, (int)headstocky);
        canvas.drawBitmap(images.get("headstock"), headstockSrcRect, headstockDestRect, paint);

        // draw frets
        paint.setColor(Color.WHITE);
        @SuppressLint("DrawAllocation") Rect inlaySrcRect = new Rect(0, 0, images.get("inlayDot").getWidth(), images.get("inlayDot").getHeight());
        @SuppressLint("DrawAllocation") Rect fretSrcRect = new Rect(0, 0, images.get("fret").getWidth(), images.get("fret").getHeight());
        int lastFretY = 0;
        for (int i = 0; i < numFrets; i++) {
            headstocky = (int) (i * height / numFrets + ((height / numFrets) / 2));
            if( i == 0) {
                canvas.drawRect(0, (int)headstocky-24, width, (int)headstocky+6, paint);
            } else {
                @SuppressLint("DrawAllocation") Rect fretDestRect = new Rect(0, (int)headstocky, width, (int)headstocky+8);
                canvas.drawBitmap(images.get("fret"), fretSrcRect, fretDestRect, paint);
            }
            //inlay dots
            if(i == 3 || i == 5 || i == 7 || i == 9 || i == 12 || i == 15 || i == 17 || i == 19 || i == 21) {
                Log.d("FullGuitarMap", "Drawing inlay dot at fret " + i);
                int dotY = (int)(headstocky - ((headstocky - lastFretY)/2));
                int dotX = width/2;
                if(i == 12) {
                    @SuppressLint("DrawAllocation") Rect inlayDestRect1 = new Rect(
                            dotX - (dotImageSize/2) - (width/3),
                            dotY - (dotImageSize/2),
                            dotX + (dotImageSize/2) - (width/3),
                            dotY + (dotImageSize/2)
                    );
                    canvas.drawBitmap(images.get("dot_1"), inlaySrcRect, inlayDestRect1, paint);
                    @SuppressLint("DrawAllocation") Rect inlayDestRect2 = new Rect(
                            dotX - (dotImageSize/2) + (width/3),
                            dotY - (dotImageSize/2),
                            dotX + (dotImageSize/2) + (width/3),
                            dotY + (dotImageSize/2)
                    );
                    canvas.drawBitmap(images.get("dot_2"), inlaySrcRect, inlayDestRect2, paint);
                } else {
                    @SuppressLint("DrawAllocation") Rect inlayDestRect = new Rect(
                            dotX - (dotImageSize/2),
                            dotY - (dotImageSize/2),
                            dotX + (dotImageSize/2),
                            dotY + (dotImageSize/2)
                    );
                    String imgName = "dot_" + i;
                    Bitmap img = images.get(imgName);
                    if(img == null) {
                        img = images.get("inlayDot");
                    }
                    Log.d("FullGuitarMap", "Drawing inlay dot_" + i );
                    canvas.drawBitmap(img, inlaySrcRect, inlayDestRect, paint);
                }

                //canvas.drawRect(0, (int)headstocky-24, width, (int)headstocky+6, paint);
            }
            lastFretY = (int)headstocky;
        }

        // draw strings
        paint.setColor(Color.LTGRAY);
        for (int i = 0; i < numStrings; i++) {
            float x = (float) (i * width / numStrings + ((width / numStrings) / 2));
            canvas.drawLine(x, 0, x, height, paint);
        }

        // draw notes
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
                    @SuppressLint("DrawAllocation") Rect noteSrcRect = new Rect(0, 0, noteImage.getWidth(), noteImage.getHeight());
                    @SuppressLint("DrawAllocation") Rect noteDestRect = new Rect((int)note.getXPos() - (noteImageSize/2), (int)(note.getYPos() - noteImageYOffset) - (noteImageSize/2), (int)note.getXPos() + (noteImageSize/2), (int)(note.getYPos() - noteImageYOffset) + (noteImageSize/2));
                    canvas.drawBitmap(noteImage, noteSrcRect, noteDestRect, paint);
                }
            }
        }
    }
}
