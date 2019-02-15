package com.example.imagesearchapp;

import android.content.Context;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.imagesearchapp", appContext.getPackageName());
    }


    @Test
    public void onInsert() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        DBHandler dbHandler = new DBHandler(appContext);
        boolean output = dbHandler.onInsert("a","aa");
        boolean expected = true;
        assertEquals(expected,output);
    }

    @Test
    public void shouldReturnData(){
        boolean expected = true;
        boolean output = false;
        Context appContext = InstrumentationRegistry.getTargetContext();
        DBHandler dbHandler = new DBHandler(appContext);
        boolean output1 = dbHandler.onInsert("a","aa");
        Cursor cursor = dbHandler.getAllData("a");
        if (!cursor.isAfterLast()){
            output = true;
        }

        assertEquals(expected,output);
    }
}
