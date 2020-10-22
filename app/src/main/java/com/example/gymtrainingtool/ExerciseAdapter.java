package com.example.gymtrainingtool;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static java.security.AccessController.getContext;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ParentViewHolder> implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    private onItemClickListener mOnItemClickListener;
    private List<Exercise> exercisesList;


    // An object of RecyclerView.RecycledViewPoo is created to share the Views between the child and the parent RecyclerViews
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        //stub

    }

    public interface onItemClickListener {
        void onItemClick(int position);

        void onAddSetClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public static class ParentViewHolder extends RecyclerView.ViewHolder {

        public TextView title, setsTitle, weightTitle, timeTitle, repsTitle, RPEtitle,date;
        public TextInputEditText sets, time, weight, reps;
        public Button addSet;
        public RelativeLayout viewBackground,viewForeground;
        public FrameLayout parentCard;


        private TextView ParentItemTitle;
        private RecyclerView ChildRecyclerView;


        public ParentViewHolder(View view, final onItemClickListener listener) {
            super(view);
            title = view.findViewById(R.id.title);
            sets = view.findViewById(R.id.sets);
            time = view.findViewById(R.id.time);
            setsTitle = view.findViewById(R.id.setsTitle);
            weightTitle = view.findViewById(R.id.weightTitle);
            repsTitle = view.findViewById(R.id.repsTitle);
            weight = view.findViewById(R.id.weight);
            timeTitle = view.findViewById(R.id.timeTitle);
            reps = view.findViewById(R.id.reps);
            addSet = view.findViewById(R.id.addSets);
            RPEtitle = view.findViewById(R.id.RPETitle);
            viewForeground = view.findViewById(R.id.view_foreground);
            viewBackground = view.findViewById(R.id.view_background);
            date = view.findViewById(R.id.date);
            ParentItemTitle = itemView.findViewById(R.id.parent_item_title);
            ChildRecyclerView = itemView.findViewById(R.id.recycler_view);
            parentCard = itemView.findViewById(R.id.linear_layout);


            parentCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            addSet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onAddSetClick(position);

                        }
                    }
                }
            });

        }

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

        // Here we inflate the corresponding layout of the parent item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parent_item, parent, false);

        return new ParentViewHolder(view, mOnItemClickListener);

    }

    @Override
    public void onBindViewHolder(@NonNull final ParentViewHolder parentViewHolder, final int position) {

        // Create an instance of the ParentItem
        final Exercise exercise = exercisesList.get(position);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        parentViewHolder.ParentItemTitle.setText(exercise.getTitle());
        parentViewHolder.setsTitle.setText("Sets");
        parentViewHolder.weightTitle.setText("Weight");
        parentViewHolder.repsTitle.setText("Reps");
        parentViewHolder.timeTitle.setText("Time");
        parentViewHolder.RPEtitle.setText("RPE");
        parentViewHolder.date.setText(dateFormat.format(date));

        // Create a layout manager to assign a layout to the RecyclerView. Here we have assigned the layout as LinearLayout with vertical orientation
        LinearLayoutManager layoutManager = new LinearLayoutManager(parentViewHolder.ChildRecyclerView.getContext(), LinearLayoutManager.VERTICAL, false);

        // Since this is a nested layout, so to define how many child items should be prefetched when the child RecyclerView is nested inside the parent RecyclerView, we use the following method
        layoutManager.setInitialPrefetchItemCount(exercise.getChildItemList().size());

        // Create an instance of the child item view adapter and set its adapter, layout manager and RecyclerViewPool
        final ExerciseChildAdapter childItemAdapter = new ExerciseChildAdapter(exercise.getChildItemList(), exercise);

        parentViewHolder.ChildRecyclerView.setLayoutManager(layoutManager);
        parentViewHolder.ChildRecyclerView.setAdapter(childItemAdapter);
        parentViewHolder.ChildRecyclerView.setRecycledViewPool(viewPool);

        childItemAdapter.setOnItemClickListener(new ExerciseChildAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Toast.makeText(parentViewHolder.ChildRecyclerView.getContext().getApplicationContext(), "Set " + exercise.getChildItemList().get(position).getSet() + " is selected", Toast.LENGTH_SHORT).show();

            }
        });

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                if (viewHolder instanceof ExerciseChildAdapter.ChildViewHolder) {

                    // backup of removed item for undo purposes
                    final ExerciseChild set = exercise.getChildItemList().get(viewHolder.getAdapterPosition());
                    final int deletedIndex = viewHolder.getAdapterPosition();
                    int removedSetNumber = exercise.getChildItemList().get(viewHolder.getAdapterPosition()).getSet();


                    childItemAdapter.removeItem(viewHolder.getAdapterPosition());

                    Snackbar snackbar = Snackbar
                            .make(((ExerciseChildAdapter.ChildViewHolder) viewHolder).fL, "Removed Set " + removedSetNumber, Snackbar.LENGTH_LONG);
                    snackbar.setDuration(3000);

                    snackbar.setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            boolean deleteItem = false;
                            // undo is selected, restore the deleted item
                            childItemAdapter.restoreItem(set, deletedIndex);
                        }
                    });
                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();
                    // childItemAdapter.notifyItemRemoved(position);
                }
            }
        };
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(parentViewHolder.ChildRecyclerView);


    }

    private static final int SET_1 = 0;
    private static final int SET_1_TO_2 = 1;
    private static final int SET_1_TO_3 = 2;

    @Override
    public int getItemViewType(int position) {
        Exercise exercise = exercisesList.get(position);
        if (exercise.isAnotherSet()) {
            return SET_1_TO_2;
        } else {
            return SET_1;
        }

    }


    @Override
    public int getItemCount() {
        return exercisesList.size();
    }

}






