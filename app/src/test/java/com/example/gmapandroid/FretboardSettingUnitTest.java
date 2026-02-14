package com.example.gmapandroid;

import org.junit.Test;

import static org.junit.Assert.*;

public class FretboardSettingUnitTest {

    @Test
    public void testFretboardSetting() {

        assertEquals(true, SettingsData.SetFretboardSettings(5, 2, 5));

        assertArrayEquals(new int[]{5, 2, 5}, SettingsData.GetFretbordSettingsData());
    }
}
