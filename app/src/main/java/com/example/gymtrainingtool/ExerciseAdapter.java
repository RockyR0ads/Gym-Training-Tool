package com.example.gymtrainingtool;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public  class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ParentViewHolder>{
    private onItemClickListener mOnItemClickListener;
    private List<Exercise> exercisesList;

    // An object of RecyclerView.RecycledViewPool
    // is created to share the Views
    // between the child and
    // the parent RecyclerViews
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    //private List<Exercise> itemList;



    public interface onItemClickListener{
        void onItemClick(int position);
        void onAddSetClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        mOnItemClickListener = listener;
    }

    public static class ParentViewHolder extends RecyclerView.ViewHolder {

        public TextView title, setsTitle, weightTitle,timeTitle,repsTitle, secondSet;
        public TextInputEditText sets,time,weight,reps;
        public Button addSet;
        public RelativeLayout viewBackground, viewForeground;

        private TextView ParentItemTitle;
        private RecyclerView ChildRecyclerView;


        public ParentViewHolder(View view, final onItemClickListener listener) {
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
            secondSet = view.findViewById(R.id.set2);

            ParentItemTitle = itemView.findViewById(R.id.parent_item_title);
            ChildRecyclerView = itemView.findViewById(R.id.recycler_view);


//            viewForeground.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(listener!=null){
//                        int position = getAdapterPosition();
//                        if(position!= RecyclerView.NO_POSITION){
//                            listener.onItemClick(position);
//                        }
//                    }
//                }
//            });
//
//            addSet.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(listener!=null){
//                        int position = getAdapterPosition();
//                        if(position!= RecyclerView.NO_POSITION){
//                            listener.onAddSetClick(position);
//
//                        }
//                    }
//                }
//            });

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
    public ParentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        View view = null;
//
//        if(viewType == SET_1){
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_list_row, parent, false);
//
//        }else{
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_list_row_add_set, parent, false);
//        }
//        return new MyViewHolder(view,mOnItemClickListener);

        // Here we inflate the corresponding
        // layout of the parent item
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.parent_item,
                        parent, false);

        return new ParentViewHolder(view,mOnItemClickListener);

    }

    @Override
    public void onBindViewHolder(@NonNull ParentViewHolder parentViewHolder, int position) {
        Exercise exercise = exercisesList.get(position);

//        switch(viewHolder.getItemViewType()){
//            case SET_1:
//                ((ParentViewHolder)viewHolder).title.setText(exercise.getTitle());
//                ((ParentViewHolder)viewHolder).setsTitle.setText("Sets");
//                ((ParentViewHolder)viewHolder).repsTitle.setText("Reps");
//                ((ParentViewHolder)viewHolder).weightTitle.setText("Weight");
//                ((ParentViewHolder)viewHolder).timeTitle.setText("Seconds");
//                ((ParentViewHolder)viewHolder).sets.setText("1");
//
//                ((ParentViewHolder)viewHolder).title.setText(exercise.getTitle());
//                ((ParentViewHolder)viewHolder).weight.setText(exercise.getWeight());
//                ((ParentViewHolder)viewHolder).reps.setText(String.valueOf(exercise.getReps()));
//                ((ParentViewHolder)viewHolder).time.setText(exercise.getTime());
//
//                break;
//            case SET_1_TO_2:
//                ((ParentViewHolder)viewHolder).sets.setText("1");
//                ((ParentViewHolder)viewHolder).secondSet.setText("2");
//                ((ParentViewHolder)viewHolder).setsTitle.setText("Sets");
//                ((ParentViewHolder)viewHolder).repsTitle.setText("Reps");
//                ((ParentViewHolder)viewHolder).weightTitle.setText("Weight");
//                ((ParentViewHolder)viewHolder).timeTitle.setText("Seconds");
//
//                ((ParentViewHolder)viewHolder).title.setText(exercise.getTitle());
//                ((ParentViewHolder)viewHolder).weight.setText(exercise.getWeight());
//                ((ParentViewHolder)viewHolder).reps.setText(String.valueOf(exercise.getReps()));
//                ((ParentViewHolder)viewHolder).time.setText(exercise.getTime());
//
//                break;
//            case SET_1_TO_3:
//
//        }


        // Create an instance of the ParentItem
        // class for the given position


        // For the created instance,
        // get the title and set it
        // as the text for the TextView
        parentViewHolder.ParentItemTitle.setText(exercise.getParentItemTitle());

        // Create a layout manager
        // to assign a layout
        // to the RecyclerView.

        // Here we have assigned the layout
        // as LinearLayout with vertical orientation
        LinearLayoutManager layoutManager = new LinearLayoutManager(parentViewHolder.ChildRecyclerView.getContext(), LinearLayoutManager.VERTICAL, false);

        // Since this is a nested layout, so
        // to define how many child items
        // should be prefetched when the
        // child RecyclerView is nested
        // inside the parent RecyclerView,
        // we use the following method
        layoutManager.setInitialPrefetchItemCount(exercise.getChildItemList().size());

        // Create an instance of the child
        // item view adapter and set its
        // adapter, layout manager and RecyclerViewPool
        ExerciseChildAdapter childItemAdapter = new ExerciseChildAdapter(exercise.getChildItemList());
        parentViewHolder.ChildRecyclerView.setLayoutManager(layoutManager);
        parentViewHolder.ChildRecyclerView.setAdapter(childItemAdapter);
        parentViewHolder.ChildRecyclerView.setRecycledViewPool(viewPool);

    }

    private static final int SET_1 = 0;
    private static final int SET_1_TO_2 = 1;
    private static final int SET_1_TO_3 = 2;

    @Override
    public int getItemViewType(int position) {
      Exercise exercise = exercisesList.get(position);
        if(exercise.isAnotherSet()) {
            return SET_1_TO_2;
        } else {
            return SET_1;
        }

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






