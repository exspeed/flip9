package com.labrats.android.flip9.fragment;

import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.labrats.android.flip9.data.FlipData;
import com.labrats.android.flip9.dialog.NightmareDialog;
import com.labrats.android.flip9.R;
import com.labrats.android.flip9.data.UserData;

import java.util.Random;

/**
 * Created by Jackie on 3/28/2015.
 */
public class NightmareModeFragment extends Fragment {
    private final int TOTALPUZZLE = 511;
    private FlipData mFlipData;
    private Button mTileButtons[] = new Button[9];
    private TextView mCurrentScore;
    private ImageButton mInfoButton;
    private Button mConfirmButton;
    private MediaPlayer mSoundEffect;
    private String mCurrentString;
    private int mScore = 0;
    private Animation mVerticalIn;
    private Animation mVerticalOut;
    private Random rand;
    private int answer;
    private static final int REQUEST_COMPLETION = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.activity_nightmare_mode, container,
                false);

        TableLayout tableLayout = (TableLayout) v
                .findViewById(R.id.tablelayout_buttons);
        // if the layout were to change, this will crash
        int index = 0;
        for (int i = 0; i < 3; i++) {
            TableRow tableRow = (TableRow) tableLayout.getChildAt(i);
            for (int j = 0; j < tableRow.getChildCount(); j++) {
                mTileButtons[index] = (Button) tableRow.getChildAt(j);
                mTileButtons[index]
                        .setOnTouchListener(new TileListener2(index));

                index++;
            }

        }
        rand = new Random();
        mFlipData = new FlipData(0);
        initialize();
        initializeAnimation();

        mCurrentScore = (TextView) v.findViewById(R.id.bestView);
        mCurrentString = mCurrentScore.getText().toString() + "\n";
        mCurrentScore.setText(mCurrentString + mScore);

        mInfoButton = (ImageButton) v.findViewById(R.id.infoButton);
        mInfoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                PopupWindow infoPopUp = new PopupWindow(getActivity());
                TextView textW = new TextView(getActivity());
                ViewGroup.LayoutParams layout = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                textW.setLayoutParams(layout);
                textW.setTextColor(-1);
                textW.setTextSize(16);
                textW.setText("You can flip one tile. Get to the final state when the red * are pressed");
                infoPopUp.setContentView(textW);
                infoPopUp.setWidth(800);
                infoPopUp.setHeight(300);
                infoPopUp.showAtLocation(mInfoButton,
                        Gravity.CENTER_HORIZONTAL, 25, 25);
                // To close, Tap outside the box
                infoPopUp.setFocusable(true);
                infoPopUp.update();
            }
        });



        mConfirmButton = (Button) v.findViewById(R.id.Confirm);
        mConfirmButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                comfirm();
            }
        });

        return v;

    }

    /*
     * Used William J. Francis scale animation tutorial as reference for code
     * below http://www.techrepublic.com/blog/software-engineer/
     * use-androids-scale-animation-to-simulate-a-3d-flip/
     */
    private void initializeAnimation() {
        mVerticalOut = AnimationUtils.loadAnimation(getActivity(),
                R.anim.flip_vertical_out);
        mVerticalIn = AnimationUtils.loadAnimation(getActivity(),
                R.anim.flip_vertical_in);

        mVerticalIn.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                updateChange();
                startAnimation(touchedTile, mVerticalOut);
            }
        });
    }

    //Very dependant on this guy
    int touchedTile = 0;

    private void startAnimation(int index, Animation anim) {
        mTileButtons[index].startAnimation(anim);
        touchedTile = index;

    }

    private void comfirm(){

        // ???
        for (Button tile : mTileButtons) {
            if (tile.isEnabled() == false)
                tile.setEnabled(true);
            tile.setText("");
        }
        if (mFlipData.getCurrentState() == answer){
            mScore++;
            mCurrentScore.setText(mCurrentString + mScore);
            initialize();
           }else{
            FragmentManager fm = getActivity().getSupportFragmentManager();
            NightmareDialog dialog =  NightmareDialog.newInstance(mScore);
            dialog.setTargetFragment(this, REQUEST_COMPLETION);
            dialog.show(fm,"yes");
        }
       updateChange();
    }


    private void initialize() {
        // find FlipData that corresponds to the puzzle
        int puzzle = rand.nextInt(TOTALPUZZLE);

        mFlipData.setStart(puzzle);
        answer = puzzle;

        int redStar = rand.nextInt(TOTALPUZZLE -1) + 1;
        for (int i = 0; i < mTileButtons.length; i++){
            int bitPosition = 1;
            bitPosition <<= i;
            if ((redStar & bitPosition) != 0){
                mTileButtons[i].setText("*");
                answer = answer ^ mFlipData.getBitmask(i);
            }
        }




        // in case the user goes back to the same level
        // and currentState was saved, restart() prevents cheating
        mFlipData.restart();
        updateChange();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mSoundEffect != null) {
            mSoundEffect.release();
            mSoundEffect = null;
        }
    }

    private void updateChange() {

        int temp = mFlipData.getCurrentState();
        for (int i = 0; i < 9; i++) {
            // reset highlighted buttons
            mTileButtons[i].setPressed(false);
            if ((temp & 1) == 1)
                mTileButtons[i]
                        .setBackgroundResource(R.drawable.tile_start_state);
            else {
                mTileButtons[i]
                        .setBackgroundResource(R.drawable.tile_end_state_normal);
                GradientDrawable background = (GradientDrawable) mTileButtons[i]
                        .getBackground();
                background.mutate();
                background.setColor(UserData.get(getActivity()).getColor());

            }
            temp >>= 1;
        }
    }

    private void checkCompleted() {
        if (mFlipData.getCurrentState() == 0) {
            mScore += 1;
            mCurrentScore.setText(mCurrentString + mScore);
            int puzzle = rand.nextInt(TOTALPUZZLE);
            mFlipData = new FlipData(puzzle);
            updateChange();
        }
    }



    private class TileListener2 implements View.OnTouchListener {

        private final int POSITION;
        private Rect rect;
        private boolean cancel = false;

        public TileListener2(int index) {
            this.POSITION = index;

        }

        /*
         * Learn how to detect cancel action
         * http://stackoverflow.com/questions/6410200/
         * android-how-to-detect-if-use-touches-and-drags-out-of-button-region
         */
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction() & MotionEvent.ACTION_MASK) {

                case MotionEvent.ACTION_DOWN:
                    //highlightTile();
                    rect = new Rect(mTileButtons[POSITION].getLeft(),
                            mTileButtons[POSITION].getTop(),
                            mTileButtons[POSITION].getRight(),
                            mTileButtons[POSITION].getBottom());
                    break;

                case MotionEvent.ACTION_MOVE:
                    if (!rect.contains(v.getLeft() + (int) event.getX(), v.getTop()
                            + (int) event.getY())) {
                        updateChange();
                        cancel = true;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (!cancel) {
                        playSound();
                        mFlipData.flipOneTile(POSITION);
                        startAnimation(POSITION, mVerticalIn);

                        checkCompleted();

                    }
                    cancel = false;
                    break;

                default:

            }
            return true;
        }

        private void highlightTile() {
            int mask = FlipData.getBitmask(POSITION);
            for (int i = 0; i < 9; i++) {
                if ((mask & 1) == 1)
                   // mTileButtons[i].setPressed(true);
                mask >>= 1;
            }
        }

        private void playSound() {
            if (mSoundEffect == null) {
                mSoundEffect = MediaPlayer.create(getActivity(), R.raw.mouse1);
            }
            mSoundEffect.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
					/*
					 * if(mp == mSoundEffect){ mSoundEffect.start(); }
					 */

                    if (mSoundEffect != null) {
                        mSoundEffect.reset();
                        mSoundEffect.release();

                        mSoundEffect = null;
                    }

                }
            });
            mSoundEffect.start();
        }

    }
}
