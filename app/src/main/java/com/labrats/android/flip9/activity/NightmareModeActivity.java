package com.labrats.android.flip9.activity;


import android.support.v4.app.Fragment;

import com.labrats.android.flip9.fragment.NightmareModeFragment;


public class NightmareModeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {

        return new NightmareModeFragment();
    }


}
