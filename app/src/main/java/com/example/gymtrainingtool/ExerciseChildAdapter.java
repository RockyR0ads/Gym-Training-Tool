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



    //private ExerciseAdapter exercise;
    private Exercise exercise;
    private onItemClickListener mOnItemClickListener;


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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.exercise_list_row, viewGroup, false);
        return new ChildViewHolder(view,mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder childViewHolder, int position)
    {
        ExerciseChild childItem = ChildItemList.get(position);

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
        return ChildItemList.size();
    }

    // This class is to initialize the Views present in the child RecyclerView
   public static class ChildViewHolder extends RecyclerView.ViewHolder {

        public TextInputEditText sets,time,weight,reps;
        public RelativeLayout viewBackground, viewForeground;
        public FrameLayout fL;



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






