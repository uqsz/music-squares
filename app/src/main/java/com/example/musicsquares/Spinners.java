package com.example.musicsquares;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Spinners {
    public static MainActivity main;
    public static Spinner[] spinners;
    public Spinners(MainActivity mainActivity) {
        Spinners.main = mainActivity;
    }

    public void initSpinners(){
        for (int i = 0; i < main.intervalsArray.length; i++) {
            main.intervalsMap.put(main.intervalsArray[i], i);
        }
        spinners = new Spinner[]{
                main.findViewById(R.id.spinnerIntervalLeft),
                main.findViewById(R.id.spinnerIntervalUp),
                main.findViewById(R.id.spinnerIntervalRight),
                main.findViewById(R.id.spinnerIntervalDown)
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(main, android.R.layout.simple_spinner_item, main.intervalsArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for (int i = 0; i < spinners.length; i++) {
            final int finalI = i;

            spinners[i].setAdapter(adapter);
            spinners[i].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    String selectedInterval = (String) parentView.getItemAtPosition(position);
                    main.currentGuesses[finalI] = main.intervalsMap.get(selectedInterval);
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }
}
