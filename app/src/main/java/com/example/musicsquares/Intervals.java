package com.example.musicsquares;

import static java.lang.Math.abs;

import android.media.AudioAttributes;
import android.media.SoundPool;

import java.lang.reflect.Field;

import java.util.Random;
public class Intervals {
    public static MainActivity main;
    public Intervals(MainActivity mainActivity) {
        Intervals.main = mainActivity;
    }
    public void initSoundPool(){
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        main.soundPool = new SoundPool.Builder()
                .setMaxStreams(4)
                .setAudioAttributes(audioAttributes)
                .build();

        Field[] fields = R.raw.class.getFields();

        for (Field field : fields) {
            String soundName = field.getName();
            int soundResource;
            try {
                soundResource = field.getInt(null);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            int soundId = main.soundPool.load(main, soundResource, 1);
            main.soundsMap.put(soundName, soundId);
        }
    }
    private static int randint(int min, int max, Random random) {
        return random.nextInt(max - min + 1) + min;
    }

    public static void pickRandomNotes(){
        int[] chosenNotes = new int[4];
        Random random = new Random(System.currentTimeMillis());

        chosenNotes[0] = randint(0, 25,random);
        chosenNotes[1] = randint(chosenNotes[0], Math.min(chosenNotes[0] + 14, 35),random);
        chosenNotes[2] = randint(Math.max(0, chosenNotes[0] - 14), chosenNotes[1],random);
        chosenNotes[3] = randint(Math.max(chosenNotes[0], chosenNotes[2]), Math.min(chosenNotes[2] + 14, 35),random);

        main.correctIntervals[0]=chosenNotes[1]-chosenNotes[0];
        main.correctIntervals[1]=abs(chosenNotes[3]-chosenNotes[1]);
        main.correctIntervals[2]=chosenNotes[3]-chosenNotes[2];
        main.correctIntervals[3]=abs(chosenNotes[2]-chosenNotes[0]);

        for (int i=0;i<4;i++){
            main.currentSounds[i] = main.notesArray[chosenNotes[i]];
            Buttons.soundsIDs[i]=main.soundsMap.get(main.currentSounds[i]);
        }
    }
}
