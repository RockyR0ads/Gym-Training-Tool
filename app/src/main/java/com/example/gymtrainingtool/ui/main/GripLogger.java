package com.example.gymtrainingtool.ui.main;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Movie;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.gymtrainingtool.Exercise;
import com.example.gymtrainingtool.ExerciseAdapter;
import com.example.gymtrainingtool.ItemClickSupport;
import com.example.gymtrainingtool.R;
import com.example.gymtrainingtool.RecyclerTouchListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class GripLogger extends Fragment{

    private List<Exercise> exercisesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ExerciseAdapter mAdapter;
    private EditText exerciseDialog,sets,time,weight,reps;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final RelativeLayout mRlayout = getView().findViewById(R.id.rel);

        recyclerView = getView().findViewById(R.id.recycler_view);
        mAdapter = new ExerciseAdapter(readList(getContext()));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ExerciseAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Exercise exercise = mAdapter.getExercisesList().get(position);
                Toast.makeText(getActivity().getApplicationContext(), exercise.getTitle() + " new implementation is WORKING HOORAY", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteClick(int position) {
                exercisesList = mAdapter.getExercisesList();
                exercisesList.remove(position);
                mAdapter.setExercisesList(exercisesList);
                writeList(getContext(),mAdapter.getExercisesList());
                mAdapter.notifyItemRemoved(position);
            }
        });

       prepareExerciseData();
        Button addSet = view.findViewById(R.id.addSets);
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

}


