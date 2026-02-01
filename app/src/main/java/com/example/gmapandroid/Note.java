package com.example.gmapandroid;

public class Note {

    private int xPos;
    private int yPos;
    private String noteName;
    private int noteValue;
    private int octave;

    public Note(int x, int y, int val) {
        xPos = x;
        yPos = y;
        noteValue = val;
    }

    public int getNoteValue() {
        return noteValue;
    }
    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }
}
