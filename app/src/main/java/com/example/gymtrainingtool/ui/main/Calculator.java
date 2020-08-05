package com.example.gymtrainingtool.ui.main;


import android.content.Context;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.gymtrainingtool.MainActivity;
import com.example.gymtrainingtool.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.text.DecimalFormat;




/**
 * A simple {@link Fragment} subclass.
 */
public class Calculator extends Fragment {

    double calculatedNumber = 0;
    double storedValue = 0;
    double percentage = 0;
    double finalNumber = 0;
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

    Uri defaultRingtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

    MediaPlayer mediaPlayer = new MediaPlayer();


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

        submitButton = getView().findViewById(R.id.submit);
        weight = getView().findViewById(R.id.cpWeight);
        Percentage = getView().findViewById(R.id.percentageText);
        result = getView().findViewById(R.id.KGresult);
        LBresult = getView().findViewById(R.id.LBresult);
        image = getView().findViewById(R.id.imageView);
        poundWeight = getView().findViewById(R.id.weightInKg);
        submit1 = getView().findViewById(R.id.submit1);
        xmlSwitch = getView().findViewById(R.id.switch1);
        ccSwitch = getView().findViewById(R.id.switch2);
        weight2 = getView().findViewById(R.id.ORMWeight);
        reps = getView().findViewById(R.id.Reps);
        submit2 = getView().findViewById(R.id.submit2);
        timer = getView().findViewById(R.id.tvTimer);
        start = getView().findViewById(R.id.btStart);
        pause = getView().findViewById(R.id.btPause);
        reset = getView().findViewById(R.id.btReset);
        submitCC = getView().findViewById(R.id.submitCC);
        calorieInput = getView().findViewById(R.id.calorieInput);

        handler = new Handler() ;

        df.setMaximumFractionDigits(3);

        weightConverter();
        calculateORM();
        calorieConverter();
        percentageCalculator();
        mediaPlayer();
        stopwatchHandler();
    }

    private void calculateORM() {
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

                ormWeight = Double.parseDouble(weight2.getEditText().getText().toString());

                //store the reps
                repetitions = Integer.parseInt(reps.getEditText().getText().toString());

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
                image.setVisibility(View.VISIBLE);

                if(oneRepMax!=ormWeight) {
                    result.setText(oneRepMax + "Kg" + "\n");
                    result.setTextSize(70);
                }
                LBresult.setText((df.format((int)convertedWeight)) + "Lb" + "\n");
                LBresult.setTextSize(70);
            }
        });

        // on tap remove the hint
        poundWeight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    poundWeight.setHint(" ");
                }
            }
        });

        //change the hint based of slider position
        xmlSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switchCheck = true;
                    poundWeight.setHint("Pounds");
                } else {
                    switchCheck = false;
                    poundWeight.setHint("Kilograms");
                }
            }
        });
    }



    private double convertWeightToPlateAmount(double weight){

        /**
         ----- 10KG ROUNDING LOGIC------

         To decide whether the weight will be flat value ending in 0 or if it will sit between 2.5 | 5 | 7.5
         Below 1.25 is considered a round value of eg 80
         Above 1.25 but less than 3.75 is considered a 2.5kg addition eg 82.5
         Above 3.75 but less that 6.25 is considered a 5kg addition to the whole number eg 85
         Above 6.75 but lower than 8.75 i considered a 7,5kg addition eg 87.5
         Anything above 8.75 is automatically a +10kg addition to the next whole number
         */

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

    private void weightConverter(){

        // on tap remove the hint
        poundWeight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    poundWeight.setHint(" ");
                }
            }
        });

        //change the hint based of slider position
        xmlSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switchCheck = true;
                    poundWeight.setHint("Pounds");
                } else {
                    switchCheck = false;
                    poundWeight.setHint("Kilograms");
                }
            }
        });

        // calculate either pounds or kgs based on slider position
        submit1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                if(!switchCheck) {

                    poundCalc = Double.valueOf( poundWeight.getEditText().getText().toString());

                    convertedWeight = poundCalc * 2.20462;

                    lastDigit = (int) convertedWeight; // get the last digit as well as the decimals

                    // show the picture of the weight
                    image.setVisibility(View.VISIBLE);

                    LBresult.setText((df.format(lastDigit)) + "Lb" + "\n");
                    LBresult.setTextSize(80);
                    
                }
                else{

                    poundCalc = Double.valueOf(poundWeight.getEditText().getText().toString());

                    convertedWeight = poundCalc / 2.20462;

                    lastDigit = (int) convertedWeight; // get the last digit as well as the decimals

                    // show the picture of the weight
                    image.setVisibility(View.VISIBLE);

                    result.setText((df.format(lastDigit)) + "Kg" + "\n");
                    result.setTextSize(80);
                }

            }
        });

    }

    private void calorieConverter(){

        double kjResult = 0; // calories to Kj
        double calorieResult = 0;


        // on tap remove the hint
        calorieInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    calorieInput.setHint(" ");
                }
            }
        });

        //change the hint based of slider position
        ccSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ccSwitchCheck = true;
                    calorieInput.setHint("Calories");
                } else {
                    ccSwitchCheck = false;
                    calorieInput.setHint("Kilojoules");
                }
            }
        });

        // calculate either pounds or kgs based on slider position
        submitCC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                if(!ccSwitchCheck) {

                    double calorieResult = Double.valueOf(calorieInput.getEditText().getText().toString())  / 4.184; // Kj to calories
                    int finalCalorieResult = (int) calorieResult;

                    lastDigit = (int) convertedWeight; // get the last digit as well as the decimals

                    // show the picture of the weight
                    image.setVisibility(View.VISIBLE);

                    LBresult.setText((df.format(finalCalorieResult)) + "cal" + "\n");
                    LBresult.setTextSize(80);

                }
                else{

                    double kjRawValue = Double.valueOf(calorieInput.getEditText().getText().toString()) * 4.184;
                    int kjResult = (int)kjRawValue;

                    lastDigit = (int) convertedWeight; // get the last digit as well as the decimals

                    // show the picture of the weight
                    image.setVisibility(View.VISIBLE);

                    LBresult.setText((df.format(kjResult)) + "Kj" + "\n");
                    LBresult.setTextSize(80);
                }

            }
        });

    }

    private void percentageCalculator(){

        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                //store the percentage chosen
                percentage = Double.valueOf(Percentage.getEditText().getText().toString());


                //store the weight
                storedValue = Float.parseFloat(weight.getEditText().getText().toString().trim());

                // show the picture of the weight
                image.setVisibility(View.VISIBLE);

                // get the percentage
                calculatedNumber = storedValue*(percentage/100.0f);

                result.setText((df.format(convertWeightToPlateAmount(calculatedNumber))) + "Kg" + "\n");
                result.setTextSize(80);

                //pound conversion
                double poundWeight = finalNumber * 2.20462;
                LBresult.setText((df.format((int)poundWeight)) + "Lb" + "\n");
                LBresult.setTextSize(80);

            }
        });
    }

    public Runnable runnable = new Runnable() {

        int seconds = 30;
        int minutes = 1;

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            MilliSeconds = (int) (UpdateTime % 1000);

            timer.setText("" + Minutes + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MilliSeconds));

            handler.postDelayed(this, 0);

            // play notification sound every 30 seconds
            if(Seconds == seconds) {

                mediaPlayer.start();
            }
            // play notification sound every 1 minute
            else if((Minutes == minutes) && (Seconds == 0)){

                mediaPlayer();
                mediaPlayer.start();
                minutes+=1;
            }

        }

    };

    private void mediaPlayer(){

        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(getActivity(), defaultRingtoneUri);
            //mediaPlayer.setAudioAttributes();
            mediaPlayer.prepare();

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    //mediaPlayer.release();
                    // mediaPlayer.reset();

                }
            });

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void stopwatchHandler(){

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.setVisibility(View.VISIBLE);
                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                timer.setVisibility(View.VISIBLE);
                reset.setEnabled(false);

                mediaPlayer();

            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimeBuff += MillisecondTime;

                handler.removeCallbacks(runnable);

                reset.setEnabled(true);

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MillisecondTime = 0L ;
                StartTime = 0L ;
                TimeBuff = 0L ;
                UpdateTime = 0L ;
                Seconds = 0 ;
                Minutes = 0 ;
                MilliSeconds = 0 ;
                timer.setText("00:00:00");

            }
        });
    }



}

