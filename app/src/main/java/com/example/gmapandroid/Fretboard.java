package com.example.gmapandroid;

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
    private int[] diatonicScale = {0, 2, 4, 5, 7, 9, 11};
    private int[] pentatonicScale = {0, 2, 4, 7, 9};
    private int[] triadScale = {0, 4, 7};

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

    public List<List<Integer>> getFretboard() {
        List<List<Integer>> fretboard = new ArrayList<>();
        for (int i = 0; i < numFrets; i++) {
            List<Integer> strCol = new ArrayList<>();
            for (int j = 0; j < numStrings; j++) {
                // Calculate the note index for the current string and fret
                int note = (openNoteTuning[j] + i) % 12;
                // if note is in current scale add to fretboard
                if (mScale == 0) {
                    for (int k = 0; k < diatonicScale.length; k++) {
                        if (note == diatonicScale[k]) {
                            strCol.add(note);
                            break;
                        }

                    }
                }
                strCol.add(note);
            }
            fretboard.add(strCol);
        }

        return fretboard;
    }
}
