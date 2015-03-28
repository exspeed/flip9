package com.labrats.android.flip9;


import android.support.v4.app.Fragment;


public class NightmareModeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {

        return new NightmareModeFragment();
    }


}
