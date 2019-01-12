package com.jeremykerby;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "AddFragment";

    CalendarView calendar;
    RadioGroup workoutType;
    EditText resistanceTxt, durationTxt, distanceTxt, calorieTxt, noteTxt;
    Button addButton;
    AdView adView;
    View rootView;

    String dateSelected, type, resistance, duration, distance, calories, note;

    private DatabaseAssistant database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(com.jeremykerby.R.layout.fragment_add, container, false);

        Log.i(TAG, "onCreate()");

        adView = (AdView) rootView.findViewById(com.jeremykerby.R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        calendar = (CalendarView) rootView.findViewById(com.jeremykerby.R.id.calendarView);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int day) {
                dateSelected = Integer.toString(month + 1) + "/" + Integer.toString(day) + "/" + Integer.toString(year);
            }
        });

        workoutType = (RadioGroup) rootView.findViewById(com.jeremykerby.R.id.workoutType);

        resistanceTxt = (EditText) rootView.findViewById(com.jeremykerby.R.id.editTextResistance);
        durationTxt = (EditText) rootView.findViewById(com.jeremykerby.R.id.editTextDuration);
        distanceTxt = (EditText) rootView.findViewById(com.jeremykerby.R.id.editTextDistance);
        calorieTxt = (EditText) rootView.findViewById(com.jeremykerby.R.id.editTextCalories);
        noteTxt = (EditText) rootView.findViewById(com.jeremykerby.R.id.editTextNotes);

        addButton = (Button) rootView.findViewById(com.jeremykerby.R.id.addButton);
        addButton.setOnClickListener(this);

        return rootView;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case com.jeremykerby.R.id.addButton:
                obtainInfo();
                break;
        }
    }

    private void obtainInfo() {
        // get radio button selection
        RadioGroup workoutType = (RadioGroup) rootView.findViewById(com.jeremykerby.R.id.workoutType);
        int selectedId = workoutType.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton radioBtnChosen = (RadioButton) rootView.findViewById(selectedId);
            type = radioBtnChosen.getText().toString();
        }

        // get date if user did not select a date
        if (dateSelected == null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            dateSelected = sdf.format(new Date(calendar.getDate()));
        }

        // get values from text fields
        resistance = resistanceTxt.getText().toString();
        duration = durationTxt.getText().toString();
        distance = distanceTxt.getText().toString();
        calories = calorieTxt.getText().toString();
        note = noteTxt.getText().toString();

        // ADD button menu
        if (type == null || resistance.equals("") || duration.equals("")
                || distance.equals("") || calories.equals("")) {
            Toast.makeText(getActivity(), "Please Complete Form",
                    Toast.LENGTH_SHORT).show();

        } else {
            Workout w = new Workout(dateSelected, type, resistance, duration, distance, calories, note);

            database = new DatabaseAssistant(getActivity());
            database.saveSingleWorkout(w);
            database.getAllWorkouts();
            database.close();

            Toast.makeText(getActivity(), "Workout Added",
                    Toast.LENGTH_SHORT).show();

            MainActivity mActivity = (MainActivity) getActivity();
            mActivity.t0.updateFragmentData();
            mActivity.t2.updateFragmentData();

            clearFields();
        }

    }

    private void clearFields() {
        calendar.setDate(Calendar.getInstance().getTimeInMillis());

        workoutType.clearCheck();
        type = null;

        resistanceTxt.setText("");
        durationTxt.setText("");
        distanceTxt.setText("");
        calorieTxt.setText("");
        noteTxt.setText("");
    }

}

