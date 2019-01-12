package com.jeremykerby;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class WorkoutFragment extends Fragment {

    private static final String TAG = "WorkoutFragment";

    AdView adView;
    View rootView;

    int ellipticalMinutesTotal, ellipticalCaloriesTotal;
    int stairsMinutesTotal, stairsCaloriesTotal;
    int bikeMinutesTotal, bikeCaloriesTotal;
    int treadmillMinutesTotal, treadmillCaloriesTotal;
    int otherMinutesTotal, otherCaloriesTotal;
    double ellipticalDistanceTotal, stairsDistanceTotal, bikeDistanceTotal, treadmillDistanceTotal, otherDistanceTotal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(com.jeremykerby.R.layout.fragment_workout, container, false);

        Log.i(TAG, "onCreate()");

        adView = (AdView) rootView.findViewById(com.jeremykerby.R.id.fragmentWorkoutAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        resetValues();

        DatabaseAssistant database = new DatabaseAssistant(getActivity());
        ArrayList<Workout> workouts = database.getAllWorkouts();

        calculateWorkoutTotals(workouts);

        displayWorkoutTotals();

        return rootView;
    }

    private void resetValues() {
        ellipticalMinutesTotal = 0;
        ellipticalCaloriesTotal = 0;
        ellipticalDistanceTotal = 0;

        stairsMinutesTotal = 0;
        stairsCaloriesTotal = 0;
        stairsDistanceTotal = 0;

        bikeMinutesTotal = 0;
        bikeCaloriesTotal = 0;
        bikeDistanceTotal = 0;

        treadmillMinutesTotal = 0;
        treadmillCaloriesTotal = 0;
        treadmillDistanceTotal = 0;

        otherMinutesTotal = 0;
        otherCaloriesTotal = 0;
        otherDistanceTotal = 0;
    }

    private void calculateWorkoutTotals(ArrayList<Workout> workouts) {

        for (Workout w : workouts) {

            switch (w.getType()) {
                case "Elliptical":
                    Log.d("WORKOUT DATA", "Elliptical");
                    ellipticalMinutesTotal += Integer.parseInt(w.getDuration());
                    ellipticalDistanceTotal += Double.parseDouble(w.getDistance());
                    ellipticalCaloriesTotal += Integer.parseInt(w.getCalories());
                    break;
                case "Stair Climber":
                    Log.d("WORKOUT DATA", "Stair Climber");
                    stairsMinutesTotal += Integer.parseInt(w.getDuration());
                    stairsDistanceTotal += Double.parseDouble(w.getDistance());
                    stairsCaloriesTotal += Integer.parseInt(w.getCalories());
                    break;
                case "Stationary Bike":
                    Log.d("WORKOUT DATA", "Stationary Bike");
                    bikeMinutesTotal += Integer.parseInt(w.getDuration());
                    bikeDistanceTotal += Double.parseDouble(w.getDistance());
                    bikeCaloriesTotal += Integer.parseInt(w.getCalories());
                    break;
                case "Treadmill":
                    Log.d("WORKOUT DATA", "Treadmill");
                    treadmillMinutesTotal += Integer.parseInt(w.getDuration());
                    treadmillDistanceTotal += Double.parseDouble(w.getDistance());
                    treadmillCaloriesTotal += Integer.parseInt(w.getCalories());
                    break;
                case "Other":
                    Log.d("WORKOUT DATA", "Other");
                    otherMinutesTotal += Integer.parseInt(w.getDuration());
                    otherDistanceTotal += Double.parseDouble(w.getDistance());
                    otherCaloriesTotal += Integer.parseInt(w.getCalories());
                    break;
            }
        }
    }

    private void displayWorkoutTotals() {
        DecimalFormat precision = new DecimalFormat("0.00");

        TextView ellipticalMinutesLabel, ellipticalDistanceLabel, ellipticalCaloriesLabel;
        TextView stairsMinutesLabel, stairsDistanceLabel, stairsCaloriesLabel;
        TextView bikeMinutesLabel, bikeDistanceLabel, bikeCaloriesLabel;
        TextView treadmillMinutesLabel, treadmillDistanceLabel, treadmillCaloriesLabel;
        TextView otherMinutesLabel, otherDistanceLabel, otherCaloriesLabel;
        TextView totalMinutesLabel, totalDistanceLabel, totalCaloriesLabel;

        ellipticalMinutesLabel = (TextView) rootView.findViewById(com.jeremykerby.R.id.ellipticalMinutesLabel);
        ellipticalMinutesLabel.setText(Integer.toString(ellipticalMinutesTotal));
        ellipticalDistanceLabel = (TextView) rootView.findViewById(com.jeremykerby.R.id.ellipticalDistanceLabel);
        ellipticalDistanceLabel.setText(precision.format(ellipticalDistanceTotal));
        ellipticalCaloriesLabel = (TextView) rootView.findViewById(com.jeremykerby.R.id.ellipticalCaloriesLabel);
        ellipticalCaloriesLabel.setText(Integer.toString(ellipticalCaloriesTotal));

        stairsMinutesLabel = (TextView) rootView.findViewById(com.jeremykerby.R.id.stairsMinutesLabel);
        stairsMinutesLabel.setText(Integer.toString(stairsMinutesTotal));
        stairsDistanceLabel = (TextView) rootView.findViewById(com.jeremykerby.R.id.stairsDistanceLabel);
        stairsDistanceLabel.setText(precision.format(stairsDistanceTotal));
        stairsCaloriesLabel = (TextView) rootView.findViewById(com.jeremykerby.R.id.stairsCaloriesLabel);
        stairsCaloriesLabel.setText(Integer.toString(stairsCaloriesTotal));

        bikeMinutesLabel = (TextView) rootView.findViewById(com.jeremykerby.R.id.bikeMinutesLabel);
        bikeMinutesLabel.setText(Integer.toString(bikeMinutesTotal));
        bikeDistanceLabel = (TextView) rootView.findViewById(com.jeremykerby.R.id.bikeDistanceLabel);
        bikeDistanceLabel.setText(precision.format(bikeDistanceTotal));
        bikeCaloriesLabel = (TextView) rootView.findViewById(com.jeremykerby.R.id.bikeCaloriesLabel);
        bikeCaloriesLabel.setText(Integer.toString(bikeCaloriesTotal));

        treadmillMinutesLabel = (TextView) rootView.findViewById(com.jeremykerby.R.id.treadmillMinutesLabel);
        treadmillMinutesLabel.setText(Integer.toString(treadmillMinutesTotal));
        treadmillDistanceLabel = (TextView) rootView.findViewById(com.jeremykerby.R.id.treadmillDistanceLabel);
        treadmillDistanceLabel.setText(precision.format(treadmillDistanceTotal));
        treadmillCaloriesLabel = (TextView) rootView.findViewById(com.jeremykerby.R.id.treadmillCaloriesLabel);
        treadmillCaloriesLabel.setText(Integer.toString(treadmillCaloriesTotal));

        otherMinutesLabel = (TextView) rootView.findViewById(com.jeremykerby.R.id.otherMinutesLabel);
        otherMinutesLabel.setText(Integer.toString(otherMinutesTotal));
        otherDistanceLabel = (TextView) rootView.findViewById(com.jeremykerby.R.id.otherDistanceLabel);
        otherDistanceLabel.setText(precision.format(otherDistanceTotal));
        otherCaloriesLabel = (TextView) rootView.findViewById(com.jeremykerby.R.id.otherCaloriesLabel);
        otherCaloriesLabel.setText(Integer.toString(otherCaloriesTotal));

        totalMinutesLabel = (TextView) rootView.findViewById(com.jeremykerby.R.id.totalMinutesLabel);
        totalMinutesLabel.setText(Integer.toString(ellipticalMinutesTotal + stairsMinutesTotal + bikeMinutesTotal + treadmillMinutesTotal + otherMinutesTotal));
        totalDistanceLabel = (TextView) rootView.findViewById(com.jeremykerby.R.id.totalDistanceLabel);
        totalDistanceLabel.setText(precision.format(ellipticalDistanceTotal + stairsDistanceTotal + bikeDistanceTotal + treadmillDistanceTotal + otherDistanceTotal));
        totalCaloriesLabel = (TextView) rootView.findViewById(com.jeremykerby.R.id.totalCaloriesLabel);
        totalCaloriesLabel.setText(Integer.toString(ellipticalCaloriesTotal + stairsCaloriesTotal + bikeCaloriesTotal + treadmillCaloriesTotal + otherCaloriesTotal));
    }

    public void updateFragmentData() {
        Log.i(TAG, "updateFragmentData()");

        DatabaseAssistant database = new DatabaseAssistant(getActivity());
        ArrayList<Workout> workouts = database.getAllWorkouts();

        calculateWorkoutTotals(workouts);
        displayWorkoutTotals();
    }

}

