package com.labrats.android.flip9.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.labrats.android.flip9.R;
import com.labrats.android.flip9.data.UserData;
/*
    purpose: Display the color options the user would like.
 */
public class ColorSelectDialog extends DialogFragment {
	//Initialize variables
	ImageButton[] colorButtons = new ImageButton[8];

	//What the ColorSelectDialog will have when it pops up
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View v = getActivity().getLayoutInflater().inflate(
				R.layout.color_pick_dialog, null);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		UserData data = UserData.get(getActivity());
		initializeColorButton(v);

		colorButtons[data.getColorIndex()].setImageResource(R.drawable.check);

        Resources res = getResources();
		builder.setTitle(res.getString(R.string.color));
		builder.setView(v);

		builder.setPositiveButton(R.string.ok, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
					UserData.get(getActivity()).saveData();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		return builder.create();
	}

    // set the color dynamically to the button
	private void initializeColorButton(View v) {
		LinearLayout rowOfButtons = (LinearLayout) v
				.findViewById(R.id.color_row1);
		int index = 0;
		for (int i = 0; i < rowOfButtons.getChildCount(); i++) {
			colorButtons[index] = (ImageButton) rowOfButtons.getChildAt(i);
			index++;
		}
		rowOfButtons = (LinearLayout) v.findViewById(R.id.color_row2);
		for (int i = 0; i < rowOfButtons.getChildCount(); i++) {
			colorButtons[index] = (ImageButton) rowOfButtons.getChildAt(i);
			index++;
		}
		TypedArray colors = getActivity().getApplicationContext()
				.getResources().obtainTypedArray(R.array.tilecolors);
		int count = 0;

		for (int i = 0; i < colorButtons.length; i++) {
			colorButtons[i].setOnClickListener(new AddCheckListener(i));
			GradientDrawable background = (GradientDrawable) colorButtons[i]
					.getBackground();
			background.mutate();
			background.setColor(colors.getColor(count, 0));
			count++;

		}
		colors.recycle();
	}

    // show user's current selection
	private class AddCheckListener implements View.OnClickListener {

		int position;

		public AddCheckListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {

			colorButtons[UserData.get(getActivity()).getColorIndex()]
					.setImageDrawable(null);

			((ImageButton) v).setImageResource(R.drawable.check);
			UserData.get(getActivity()).setColorPreference(position);
		}

	}
}
