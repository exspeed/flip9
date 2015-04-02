package com.labrats.android.flip9.dialog;

import java.util.Random;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.labrats.android.flip9.R;

public class CompleteDialog extends DialogFragment {
	//Initialize variables
	public static final String EXTRA_NEXT = "next";
	public static final String EXTRA_SCORE ="stars";


	private TextView mMotivationTextView;
	private RatingBar mRatingBar;

	//Complete dialog when completing a puzzle 
	public static CompleteDialog newInstance(int numOfStars) {
		Bundle args = new Bundle();
		args.putInt(EXTRA_SCORE, numOfStars);
		
		CompleteDialog fragment = new CompleteDialog();
		fragment.setArguments(args);
		return fragment;
	}

	//Set ups what the dioalong box will have when it pops up
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		View v = getActivity().getLayoutInflater().inflate(
				R.layout.complete_fragment, null);
		mMotivationTextView = (TextView) v.findViewById(R.id.congratsTextView);
		//Generate random motivation message

        Random rand = new Random();
        Resources res = getResources();
        String [] MOTIVATION = res.getStringArray(R.array.motivation);
        int index = rand.nextInt(MOTIVATION.length);
        mMotivationTextView.setText(MOTIVATION[index]);

		mRatingBar = (RatingBar) v.findViewById(R.id.congratsRatingBar);
		int score = getArguments().getInt(EXTRA_SCORE);
		mRatingBar.setRating(getStars(score));

        String next = res.getString(R.string.next);
        String retry = res.getString(R.string.retry);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		builder.setView(v);
		//Listener for "Next" button
		builder.setPositiveButton(next, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent i = new Intent();
				i.putExtra(EXTRA_NEXT, true);

				getTargetFragment().onActivityResult(getTargetRequestCode(),
						Activity.RESULT_OK, i);
        }
		});
		//Listener for "Retry" button
		builder.setNegativeButton(retry, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent i = new Intent();
				i.putExtra(EXTRA_NEXT, false);
				getTargetFragment().onActivityResult(getTargetRequestCode(),
						Activity.RESULT_OK, i);
			}
		});

		return builder.create();
	}
	
	//Determines how many stars the user got on the puzzle
	public int getStars(int score){
		if(score <= 10)
			return 3;
		else if (score <= 20)
			return 2;
		else
			return 1;
	}
}
