package com.labrats.android.flip9;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.AbsListView.SelectionBoundsAdjuster;

import java.util.Random;

/**
 * Created by Jackie on 3/28/2015.
 */
public class NightmareDialog extends DialogFragment {
    //Initialize variables
    public static final String EXTRA_NEXT = "next";
    public static final String EXTRA_SCORE ="Score";

    private static final String[] DEMOTIVATION = { "You Lost", "WRONG",
             "Nice Try", "You Suck", "Incorrect" };
    private TextView mMotivationTextView;

    //Complete dialog when completing a puzzle
    public static NightmareDialog newInstance(int score) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_SCORE, score);

        NightmareDialog fragment = new NightmareDialog();
        fragment.setArguments(args);
        return fragment;
    }

    //Set ups what the dioalong box will have when it pops up
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //Generate random motivation message
        Random rand = new Random();
        int index = rand.nextInt(DEMOTIVATION.length);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(DEMOTIVATION[index]);
        //Listener for "Retry" button

        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(getActivity(), NightmareModeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        //Listener for "Menu" button
        builder.setNegativeButton("Menu", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(getActivity(), MainMenuActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        return builder.create();
    }

}
