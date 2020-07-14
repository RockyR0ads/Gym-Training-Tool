package com.example.gymtrainingtool;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.MyViewHolder>{

    private List<Exercise> exercisesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, time, sets, setsTitle, weightTitle,weight,timeTitle;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            sets = (TextView) view.findViewById(R.id.sets);
            time = (TextView) view.findViewById(R.id.time);
            setsTitle = (TextView) view.findViewById(R.id.setsTitle);
            weightTitle = (TextView) view.findViewById(R.id.weightTitle);
            weight = (TextView) view.findViewById(R.id.weight);
            timeTitle = (TextView) view.findViewById(R.id.timeTitle);
        }
    }

    public ExerciseAdapter(List<Exercise> exerciseList) {
        this.exercisesList = exerciseList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Exercise exercise = exercisesList.get(position);

        holder.title.setText(exercise.getTitle());
        holder.sets.setText(exercise.getSets());
        holder.time.setText(exercise.getTime());
        holder.setsTitle.setText("Sets");
        holder.weightTitle.setText("Weight");
        holder.weight.setText(exercise.getWeight());
        holder.timeTitle.setText("Seconds");
    }

    @Override
    public int getItemCount() {
        return exercisesList.size();
    }

}
