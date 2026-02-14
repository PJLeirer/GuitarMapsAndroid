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
    
    // Diatonic Scales
    public static final int[] diatonicIonianScale =     {1, 0, 2, 0, 3, 4, 0, 5, 0, 6, 0, 7};
    public static final int[] diatonicDorianScale =     {1, 0, 2, 3, 0, 4, 0, 5, 0, 6, 7, 0};
    public static final int[] diatonicPhrygianScale =   {1, 2, 0, 3, 0, 4, 0, 5, 6, 0, 7, 0};
    public static final int[] diatonicLydianScale =     {1, 0, 2, 0, 3, 0, 4, 5, 0, 6, 0, 7};
    public static final int[] diatonicMixolydianScale = {1, 0, 2, 0, 3, 4, 0, 5, 0, 6, 7, 0};
    public static final int[] diatonicAeolianScale =    {1, 0, 2, 3, 0, 4, 0, 5, 6, 0, 7, 0};
    public static final int[] diatonicLocrianScale =    {1, 2, 0, 3, 0, 4, 5, 0, 6, 0, 7, 0};

    // Pentatonic Scales
    public static final int[] pentatonicIonianScale =     {1, 0, 2, 0, 3, 0, 0, 5, 0, 6, 0, 0};
    public static final int[] pentatonicDorianScale =     {1, 0, 2, 3, 0, 4, 0, 5, 0, 0, 7, 0};
    public static final int[] pentatonicPhrygianScale =   {1, 0, 0, 3, 0, 4, 0, 5, 6, 0, 7, 0};
    public static final int[] pentatonicLydianScale =     {1, 0, 2, 0, 3, 0, 4, 5, 0, 6, 0, 0};
    public static final int[] pentatonicMixolydianScale = {1, 0, 2, 0, 3, 0, 0, 5, 0, 0, 7, 0};
    public static final int[] pentatonicAeolianScale =    {1, 0, 0, 3, 0, 4, 0, 5, 0, 0, 7, 0};
    public static final int[] pentatonicLocrianScale =    {1, 0, 0, 3, 0, 4, 5, 0, 0, 0, 7, 0};

    // Triad Scales
    public static final int[] triadIonianScale =      {1, 0, 0, 0, 3, 0, 0, 5, 0, 0, 0, 0};
    public static final int[] triadDorianScale =      {1, 0, 0, 3, 0, 0, 0, 5, 0, 0, 0, 0};
    public static final int[] triadPhrygianScale =    {1, 0, 0, 3, 0, 0, 0, 5, 0, 0, 0, 0};
    public static final int[] triadLydianScale =      {1, 0, 0, 0, 3, 0, 0, 5, 0, 0, 0, 0};
    public static final int[] triadMixolydianScale =  {1, 0, 0, 0, 3, 0, 0, 5, 0, 0, 0, 0};
    public static final int[] triadAeolianScale =     {1, 0, 0, 3, 0, 0, 0, 5, 0, 0, 0, 0};
    public static final int[] triadLocrianScale =     {1, 0, 0, 3, 0, 0, 5, 0, 0, 0, 0, 0};
}
