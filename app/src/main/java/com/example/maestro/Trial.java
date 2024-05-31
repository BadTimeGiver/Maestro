package com.example.maestro;

import android.os.Handler;
import android.os.Looper;
import android.text.Html;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Trial {
    private final Chord[] chordList;
    private int i;
    private int currentTimer;
    private Runnable chordRunnable;
    private Handler handler;

    public Trial() {
        chordList = Chord.values();
        i = 0;
    }

    public void startTrial(MainActivity mainActivity, Settings settings){
        Chord[] shuffledList = sanitizeList(settings);
        currentTimer = settings.getDelay()+1;
        i=0;
        Collections.shuffle(Arrays.asList(shuffledList));
        handler = new Handler(Looper.getMainLooper());
        mainActivity.getChordView().setText(Html.fromHtml(displayChord(shuffledList[i],settings),Html.FROM_HTML_MODE_COMPACT));
        chordRunnable = new Runnable() {
            @Override
            public void run() {
                currentTimer--;
                if(currentTimer==0) {
                    currentTimer = settings.getDelay();
                    if (i < shuffledList.length - 1){
                        i++;
                    } else {
                        stopTrial(mainActivity);
                        return;
                    }
                    mainActivity.getChordView().setText(Html.fromHtml(displayChord(shuffledList[i],settings),Html.FROM_HTML_MODE_COMPACT));
                }
                handler.postDelayed(this,1000L);
                mainActivity.getTimerView().setText(String.valueOf(currentTimer));
            }
        };
        handler.post(chordRunnable);
    }

    public void stopTrial(MainActivity mainActivity) {
        mainActivity.getChordView().setText("");
        mainActivity.getTimerView().setText("");
        mainActivity.getStartButton().setEnabled(true);
        for(int i = 0; i<3; i++){
            mainActivity.getDelayGroup().getChildAt(i).setEnabled(true);
        }
        mainActivity.getMinorSwitch().setEnabled(true);
        mainActivity.getSharpSwitch().setEnabled(true);
        mainActivity.getFrenchNotationSwitch().setEnabled(true);
        mainActivity.getResetButton().setEnabled(false);
        handler.removeCallbacks(chordRunnable);
    }

    public String displayChord(Chord chord, Settings settings){
        String trueValue = settings.isFrenchNotation() ? chord.getFrenchValue() : chord.getValue();
        String subscript = chord.isMinor() ? "m" : "";
        String superscript = "";
        if (chord.isFlat()){
            superscript = "b";
        } else if (chord.isSharp()) {
            superscript="#";
        }
        return trueValue+"<sup>"+superscript+"</sup><sub>" +subscript+"</sub>";
    }
    public Chord[] sanitizeList(Settings settings){
        ArrayList<Chord> sanitizedList = new ArrayList<>();
        for (Chord chord: chordList) {
            if (!settings.isAllowedMinor() && chord.isMinor()){
                continue;
            }
            if (!settings.isAllowedSharp() && (chord.isFlat() || chord.isSharp())){
                continue;
            }
            sanitizedList.add(chord);
        }
        Chord[] finalList = new Chord[sanitizedList.size()];
        finalList = sanitizedList.toArray(finalList);
        return finalList;

    }

}
