package com.labrats.android.flip9.activity;

        import android.support.v4.app.Fragment;

        import com.labrats.android.flip9.fragment.TimeTrialFragment;

public class TimeTrialActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new TimeTrialFragment();
    }


}
