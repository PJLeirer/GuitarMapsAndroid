package com.example.gmapandroid;

import android.util.Log;

public class Note {

    private int xPos;
    private int yPos;
    private String noteName;
    private int noteValue;
    private int octave;
    private boolean isPressed = false;
    private Player player;
    private int strNum;

    public Note(int x, int y, int val, String name, int oct, int sn) {
        xPos = x;
        yPos = y;
        noteValue = val;
        noteName = name;
        octave = oct;
        strNum = sn;
        player = new Player();
    }
    public String getNoteName() {
        return noteName;
    }
    public int getNoteValue() {
        return noteValue;
    }
    public int getOctave() {
        return octave;
    }
    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public boolean isPressed() {
        return isPressed;
    }

    public void setPressed(boolean pressed) {
        isPressed = pressed;
    }

    public void playNote() {
        // play note
        player.play(strNum, noteName, octave);
        Log.d("NOTE:", "Playing note name: " + noteName + ", value:  " + noteValue + ", oct: " + octave + ", sn: " + strNum + ".");
    }
}
