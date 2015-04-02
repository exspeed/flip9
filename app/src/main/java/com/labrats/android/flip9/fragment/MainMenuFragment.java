package com.labrats.android.flip9.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
	
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.labrats.android.flip9.dialog.ColorSelectDialog;
import com.labrats.android.flip9.R;
import com.labrats.android.flip9.activity.NightmareModeActivity;
import com.labrats.android.flip9.activity.PuzzleListActivity;
import com.labrats.android.flip9.activity.TimeTrialActivity;

public class MainMenuFragment extends Fragment {
	//Initializing variables
	private Button mClassicButton;
	private Button mTimeTrialButton;
	private Button mNightmareButton;
	private Button mSettingsButton;
    private ShareButton mShareButton;
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());

		View v = inflater
				.inflate(R.layout.activity_main_menu, container, false);
		//Listener for Classic mode
		mClassicButton = (Button)v.findViewById(R.id.Classic);
		mClassicButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), PuzzleListActivity.class);
				startActivity(i);
			}
		});
		//Listener for time trial mode
		mTimeTrialButton = (Button)v.findViewById(R.id.Time_Trial);
		mTimeTrialButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), TimeTrialActivity.class);
				startActivity(i);
				
			}
		});
		//Listener for nightmare mode
		mNightmareButton = (Button) v.findViewById(R.id.Nightmare);
		mNightmareButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(), NightmareModeActivity.class);
				startActivity(i);
			}
		});
		//Listener for settings 
	    mSettingsButton = (Button) v.findViewById(R.id.Settings);
		mSettingsButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e("Hi", "Settings Menu");
				FragmentManager fm = getActivity().getSupportFragmentManager();
				ColorSelectDialog dialog = new ColorSelectDialog();
				dialog.show(fm, "Choose Color");
			}
		});

        mShareButton = (ShareButton) v.findViewById(R.id.fb_share_button);
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://developers.facebook.com"))
                .build();
        mShareButton.setShareContent(content);

        mShareButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

		return v;

	}

}
