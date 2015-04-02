package com.labrats.android.flip9.activity;

import android.support.v4.app.Fragment;
import com.facebook.appevents.AppEventsLogger;
import com.labrats.android.flip9.fragment.MainMenuFragment;


public class MainMenuActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		
		return new MainMenuFragment();
	}

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }
    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }


}
