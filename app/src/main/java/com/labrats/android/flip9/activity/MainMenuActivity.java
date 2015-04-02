package com.labrats.android.flip9.activity;

import android.support.v4.app.Fragment;
import com.facebook.appevents.AppEventsLogger;
import com.labrats.android.flip9.fragment.MainMenuFragment;


//activity for main menu
public class MainMenuActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		
		return new MainMenuFragment();
	}

}
