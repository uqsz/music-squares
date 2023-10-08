package com.example.musicsquares;

import android.os.Handler;
import android.widget.Button;
import android.widget.Toast;

import java.util.Arrays;

public class Buttons {
    public static MainActivity main;
    public Buttons(MainActivity mainActivity) {
        Buttons.main = mainActivity;
    }
    private static final Handler handler = new Handler();
    protected static int[] soundsIDs = new int[4];
    protected static boolean isSurrendered = false;
    public void initButtons(){
        Button buttonPlay = main.findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(main);

        Button buttonNext = main.findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(main);

        Button buttonCheck = main.findViewById(R.id.buttonCheck);
        buttonCheck.setOnClickListener(main);

        Button buttonSurrender = main.findViewById(R.id.buttonSurrender);
        buttonSurrender.setOnClickListener(main);
    }
    public static void buttonPlayOnClick() {
        main.findViewById(R.id.buttonSurrender).setEnabled(false);
        main.findViewById(R.id.buttonPlay).setEnabled(false);
        main.findViewById(R.id.buttonNext).setEnabled(false);

        Thread soundThread = new Thread(() -> {
            main.soundPool.play(soundsIDs[0], 1.0f, 1.0f, 1, 0, 1.0f);
            main.soundPool.play(soundsIDs[1], 1.0f, 1.0f, 1, 0, 1.0f);
            try {
                Thread.sleep(2650);
            } catch (InterruptedException e) {
                return;
            }
            main.soundPool.play(soundsIDs[2], 1.0f, 1.0f, 1, 0, 1.0f);
            main.soundPool.play(soundsIDs[3], 1.0f, 1.0f, 1, 0, 1.0f);
            try {
                Thread.sleep(2650);
            } catch (InterruptedException e) {
                return;
            }
            handler.postDelayed(() -> {
                main.findViewById(R.id.buttonPlay).setEnabled(true);
                if (!isSurrendered) main.findViewById(R.id.buttonSurrender).setEnabled(true);
                else main.findViewById(R.id.buttonNext).setEnabled(true);
            }, 200);
        });
        soundThread.start();
    }
    public static void buttonNextOnClick(){
        Arrays.fill(main.isGuessCorrect, false);
        main.findViewById(R.id.buttonCheck).setEnabled(true);
        main.findViewById(R.id.buttonSurrender).setEnabled(true);
        Intervals.pickRandomNotes();
        for (int i = 0; i < 4; i++) {
            Spinners.spinners[i].setSelection(main.intervalsArray.length - 1);
            Spinners.spinners[i].setBackgroundResource(android.R.color.holo_blue_light);
        }
        if (main.isUser) {
            Toast.makeText(main.getApplicationContext(), "Next square", Toast.LENGTH_SHORT).show();
            buttonPlayOnClick();
        }
        main.findViewById(R.id.buttonNext).setEnabled(false);
        isSurrendered=false;
    }
    public static void buttonCheckOnClick(){
        boolean flag = false;
        for (int i=0; i<4;i++) {
            if (main.currentGuesses[i] == main.correctIntervals[i]) {
                Spinners.spinners[i].setBackgroundResource(android.R.color.holo_green_light);
                main.isGuessCorrect[i]=true;
            }
            else{
                Spinners.spinners[i].setBackgroundResource(android.R.color.holo_red_light);
                flag=true;
            }
        }
        if (flag) {
            Toast.makeText(main.getApplicationContext(), "Correct your answers", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(main.getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();
            isSurrendered = true;
            main.findViewById(R.id.buttonNext).setEnabled(true);
            main.findViewById(R.id.buttonCheck).setEnabled(false);
            main.findViewById(R.id.buttonSurrender).setEnabled(false);
        }
    }

    public static void buttonSurrenderOnClick(){
        Toast.makeText(main.getApplicationContext(), "Surrendered!", Toast.LENGTH_SHORT).show();
        for (int i=0;i<Spinners.spinners.length;i++){
            if (!main.isGuessCorrect[i]) {
                Spinners.spinners[i].setSelection(main.correctIntervals[i]);
                Spinners.spinners[i].setBackgroundResource(android.R.color.holo_orange_light);
            }
        }
        main.findViewById(R.id.buttonNext).setEnabled(true);
        main.findViewById(R.id.buttonCheck).setEnabled(false);
        main.findViewById(R.id.buttonSurrender).setEnabled(false);
        isSurrendered=true;
    }
}
