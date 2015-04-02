package com.labrats.android.flip9.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import com.labrats.android.flip9.R;
import com.labrats.android.flip9.activity.MainMenuActivity;

/**
 * Created by Andrew on 2015-04-01.
 * incomplete not being used
 */
public class ClassicTest extends ActivityInstrumentationTestCase2<MainMenuActivity>{

    private MainMenuActivity activity;
    private Button mClassicButton;

    public ClassicTest (){
        super(MainMenuActivity.class);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();
        activity  = getActivity();
        mClassicButton = (Button) activity.findViewById(R.id.Classic);
    }
}
