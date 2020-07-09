package com.example.gymtrainingtool.ui.main;


import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import com.example.gymtrainingtool.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.text.DecimalFormat;




/**
 * A simple {@link Fragment} subclass.
 */
public class Calculator extends Fragment {

    double calculatedNumber = 0;
    double storedValue = 0;
    double percentage = 0;
    double finalNumber = 0;
    double rawFinal = 0;
    double poundCalc = 0;
    double convertedWeight = 0;
    double ormWeight = 0;
    double oneRepMax = 0;

    int repetitions = 0;
    int intPart = 0;
    int lastDigit = 0;

    Boolean switchCheck = false;
    boolean ccSwitchCheck = false;

    DecimalFormat df = new DecimalFormat();


    TextView result, LBresult, timer;
    ImageView image;
    TextInputLayout poundWeight, weight, weight2, reps, Percentage, calorieInput;

    Switch xmlSwitch;
    Switch ccSwitch;

    Button start, pause, reset, submitButton, submit1, submit2, submitCC;
    Handler handler;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    int Seconds, Minutes, MilliSeconds ;


    public Calculator() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calculator, container, false);


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);

        submit2 = getView().findViewById(R.id.submit2);
        reps = getView().findViewById(R.id.Reps);
        weight2 = getView().findViewById(R.id.ORMWeight);
        result = getView().findViewById(R.id.KGresult);
        LBresult = getView().findViewById(R.id.LBresult);


        submit2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if (v != null) {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }

                // store the weight

                ormWeight = Double.valueOf(weight2.getEditText().getText().toString());

                //store the reps
                repetitions = Integer.valueOf(reps.getEditText().getText().toString());

                //formula to get 1RM
                if(repetitions>1) {
                   // oneRepMax = ormWeight * (1 + repetitions / 30f);
                    double byz = 36/((double)37-repetitions);
                    oneRepMax = ormWeight * byz;
                    oneRepMax = convertWeightToPlateAmount(oneRepMax);
                }
                else{
                    // for retards who enter 1 rep
                    oneRepMax = ormWeight;
                    result.setText((int)oneRepMax + "Kg" + "\n");
                    result.setTextSize(80);
                }

                //pound conversion
                convertedWeight = oneRepMax * 2.20462;
               // lastDigit = convertedWeight;



             //   weight.setText(String.valueOf((int)oneRepMax)); // pre-set percentage calculator weight value to the ORM

                // show the picture of the weight
               // image.setVisibility(View.VISIBLE);

                if(oneRepMax!=ormWeight) {
                    result.setText(oneRepMax + "Kg" + "\n");
                    result.setTextSize(70);
                }
                LBresult.setText((df.format((int)convertedWeight)) + "Lb" + "\n");
                LBresult.setTextSize(70);
            }
        });



    }



    private double convertWeightToPlateAmount(double weight){

        intPart = (int) weight;

        double lastDigit = weight % 10; // get the last digit as well as the decimals

        // cases for weight between depending in what range it falls
        if(lastDigit > 1.25 && lastDigit <= 3.75){
            finalNumber = weight - lastDigit;
            finalNumber = finalNumber + 2.5;
        }
        else if(lastDigit <= 1.25){
            finalNumber = weight - lastDigit; // round down to the nearest 10
        }
        else if(lastDigit > 3.75 && lastDigit <= 6.25){
            finalNumber = weight - lastDigit; // get the rounded down number to the closest 10
            finalNumber = finalNumber + 5;
        }
        else if(lastDigit > 6.25 && lastDigit <= 8.75){
            finalNumber = weight - lastDigit;
            finalNumber = finalNumber + 7.5;
        }
        else if(lastDigit > 8.75){
            finalNumber = weight - lastDigit;
            finalNumber = finalNumber + 10;
        }

        return finalNumber;

    }





}

