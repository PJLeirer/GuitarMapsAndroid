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

    public static final Integer[] defaultOpenNoteTuning = {4, 9, 2, 7, 11, 4}; // Default, E Standard
    
    // Diatonic Scales
    public static final Integer[] diatonicIonianScale =     {1, 0, 2, 0, 3, 4, 0, 5, 0, 6, 0, 7};
    public static final Integer[] diatonicDorianScale =     {1, 0, 2, 3, 0, 4, 0, 5, 0, 6, 7, 0};
    public static final Integer[] diatonicPhrygianScale =   {1, 2, 0, 3, 0, 4, 0, 5, 6, 0, 7, 0};
    public static final Integer[] diatonicLydianScale =     {1, 0, 2, 0, 3, 0, 4, 5, 0, 6, 0, 7};
    public static final Integer[] diatonicMixolydianScale = {1, 0, 2, 0, 3, 4, 0, 5, 0, 6, 7, 0};
    public static final Integer[] diatonicAeolianScale =    {1, 0, 2, 3, 0, 4, 0, 5, 6, 0, 7, 0};
    public static final Integer[] diatonicLocrianScale =    {1, 2, 0, 3, 0, 4, 5, 0, 6, 0, 7, 0};

    // Pentatonic Scales
    public static final Integer[] pentatonicIonianScale =     {1, 0, 2, 0, 3, 0, 0, 5, 0, 6, 0, 0};
    public static final Integer[] pentatonicDorianScale =     {1, 0, 2, 3, 0, 4, 0, 5, 0, 0, 7, 0};
    public static final Integer[] pentatonicPhrygianScale =   {1, 0, 0, 3, 0, 4, 0, 5, 6, 0, 7, 0};
    public static final Integer[] pentatonicLydianScale =     {1, 0, 2, 0, 3, 0, 4, 5, 0, 6, 0, 0};
    public static final Integer[] pentatonicMixolydianScale = {1, 0, 2, 0, 3, 0, 0, 5, 0, 0, 7, 0};
    public static final Integer[] pentatonicAeolianScale =    {1, 0, 0, 3, 0, 4, 0, 5, 0, 0, 7, 0};
    public static final Integer[] pentatonicLocrianScale =    {1, 0, 0, 3, 0, 4, 5, 0, 0, 0, 7, 0};

    // Triad Scales
    public static final Integer[] triadIonianScale =      {1, 0, 0, 0, 3, 0, 0, 5, 0, 0, 0, 0};
    public static final Integer[] triadDorianScale =      {1, 0, 0, 3, 0, 0, 0, 5, 0, 0, 0, 0};
    public static final Integer[] triadPhrygianScale =    {1, 0, 0, 3, 0, 0, 0, 5, 0, 0, 0, 0};
    public static final Integer[] triadLydianScale =      {1, 0, 0, 0, 3, 0, 0, 5, 0, 0, 0, 0};
    public static final Integer[] triadMixolydianScale =  {1, 0, 0, 0, 3, 0, 0, 5, 0, 0, 0, 0};
    public static final Integer[] triadAeolianScale =     {1, 0, 0, 3, 0, 0, 0, 5, 0, 0, 0, 0};
    public static final Integer[] triadLocrianScale =     {1, 0, 0, 3, 0, 0, 5, 0, 0, 0, 0, 0};
}
