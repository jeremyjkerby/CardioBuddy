package com.jeremykerby;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListFragment extends Fragment {

    private static final String TAG = "ListFragment";

    CalendarView calendar;
    private ListView lv;
    WorkoutListAdapter adapter;
    View rootView;

    private String[] ALERT_DIALOG_MENU = {"Edit", "Delete"};
    int POSITION;
    private DatabaseAssistant database;
    private ArrayList<Workout> workouts;

    String dateSelected;
    String type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(com.jeremykerby.R.layout.fragment_list, container, false);

        Log.i(TAG, "onCreate()");

        database = new DatabaseAssistant(getActivity());
        workouts = database.getAllWorkouts();

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        lv = (ListView) rootView.findViewById(com.jeremykerby.R.id.workoutListView);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                POSITION = arg2;
                builder.create();
                builder.show();

                return true;
            }
        });

        // AlertDialog menu
        builder.setTitle("What would you like to do?");
        builder.setItems(ALERT_DIALOG_MENU, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int selection) {
                if (selection == 0) { // if user selects edit
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                    final android.app.AlertDialog alert = builder.create();
                    final View view = inflater.inflate(com.jeremykerby.R.layout.custom_dialog_workout, null);

                    alert.setTitle("Edit Workout");

                    final RadioGroup rg = (RadioGroup) view.findViewById(com.jeremykerby.R.id.workoutType);
                    String rgtype = workouts.get(POSITION).getType();
                    switch (rgtype) {
                        case "Elliptical":
                            ((RadioButton)rg.getChildAt(0)).setChecked(true);
                            break;
                        case "Stair Climber":
                            ((RadioButton)rg.getChildAt(1)).setChecked(true);
                            break;
                        case "Stationary Bike":
                            ((RadioButton)rg.getChildAt(2)).setChecked(true);
                            break;
                        case "Treadmill":
                            ((RadioButton)rg.getChildAt(3)).setChecked(true);
                            break;
                        case "Other":
                            ((RadioButton)rg.getChildAt(4)).setChecked(true);
                            break;
                    }

                    final String w_id = workouts.get(POSITION).getID();
                    final EditText resistanceText = (EditText) view.findViewById(com.jeremykerby.R.id.resistanceText);
                    resistanceText.setText(workouts.get(POSITION).getResistance());
                    final EditText durationText = (EditText) view.findViewById(com.jeremykerby.R.id.durationText);
                    durationText.setText(workouts.get(POSITION).getDuration());
                    final EditText distanceText = (EditText) view.findViewById(com.jeremykerby.R.id.distanceText);
                    distanceText.setText(workouts.get(POSITION).getDistance());
                    final EditText caloriesText = (EditText) view.findViewById(com.jeremykerby.R.id.caloriesText);
                    caloriesText.setText(workouts.get(POSITION).getCalories());
                    final EditText noteText = (EditText) view.findViewById(com.jeremykerby.R.id.noteText);
                    noteText.setText(workouts.get(POSITION).getNote());

                    calendar = (CalendarView) view.findViewById(com.jeremykerby.R.id.calendarView);
                    calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                        @Override
                        public void onSelectedDayChange(CalendarView view, int year, int month,
                                                        int day) {
                            dateSelected = Integer.toString(month+1) + "/" + Integer.toString(day) + "/" + Integer.toString(year);
                        }
                    });

                    Button saveButton = (Button) view.findViewById(com.jeremykerby.R.id.saveButton);
                    saveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String resistance, duration, distance, calories, note;

                            resistance = resistanceText.getText().toString();
                            duration = durationText.getText().toString();
                            distance = distanceText.getText().toString();
                            calories = caloriesText.getText().toString();
                            note = noteText.getText().toString();

                            if (dateSelected == null) {
                                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                                dateSelected = sdf.format(new Date(calendar.getDate()));
                            }

                            // get radio button selection
                            RadioGroup workoutType = (RadioGroup) view.findViewById(com.jeremykerby.R.id.workoutType);
                            int selectedId = workoutType.getCheckedRadioButtonId();
                            if (selectedId != -1) {
                                RadioButton radioBtnChosen = (RadioButton) view.findViewById(selectedId);
                                type = radioBtnChosen.getText().toString();
                            }

                            Log.i(TAG + " DIALOG", dateSelected + " " + type + " " + resistance + " " + duration + " " + distance + " " + calories + " " + note);

                            // save data to user table in DB
                            database = new DatabaseAssistant(getActivity());

                            Workout workout = new Workout(w_id, dateSelected, type, resistance, duration, distance, calories, note);
                            database.updateSingleWorkout(workout);
                            database.close();

                            alert.cancel();

                            Toast.makeText(getActivity(), "Workout Saved",
                                    Toast.LENGTH_SHORT).show();

                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(ListFragment.this).attach(ListFragment.this).commit();
                        }
                    });

                    alert.setCancelable(true);
                    alert.setView(view);
                    alert.show();

                } else if (selection == 1) { // if user selects delete
                    Toast.makeText(getActivity(), "Workout Deleted",
                            Toast.LENGTH_SHORT).show();

                    database.deleteSingleWorkout(workouts.get(POSITION)); // execute SQL command

                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.detach(ListFragment.this).attach(ListFragment.this).commit();
                }


            }
        });

        // binds data from ArrayLists to layout
        adapter = new WorkoutListAdapter(
                getActivity(), workouts);

        adapter.notifyDataSetChanged();

        lv.setAdapter(adapter);

        return rootView;
    }

    public void updateFragmentData() {
        Log.i(TAG, "updateFragmentData()");

        database = new DatabaseAssistant(getActivity());
        workouts.clear();
        workouts = database.getAllWorkouts();

        // binds data from ArrayLists to layout
        adapter = new WorkoutListAdapter(
                getActivity(), workouts);

        adapter.notifyDataSetChanged();

        lv.setAdapter(adapter);
    }

}

