package com.example.gymtrainingtool.ui.main;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Movie;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gymtrainingtool.Exercise;
import com.example.gymtrainingtool.ExerciseAdapter;
import com.example.gymtrainingtool.R;
import com.example.gymtrainingtool.RecyclerTouchListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GripLogger extends Fragment {

    private List<Exercise> exercisesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ExerciseAdapter mAdapter;
    private EditText exerciseDialog,sets,time,weight;

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

//        exerciseDialog = .findViewById(R.id.exercise);
//        sets = getView().findViewById(R.id.sets);
//        time = getView().findViewById(R.id.time);
//        weight = getView().findViewById(R.id.weight);

        recyclerView = getView().findViewById(R.id.recycler_view);
        mAdapter = new ExerciseAdapter(exercisesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        prepareExerciseData();

        FloatingActionButton fab = getView().findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Creating New exercise", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                buildDialog();
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Exercise exercise = exercisesList.get(position);
                Toast.makeText(getActivity().getApplicationContext(), exercise.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void prepareExerciseData() {
        Exercise exercise = new Exercise("Single Hand Barbell Hold", "3","60kg", "15");
        exercisesList.add(exercise);

        exercise = new Exercise("Double Overhand Barbell Hold", "3", "100kg","20");
        exercisesList.add(exercise);


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

        builder.setView(mView);
        builder.setMessage("Log new exercise");
        builder.setIcon(android.R.drawable.ic_dialog_alert)



                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        // do what when you click add
                        Exercise exercise = new Exercise(exerciseDialog.getText().toString(),sets.getText().toString() ,weight.getText().toString(),time.getText().toString());
                        String test = exerciseDialog.getText().toString();
                        exercisesList.add(exercise);
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

}


