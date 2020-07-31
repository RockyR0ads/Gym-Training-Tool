package com.example.gymtrainingtool;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public  class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.MyViewHolder>{
    private onItemClickListener mOnItemClickListener;
    private List<Exercise> exercisesList;

    public interface onItemClickListener{
        void onImageClick(int position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView title, setsTitle, weightTitle,timeTitle,repsTitle;
        public TextInputEditText sets,time,weight,reps;
        public ImageView deleteImage;
        onItemClickListener oic;


        public MyViewHolder(View view,onItemClickListener oic) {
            super(view);
            title = view.findViewById(R.id.title);
            sets = view.findViewById(R.id.sets);
            time = view.findViewById(R.id.time);
            setsTitle = view.findViewById(R.id.setsTitle);
            weightTitle =view.findViewById(R.id.weightTitle);
            repsTitle =view.findViewById(R.id.repsTitle);
            weight = view.findViewById(R.id.weight);
            timeTitle = view.findViewById(R.id.timeTitle);
            reps = view.findViewById(R.id.reps);
            deleteImage = view.findViewById(R.id.image_delete);
            view.setOnClickListener(this);
            this.oic = oic;
        }


        @Override
        public void onClick(View v) {
            oic.onImageClick(getAdapterPosition());
        }
    }

    public ExerciseAdapter(List<Exercise> exerciseList, onItemClickListener mOnItemClickListener) {
        this.exercisesList = exerciseList;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setExercisesList(List<Exercise> exercisesList) {
        this.exercisesList = exercisesList;
    }

    public List<Exercise> getExercisesList() {
        return exercisesList;
    }


    public void addExercise(Exercise e) {
        exercisesList.add(e);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_list_row, parent, false);

        return new MyViewHolder(itemView,mOnItemClickListener);
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
        holder.repsTitle.setText("Reps");
        holder.reps.setText(String.valueOf(exercise.getReps()));

    }

    @Override
    public int getItemCount() {
        return exercisesList.size();
    }

    }






