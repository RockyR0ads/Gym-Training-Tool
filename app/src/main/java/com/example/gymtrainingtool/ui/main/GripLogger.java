package com.example.gymtrainingtool.ui.main;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.TableRow.LayoutParams;

import com.example.gymtrainingtool.Exercise;
import com.example.gymtrainingtool.ExerciseAdapter;
import com.example.gymtrainingtool.R;
import com.example.gymtrainingtool.RecyclerItemTouchHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GripLogger extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    //private List<Exercise> exercisesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ExerciseAdapter mAdapter;
    private EditText exerciseDialog,sets,time,weight,reps;
    private FrameLayout frameLayout;
    public TextInputEditText test;
    private RelativeLayout viewForeground;

    public GripLogger() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_grip_logger, container, false);

    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // final FrameLayout mRlayout = getView().findViewById(R.id.Frame_layout);


//        EditText myEditText = new EditText(getContext());
//        myEditText.setLayoutParams(new Gallery.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

        

        recyclerView = getView().findViewById(R.id.recycler_view);
        frameLayout = getView().findViewById(R.id.Frame_layout);
        mAdapter = new ExerciseAdapter(readList(getContext()));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        viewForeground = getView().findViewById(R.id.view_foreground);




        mAdapter.setOnItemClickListener(new ExerciseAdapter.onItemClickListener() {
            @Override
            //on TAP of every element
            public void onItemClick(int position) {
                Exercise exercise = mAdapter.getExercisesList().get(position);
                Toast.makeText(getActivity().getApplicationContext(), exercise.getTitle() + " is selected", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onAddSetClick(int position) {


                TableLayout detailsTable = getView().findViewById(R.id.table);
                TableRow tableRow = (TableRow) getLayoutInflater().inflate(R.layout.tablerow, null);


                Exercise exercise = mAdapter.getExercisesList().get(position);
                //Add row to the table
                //exercise.setTitle();
               // mAdapter.addView(tableRow);
                detailsTable.addView(tableRow);
               // mAdapter.notifyDataSetChanged();
               // FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 300);
                //frameLayout.setLayoutParams(lp);
            }
        });

      // prepareExerciseData();

        FloatingActionButton fab = getView().findViewById(R.id.fab);



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Creating New exercise", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                buildDialog();
            }
        });

//        ItemClickSupport.addTo(recyclerView)
//                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
//                    @Override
//                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                        // do it
//                        Toast.makeText(getActivity().getApplicationContext(), "my anus hurts", Toast.LENGTH_SHORT).show();
//                    }
//                });

//        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                Exercise exercise = mAdapter.getExercisesList().get(position);
//                Toast.makeText(getActivity().getApplicationContext(), exercise.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
//
//            }

//            @Override
//            public void onLongClick(View view, int position) {
//                exercisesList = mAdapter.getExercisesList();
//                exercisesList.remove(position);
//                mAdapter.setExercisesList(exercisesList);
//                writeList(getContext(),mAdapter.getExercisesList());
//                mAdapter.notifyDataSetChanged();
//
//            }
//        }));

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT,this );
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);



    }

    private void prepareExerciseData() {
        Exercise exercise = new Exercise("Single Hand Barbell Hold", "3","60kg", "15",1);
        mAdapter.addExercise(exercise);

        exercise = new Exercise("Double Overhand Barbell Hold", "3", "100kg","20",1);
        mAdapter.addExercise(exercise);
        writeList(getContext(),mAdapter.getExercisesList());
        mAdapter.notifyDataSetChanged();
    }

    private void buildDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View mView = inflater.inflate(R.layout.dialog_new_exercise, null);

        exerciseDialog = mView.findViewById(R.id.exercise);
        sets = mView.findViewById(R.id.sets);
        time = mView.findViewById(R.id.time);
        weight = mView.findViewById(R.id.weight);
        reps = mView.findViewById(R.id.reps);


        builder.setView(mView);
        builder.setMessage("Log new exercise");
        builder.setIcon(android.R.drawable.ic_dialog_alert)

                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // do what when you click add
                        Exercise exercise = new Exercise(exerciseDialog.getText().toString(),sets.getText().toString() ,weight.getText().toString(),time.getText().toString(),Integer.parseInt(reps.getText().toString()));
                        mAdapter.addExercise(exercise);
                        writeList(getContext(),mAdapter.getExercisesList());
                        mAdapter.notifyDataSetChanged();

                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.cancel();

                    }
                });

        // Create the AlertDialog object and return it
        AlertDialog builder1 = builder.create();
        builder1.show();

    }

    public static List<Exercise> readList(Context c){//
        try{
            FileInputStream fis = c.openFileInput("NAME");
            ObjectInputStream is = new ObjectInputStream(fis);
            List<Exercise> list = (List<Exercise>)is.readObject();
            is.close();
            return list;
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static List<Exercise> removeExercise(Context c,int position){//
        try{
            FileInputStream fis = c.openFileInput("NAME");
            ObjectInputStream is = new ObjectInputStream(fis);
            List<Exercise> list = (List<Exercise>)is.readObject();
            list.remove(position);
            is.close();
            return list;
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static void writeList(Context c, List<Exercise> list){

        try{

            FileOutputStream fos = c.openFileOutput("NAME", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);

            os.writeObject(list);
            os.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        if (viewHolder instanceof ExerciseAdapter.MyViewHolder) {

            Exercise exercise = mAdapter.getExercisesList().get(position);


            // get the removed item name to display it in snack bar
           // String name = exercisesList.get(viewHolder.getAdapterPosition()).getTitle();
            String title = exercise.getTitle();
            // backup of removed item for undo purpose
          //  final Exercise deletedItem = exercisesList.get(viewHolder.getAdapterPosition());
            final Exercise deletedItem = exercise;
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            mAdapter.removeItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(frameLayout, title + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  boolean  deleteItem = false;
                    // undo is selected, restore the deleted item
                    mAdapter.restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();

           // exercisesList = mAdapter.getExercisesList();
           // exercisesList.remove(position);
           // mAdapter.setExercisesList(exercisesList);
            writeList(getContext(),mAdapter.getExercisesList());
            mAdapter.notifyItemRemoved(position);

        }
    }
}


