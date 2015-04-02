package com.labrats.android.flip9.test;

import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;

import com.labrats.android.flip9.data.Cheat;
import java.util.ArrayList;

/**
 * Created by Andrew & Jackie on 2015-04-01.
 */

public class CheatTest extends InstrumentationTestCase{

    AndroidTestCase tester = new AndroidTestCase();

     // test bfs when the answer is 1 solution away
    public void testOneClickAway(){
        ArrayList<Integer> actual = Cheat.getCheat(23);

        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.add(1);
        tester.assertEquals("Methods wrong", expected, actual);
    }

    // test out of bounds
    public void testOutOfBounds(){
        ArrayList<Integer> actual = Cheat.getCheat(512);
        ArrayList<Integer> expected = null;
        tester.assertEquals("Did not handle out of bounds", expected, actual);
    }
    // test when two click away from solution
    public void testTwoClicksAway(){
        ArrayList<Integer> actual = Cheat.getCheat(427);
        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.add(8);
        expected.add(0);
        tester.assertEquals("Two click away did not work",expected,actual);

    }
    // all buttons needs to be pressed for the solution
    public void testAnswerIsAllTiles(){
        ArrayList<Integer> actual = Cheat.getCheat(0b101010101);
        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.add(8);
        expected.add(7);
        expected.add(6);
        expected.add(5);
        expected.add(4);
        expected.add(3);
        expected.add(2);
        expected.add(1);
        expected.add(0);
        tester.assertEquals("Did not click all tiles",expected,actual);
    }


}
