package com.example.gmapandroid;


import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Fretboard {

    private int numFrets;
    private int numStrings;
    private int rootNote;
    private int mScale;

    private String[] noteNames = {
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

    private int[] openNoteTuning = {4, 9, 2, 7, 11, 4}; // Default, E Standard
    private int[] diatonicScale =   {1, 0, 2, 0, 3, 4, 0, 5, 0, 6, 0, 7};
    private int[] pentatonicScale = {1, 0, 0, 0, 3, 0, 0, 5, 0, 6, 0, 0};
    private int[] triadScale =      {1, 0, 0, 0, 3, 0, 0, 5, 0, 0, 0, 0};

    public Fretboard(int frets, int strings , int root, int scale) {
        numStrings = strings;
        numFrets = frets;
        rootNote = root;
        mScale = scale;
    }

    public void buildFretboard(int root, int scale) {
        rootNote = root;
        mScale = scale;

        // update fretboard

    }
    public List<List<Integer>> buildFretboard() {

        List<List<Integer>> fretboard = new ArrayList<>();

        for (int i = 0; i < numStrings; i++) {

            List<Integer> strCol = new ArrayList<>();

            for (int j = 0; j < numFrets; j++) {
                // Calculate the note index for the current string and fret
                int note = (openNoteTuning[j] + i) % 12;

            }
            fretboard.add(strCol);
        }
        return fretboard;
    }

    public List<List<Integer>> getFretboard() {
        List<List<Integer>> fretboard = new ArrayList<>();
        for (int i = 0; i < numStrings; i++) {
            List<Integer> strCol = new ArrayList<>();
            //Log.d("", "string number: " + i);
            int n = openNoteTuning[i];

            for (int j = 0; j < numFrets; j++) {
                // Calculate the note index for the current string and fret
                int note = (n + j) % 12;

                String noteLetter = noteNames[note];

                switch (mScale) {
                    case 0:
                        // Diatonic
                        strCol.add(diatonicScale[note]);
                        break;
                    case 1:
                        // Pentatonic
                        strCol.add(pentatonicScale[note]);
                        break;
                    case 2:
                        // Triad
                        strCol.add(triadScale[note]);
                        break;
                    default:
                        // Default to diatonic
                        strCol.add(diatonicScale[note]);
                        break;

                }
                //strCol.add(note);
            }
            fretboard.add(strCol);
        }


        return fretboard;
    }
}
