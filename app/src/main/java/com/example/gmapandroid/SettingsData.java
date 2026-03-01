package com.example.gmapandroid;

//import androidx.room.Entity;

public class SettingsData {
    private static int key = 0;
    private static int scale = 0;
    private static int mode = 0;
    private static int position = 0;


    public static Boolean SetFretboardSettings(int k, int s, int m, int p) {
        key = k;
        scale = s;
        mode = m;
        position = p;
        return true;
    }

    public static int[] GetFretbordSettingsData() {
        int[] settings = new int[4];
        settings[0] = key;
        settings[1] = scale;
        settings[2] = mode;
        settings[3] = position;
        return settings;
    }

}
