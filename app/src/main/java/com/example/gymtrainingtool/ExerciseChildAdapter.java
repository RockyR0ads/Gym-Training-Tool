package com.example.gymtrainingtool;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public  class ExerciseChildAdapter extends RecyclerView.Adapter<ExerciseChildAdapter.ChildViewHolder>{
    private List<ExerciseChild> ChildItemList;

    // Constuctor
   public ExerciseChildAdapter(List<ExerciseChild> childItemList)
    {
        this.ChildItemList = childItemList;
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {

        // Here we inflate the corresponding
        // layout of the child item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.exercise_list_row, viewGroup, false);

        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder childViewHolder, int position)
    {

        // Create an instance of the ChildItem
        // class for the given position
        ExerciseChild childItem = ChildItemList.get(position);

        // For the created instance, set title.
        // No need to set the image for
        // the ImageViews because we have
        // provided the source for the images
        // in the layout file itself
        childViewHolder.reps.setText("2");

    }

    @Override
    public int getItemCount()
    {

        // This method returns the number
        // of items we have added
        // in the ChildItemList
        // i.e. the number of instances
        // of the ChildItemList
        // that have been created
        return ChildItemList.size();
    }

    // This class is to initialize
    // the Views present
    // in the child RecyclerView
    static class ChildViewHolder extends RecyclerView.ViewHolder {

       // public TextView setsTitle, weightTitle,timeTitle,repsTitle;
        public TextInputEditText sets,time,weight,reps;
        public RelativeLayout viewBackground, viewForeground;

        ChildViewHolder(View itemView)
        {
            super(itemView);

            reps = itemView.findViewById(R.id.reps);
            sets = itemView.findViewById(R.id.sets);
            time = itemView.findViewById(R.id.time);
            weight = itemView.findViewById(R.id.weight);
//            setsTitle = itemView.findViewById(R.id.setsTitle);
//            weightTitle =itemView.findViewById(R.id.weightTitle);
//            repsTitle =itemView.findViewById(R.id.repsTitle);
//            timeTitle = itemView.findViewById(R.id.timeTitle);
            viewForeground = itemView.findViewById(R.id.view_foreground);
            viewBackground = itemView.findViewById(R.id.view_background);

        }
    }

}






