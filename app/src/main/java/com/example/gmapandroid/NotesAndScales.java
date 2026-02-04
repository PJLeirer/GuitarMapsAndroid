package com.example.gmapandroid;

public class NotesAndScales {

    public static final String[] noteNames = {
            "C", // 0
            "C#", // 1
            "D", // 2
            "D#", // 3
            "E", // 4
            "F", // 5
            "F#", // 6
            "G", // 7
            "G#", // 8
            "A", // 9
            "A#", // 10
            "B" // 11
    };

    public static final int[] defaultOpenNoteTuning = {4, 9, 2, 7, 11, 4}; // Default, E Standard
    public static final int[] diatonicScale =   {1, 0, 2, 0, 3, 4, 0, 5, 0, 6, 0, 7};
    public static final int[] pentatonicScale = {1, 0, 2, 0, 3, 0, 0, 5, 0, 6, 0, 0};
    public static final int[] triadScale =      {1, 0, 0, 0, 3, 0, 0, 5, 0, 0, 0, 0};
}
