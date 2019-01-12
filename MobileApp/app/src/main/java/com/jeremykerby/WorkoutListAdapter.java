package com.jeremykerby;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class WorkoutListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Workout> workouts;

    public WorkoutListAdapter(Context context, ArrayList<Workout> workouts) {
        this.context = context;
        this.workouts = workouts;
    }

    public int getCount() {
        return workouts.size();
    }

    public Object getItem(int position) {
        return workouts.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup viewGroup) {
        String type = workouts.get(position).getType();
        String resistance = "Lvl: " + workouts.get(position).getResistance();
        String date = workouts.get(position).getDate();
        String duration = "Duration: " + workouts.get(position).getDuration();
        String distance = "Distance: " + workouts.get(position).getDistance();
        String calories = "Calories: " + workouts.get(position).getCalories();
        String note = workouts.get(position).getNote();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(com.jeremykerby.R.layout.row_workout, null);
        }

        TextView workoutTypeText = (TextView) convertView.findViewById(com.jeremykerby.R.id.workoutType);
        workoutTypeText.setText(type);
        workoutTypeText.setTypeface(null, Typeface.BOLD);
        TextView workoutResistanceText = (TextView) convertView.findViewById(com.jeremykerby.R.id.workoutResistance);
        workoutResistanceText.setText(resistance);
        workoutResistanceText.setTypeface(null, Typeface.BOLD);
        TextView workoutDateText = (TextView) convertView.findViewById(com.jeremykerby.R.id.workoutDate);
        workoutDateText.setText(date);
        TextView workoutDurationText = (TextView) convertView.findViewById(com.jeremykerby.R.id.workoutDuration);
        workoutDurationText.setText(duration);
        TextView workoutDistanceText = (TextView) convertView.findViewById(com.jeremykerby.R.id.workoutDistance);
        workoutDistanceText.setText(distance);
        TextView workoutCaloriesText = (TextView) convertView.findViewById(com.jeremykerby.R.id.workoutCalories);
        workoutCaloriesText.setText(calories);

        TextView workoutNoteText = (TextView) convertView.findViewById(com.jeremykerby.R.id.workoutNote);
        if (note.equals("")) {
            workoutNoteText.setVisibility(TextView.GONE);
        } else {
            workoutNoteText.setVisibility(TextView.VISIBLE);
            workoutNoteText.setText(note);
        }

        return convertView;
    }

}

