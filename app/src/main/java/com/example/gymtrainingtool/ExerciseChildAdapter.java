package com.example.gymtrainingtool;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public  class ExerciseChildAdapter extends RecyclerView.Adapter<ExerciseChildAdapter.ChildViewHolder>{
    private List<ExerciseChild> ChildItemList;
    private int sets = 1;

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    //private ExerciseAdapter exercise;
    private Exercise exercise;
    private List<Exercise> exercisesList;
    private onItemClickListener mOnItemClickListener;
    private int pPos = 0;

    public interface onItemClickListener{
        void onItemClick(int position);

    }

    public void setOnItemClickListener(onItemClickListener listener){
        mOnItemClickListener = listener;
    }
    // Constuctor
   public ExerciseChildAdapter(List<ExerciseChild> childItemList,Exercise exercise)
    {
        this.ChildItemList = childItemList;
        this.exercise = exercise;
    }



    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {

        // Here we inflate the corresponding
        // layout of the child item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.exercise_list_row, viewGroup, false);

        return new ChildViewHolder(view,mOnItemClickListener);
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
        childViewHolder.sets.setText(String.valueOf(childItem.getSet()));
        childViewHolder.reps.setText(String.valueOf(childItem.getReps()));
        childViewHolder.weight.setText(childItem.getWeight());
        childViewHolder.time.setText(childItem.getTime());


    }


    public void removeItem(int position) {

        exercise.getChildItemList().remove(position);
       // ChildItemList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(ExerciseChild exercise, int position) {
        ChildItemList.add(position, exercise);
        // notify item added by position
        notifyItemInserted(position);
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
   public static class ChildViewHolder extends RecyclerView.ViewHolder {

        public TextInputEditText sets,time,weight,reps;
        public RelativeLayout viewBackground, viewForeground;
        public FrameLayout fL;
        public RecyclerView ChildRecyclerView;


        ChildViewHolder(View itemView,final onItemClickListener listener)
        {
            super(itemView);

            reps = itemView.findViewById(R.id.reps);
            sets = itemView.findViewById(R.id.sets);
            time = itemView.findViewById(R.id.time);
            weight = itemView.findViewById(R.id.weight);
            viewForeground = itemView.findViewById(R.id.view_foreground);
            viewBackground = itemView.findViewById(R.id.view_background);
            fL = itemView.findViewById(R.id.Frame_layout);


            viewForeground.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position!= RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }


    }

}






