package com.example.musicsquares;

import android.graphics.drawable.GradientDrawable;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    protected SoundPool soundPool;
    protected String[] currentSounds = new String[4];
    protected int[] currentGuesses = new int[4];
    protected int[] correctIntervals = new int[4];
    protected final String[] intervalsArray = {"1", "2>", "2", "3>", "3", "4", "4</5>", "5", "6>", "6", "7", "7<", "8", "9>", "9"," "};
    protected final String[] notesArray =  {"g2", "gis2", "a2", "ais2", "h2", "c3", "cis3", "d3", "dis3", "e3", "f3", "fis3", "g3", "gis3", "a3", "ais3", "h3", "c4", "cis4", "d4", "dis4", "e4", "f4", "fis4", "g4", "gis4", "a4", "ais4", "h4", "c5", "cis5", "d5", "dis5", "e5", "f5", "fis5"};
    protected final int notesArrayLength = notesArray.length;
    protected HashMap<String, Integer> intervalsMap = new HashMap<>();
    protected HashMap<String, Integer> soundsMap = new HashMap<>();
    private final Intervals intervals = new Intervals(this);
    private final Spinners spinners = new Spinners(this);
    private final Buttons buttons = new Buttons(this);
    protected boolean isUser = true;
    protected boolean[] isGuessCorrect = new boolean[4];

    protected GradientDrawable drawable = new GradientDrawable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intervals.initSoundPool();
        spinners.initSpinners();
        buttons.initButtons();

        isUser=false;
        Buttons.buttonNextOnClick();
        isUser=true;

    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.buttonPlay) Buttons.buttonPlayOnClick();
        else if (viewId == R.id.buttonNext) Buttons.buttonNextOnClick();
        else if (viewId == R.id.buttonCheck) Buttons.buttonCheckOnClick();
        else if (viewId == R.id.buttonSurrender) Buttons.buttonSurrenderOnClick();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }
}