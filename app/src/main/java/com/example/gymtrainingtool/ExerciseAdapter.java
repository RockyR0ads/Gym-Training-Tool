package com.example.gymtrainingtool;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.gymtrainingtool.ui.main.GripLogger;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public  class ExerciseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private onItemClickListener mOnItemClickListener;
    private List<Exercise> exercisesList;


    public interface onItemClickListener{
        void onItemClick(int position);
        void onAddSetClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        mOnItemClickListener = listener;
    }

    public static class MyViewHolder1 extends RecyclerView.ViewHolder {

        public TextView title, setsTitle, weightTitle,timeTitle,repsTitle,tester;
        public TextInputEditText sets,time,weight,reps;
        public Button addSet;
        public RelativeLayout viewBackground, viewForeground;

        public MyViewHolder1(View view,final onItemClickListener listener) {
            super(view);
            tester = itemView.findViewById(R.id.sets1);
            title = view.findViewById(R.id.title);
            sets = view.findViewById(R.id.sets);
            time = view.findViewById(R.id.time);
            setsTitle = view.findViewById(R.id.setsTitle);
            weightTitle =view.findViewById(R.id.weightTitle);
            repsTitle =view.findViewById(R.id.repsTitle);
            weight = view.findViewById(R.id.weight);
            timeTitle = view.findViewById(R.id.timeTitle);
            reps = view.findViewById(R.id.reps);
            addSet = view.findViewById(R.id.addSets);
            viewForeground = view.findViewById(R.id.view_foreground);
            viewBackground = view.findViewById(R.id.view_background);

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

            addSet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position!= RecyclerView.NO_POSITION){
                            listener.onAddSetClick(position);

                        }
                    }
                }
            });
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title, setsTitle, weightTitle,timeTitle,repsTitle,sets1;
        public TextInputEditText sets,time,weight,reps;
        public Button addSet;
        public RelativeLayout viewBackground, viewForeground;



        public MyViewHolder(View view, final onItemClickListener listener) {
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
            addSet = view.findViewById(R.id.addSets);
            viewForeground = view.findViewById(R.id.view_foreground);
            viewBackground = view.findViewById(R.id.view_background);
            sets1 = view.findViewById(R.id.sets1);


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

            addSet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position!= RecyclerView.NO_POSITION){
                            listener.onAddSetClick(position);

                        }
                    }
                }
            });

        }


//        @Override
//        public void onClick(View v) {
//            oic.onImageClick(getAdapterPosition());
//        }

    }

    public ExerciseAdapter(List<Exercise> exerciseList) {
        this.exercisesList = exerciseList;

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



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;

//        ViewSwitcher itemView = new ViewSwitcher(parent.getContext());
//        itemView.addView(LayoutInflater.from(parent.getContext()).inflate (R.layout.exercise_list_row, parent, false), 0, null);
//        itemView.addView(LayoutInflater.from (parent.getContext()).inflate (R.layout.exercise_list_row_add_set, parent, false), 0, null);
//        return new MyViewHolder(itemView, mOnItemClickListener);
//
        if(viewType == VIEW_ORDINARY){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_list_row, parent, false);
            return new MyViewHolder(view,mOnItemClickListener);

        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_list_row_add_set, parent, false);
            return new MyViewHolder(view,mOnItemClickListener);
        }


//            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_list_row, parent, false);
//
//
           // return new MyViewHolder(view,mOnItemClickListener);




    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Exercise exercise = exercisesList.get(position);

        if(viewHolder.getItemViewType() == VIEW_WITH_EXTRA_TEXT_VIEW){
            ((MyViewHolder)viewHolder).title.setText(exercise.getTitle());
            ((MyViewHolder)viewHolder).sets1.setText(exercise.getSets());
        }else {
            ((MyViewHolder)viewHolder).title.setText(exercise.getTitle());


//            holder.title.setText(exercise.getTitle());
//            holder.sets.setText(exercise.getSets());
//            holder.time.setText(exercise.getTime());
//            holder.setsTitle.setText("Sets");
//            holder.weightTitle.setText("Weight");
//            holder.weight.setText(exercise.getWeight());
//            holder.timeTitle.setText("Seconds");
//            holder.repsTitle.setText("Reps");
//            holder.reps.setText(String.valueOf(exercise.getReps()));
        }

//        if (viewHolder instanceof MyViewHolder) {
//            ((MyViewHolder)
//                    viewHolder).title.setText(exercise.getTitle());
//
//        }else{
//            ((MyViewHolder)
//                    viewHolder).sets.setText("4");
//        }


//        if (getItemViewType(position) == VIEW_ORDINARY) {
//            ((MyViewHolder)
//                    viewHolder).title.setText(exercise.getTitle());
//        } else {
//            ((MyViewHolder1)
//                    viewHolder).tester.setText("4");
//        }

    }

    private static final int VIEW_ORDINARY = 0;
    private static final int VIEW_WITH_EXTRA_TEXT_VIEW = 1;


    @Override
    public int getItemViewType(int position) {
      Exercise exercise = exercisesList.get(position);
        if(exercise.isAnotherSet()) {
            return VIEW_WITH_EXTRA_TEXT_VIEW;
        } else {
            return VIEW_ORDINARY;
        }

    }



    public void toggleItemViewType (int position) {
//        if (mCurrentType == VIEW_ORDINARY){
//            mCurrentType = VIEW_WITH_EXTRA_TEXT_VIEW;
//        } else {
//            mCurrentType = VIEW_ORDINARY;
//        }
        Exercise exercise = exercisesList.get(position);
        exercise.setAnotherSet(true);
//
//        RecyclerView.ViewHolder vh = (ExerciseAdapter.MyViewHolder) rV.findViewHolderForItemId(position);
//        ((ViewSwitcher) vh.itemView).showNext();
    }


    public void removeItem(int position) {
        exercisesList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Exercise exercise, int position) {
        exercisesList.add(position, exercise);
        // notify item added by position
        notifyItemInserted(position);
    }

    @Override
    public int getItemCount() {
        return exercisesList.size();
    }

    }






