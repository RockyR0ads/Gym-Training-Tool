package com.example.gymtrainingtool.ui.main;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.gymtrainingtool.ExerciseChild;
import com.example.gymtrainingtool.ExerciseChildAdapter;
import com.example.gymtrainingtool.R;
import com.example.gymtrainingtool.RecyclerItemTouchHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GripLogger extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    //private List<Exercise> exercisesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ExerciseAdapter mAdapter;
    private EditText exerciseDialog,setsTextField,time,weight,reps;


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


        recyclerView = getView().findViewById(R.id.parent_recyclerview);
        mAdapter = new ExerciseAdapter(ParentItemList());


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(new ExerciseAdapter.onItemClickListener() {
            @Override
            //on TAP of every PARENT element
            public void onItemClick(int position) {
                Exercise exercise = mAdapter.getExercisesList().get(position);
                Toast.makeText(getActivity().getApplicationContext(), exercise.getTitle() + " is selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAddSetClick(int position) {
                Exercise exercise =  mAdapter.getExercisesList().get(position);
                int sets = exercise.getChildItemList().size();
                Toast.makeText(getActivity().getApplicationContext(), "added another set!", Toast.LENGTH_SHORT).show();
                exercise.getChildItemList().add(new ExerciseChild(++sets));
                mAdapter.notifyDataSetChanged();
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


//        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT,this );
//        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

    }



    private List<Exercise> ParentItemList()
    {
        List<Exercise> itemList = new ArrayList<>();

        Exercise item = new Exercise("Single Hand Barbell Hold", ChildItemList());
        itemList.add(item);
        Exercise item1 = new Exercise("Double Overhand Barbell Hold", ChildItemList());
        itemList.add(item1);


       return itemList;
    }

    private List<ExerciseChild> ChildItemList()
    {

        List<ExerciseChild> ChildItemList = new ArrayList<>();


            ChildItemList.add(new ExerciseChild(1,"60kg", "15", 1,10));

            ChildItemList.add(new ExerciseChild(2,"100kg", "20", 1,10));


        return ChildItemList;
    }


    private void prepareExerciseData() {
        Exercise exercise = new Exercise("Single Hand Barbell Hold","60kg", "15",1);
        mAdapter.addExercise(exercise);

        exercise = new Exercise("Double Overhand Barbell Hold","100kg","20",1);
        mAdapter.addExercise(exercise);
        writeList(getContext(),mAdapter.getExercisesList());
        mAdapter.notifyDataSetChanged();
    }

    private void buildDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View mView = inflater.inflate(R.layout.dialog_new_exercise, null);

        exerciseDialog = mView.findViewById(R.id.newExerciseDialog);
        setsTextField = mView.findViewById(R.id.sets);



        builder.setView(mView);
        builder.setTitle("Log new exercise");
        builder.setIcon(android.R.drawable.arrow_down_float)

                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // do what when you click add
                        int setsInput = Integer.parseInt(setsTextField.getText().toString());
                        List<ExerciseChild> ChildItemList = new ArrayList<>();
                        for(int i = 0; i < setsInput; i++ ){
                            int sets = i;
                            ChildItemList.add(new ExerciseChild(++sets));
                        }


                        Exercise exercise = new Exercise(exerciseDialog.getText().toString(),ChildItemList);
                        mAdapter.addExercise(exercise);
                        writeList(getContext(),mAdapter.getExercisesList());
                        mAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity().getApplicationContext(), "added another exercise!", Toast.LENGTH_SHORT).show();

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

        if (viewHolder instanceof ExerciseChildAdapter.ChildViewHolder) {

            Exercise exercise = mAdapter.getExercisesList().get(position);


            // get the removed item name to display it in snack bar
//            String name = exercisesList.get(viewHolder.getAdapterPosition()).getTitle();
//            String title = exercise.getTitle();
//            // backup of removed item for undo purpose
//            final Exercise deletedItem = exercisesList.get(viewHolder.getAdapterPosition());
//            final Exercise deletedItem = exercise;
//            final int deletedIndex = viewHolder.getAdapterPosition();
//
//            // remove the item from recycler view
//            mAdapter.removeItem(viewHolder.getAdapterPosition());
//
//            // showing snack bar with Undo option
//            Snackbar snackbar = Snackbar
//                    .make(frameLayout, title + " removed from cart!", Snackbar.LENGTH_LONG);
//            snackbar.setAction("UNDO", new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                  boolean  deleteItem = false;
//                    // undo is selected, restore the deleted item
//                    mAdapter.restoreItem(deletedItem, deletedIndex);
//                }
//            });
//            snackbar.setActionTextColor(Color.YELLOW);
//            snackbar.show();
//
//           // exercisesList = mAdapter.getExercisesList();
//           // exercisesList.remove(position);
//           // mAdapter.setExercisesList(exercisesList);
//            writeList(getContext(),mAdapter.getExercisesList());
//            mAdapter.notifyItemRemoved(position);

        }
    }
}


