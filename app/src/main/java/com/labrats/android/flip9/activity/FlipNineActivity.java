package com.labrats.android.flip9.activity;

import java.util.UUID;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.labrats.android.flip9.fragment.FlipNineFragment;
import com.labrats.android.flip9.R;

public class FlipNineActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		Intent i = getIntent();
		UUID gameId = (UUID) i.getSerializableExtra(FlipNineFragment.EXTRA_GAME_ID);
		return FlipNineFragment.newInstance(gameId);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.overridePendingTransition(R.anim.translate_right_in, R.anim.translate_right_out);
	}
}
