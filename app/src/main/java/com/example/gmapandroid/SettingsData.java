package com.example.gmapandroid;

//import androidx.room.Entity;

public class SettingsData {
    private static int key = 0;
    private static int scale = 0;
    private static int mode = 0;

    public static Boolean SetFretboardSettings(int k, int s, int m) {
        key = k;
        scale = s;
        mode = m;
        return true;
    }

    public static int[] GetFretbordSettingsData() {
        int[] settings = new int[3];
        settings[0] = key;
        settings[1] = scale;
        settings[2] = mode;
        return settings;
    }

}
