package com.example.gymtrainingtool;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;

import com.example.gymtrainingtool.ui.main.Calculator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.gymtrainingtool.ui.main.SectionsPagerAdapter;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

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

    EditText poundWeight, weight, weight2, reps, Percentage, calorieInput;
    TextView result, LBresult, timer;
    ImageView image;

    Switch xmlSwitch;
    Switch ccSwitch;

    Button start, pause, reset, submitButton, submit1, submit2, submitCC;
    Handler handler;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    int Seconds, Minutes, MilliSeconds ;

    Uri defaultRingtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

    MediaPlayer mediaPlayer = new MediaPlayer();
    private int noOfTabs = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submit2 = findViewById(R.id.submit2);
        reps = findViewById(R.id.Reps);
        weight2 = findViewById(R.id.ORMWeight);
        result = findViewById(R.id.KGresult);
        LBresult = findViewById(R.id.LBresult);

        final TabLayout tabLayout = findViewById(R.id.tabBar);
        TabItem tools = findViewById(R.id.tab1);
        TabItem grip = findViewById(R.id.tab2);
        final ViewPager viewPager = findViewById(R.id.view_pager);


        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(sectionsPagerAdapter);

        //Calculator calculator = (Calculator)getSupportFragmentManager().findFragmentByTag(R.layout.fragment_calculator)

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout)); // makes the tab indicator work

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

;

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



    }


}