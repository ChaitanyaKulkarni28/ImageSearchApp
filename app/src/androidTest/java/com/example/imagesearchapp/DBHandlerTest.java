package com.example.imagesearchapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DBHandlerTest {

    @Test
    public void onInsert() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        DBHandler dbHandler = new DBHandler(appContext);
        boolean output = dbHandler.onInsert("a", "aa");
        boolean expected = true;
//        assertEquals(expected, output);
        assertTrue(expected==output);
    }
}