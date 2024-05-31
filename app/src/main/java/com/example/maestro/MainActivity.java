package com.example.maestro;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

@SuppressLint("UseSwitchCompatOrMaterialCode")
public class MainActivity extends AppCompatActivity {
    private Settings settings;
    private Trial trial;
    private Switch minorSwitch;
    private Switch sharpSwitch;
    private Switch frenchNotationSwitch;
    private RadioGroup delayGroup;
    private TextView chordView;
    private TextView timerView;
    private Button startButton;
    private Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        minorSwitch = findViewById(R.id.minorSwitch);
        sharpSwitch = findViewById(R.id.sharpSwitch);
        frenchNotationSwitch = findViewById(R.id.frenchNotationSwitch);
        delayGroup = findViewById(R.id.delayGroup);
        chordView= findViewById(R.id.chordView);
        timerView = findViewById(R.id.timerView);
        startButton = findViewById(R.id.startButton);
        resetButton = findViewById(R.id.resetButton);
        settings = new Settings(this);
        trial = new Trial();
        setEventRadioGroup();
        setEventMinorSwitch();
        setEventSharpSwitch();
        setEventFrenchNotationSwitch();
        setEventStartButton();
        setEventResetButton();
    }



    public Switch getMinorSwitch(){
        return this.minorSwitch;
    }

    public Switch getSharpSwitch() {
        return this.sharpSwitch;
    }

    public Switch getFrenchNotationSwitch() {
        return frenchNotationSwitch;
    }

    public Button getStartButton() {
        return startButton;
    }

    public Button getResetButton() {
        return resetButton;
    }

    public RadioGroup getDelayGroup() {
        return delayGroup;
    }

    public TextView getChordView() {
        return chordView;
    }

    public TextView getTimerView() {
        return timerView;
    }

    public int getRadioValue(){
        int selectedId = delayGroup.getCheckedRadioButtonId();
        RadioButton selectedButton = findViewById(selectedId);
        String lastStep = selectedButton.getText().toString();
        return Integer.parseInt(lastStep.split("s")[0]);
    }
    public void setEventRadioGroup(){
        for (int i = 0; i < delayGroup.getChildCount(); i++) {
            View view = delayGroup.getChildAt(i);
            if (view instanceof RadioButton) {
                RadioButton radioButton = (RadioButton) view;
                radioButton.setOnClickListener(v -> settings.setDelay(getRadioValue()));
            }
        }
    }
    public void setEventMinorSwitch() {
        minorSwitch.setOnCheckedChangeListener((compoundButton, b) -> settings.setAllowedMinor(minorSwitch.isChecked()));
    }
    public void setEventSharpSwitch(){
        sharpSwitch.setOnCheckedChangeListener((compoundButton, b) -> settings.setAllowedSharp(sharpSwitch.isChecked()));
    }
    public void setEventFrenchNotationSwitch(){
        frenchNotationSwitch.setOnCheckedChangeListener((compoundButton, b) -> settings.setFrenchNotation(frenchNotationSwitch.isChecked()));
    }
    public void setEventStartButton(){
        startButton.setOnClickListener(v -> {
            trial.startTrial(this,settings);
            startButton.setEnabled(false);
            for(int i = 0; i<3; i++){
                delayGroup.getChildAt(i).setEnabled(false);
            }
            minorSwitch.setEnabled(false);
            sharpSwitch.setEnabled(false);
            frenchNotationSwitch.setEnabled(false);
            resetButton.setEnabled(true);
        });
    }
    private void setEventResetButton() {
        resetButton.setOnClickListener(v->{
            trial.stopTrial(this);
            startButton.setEnabled(true);
            for(int i = 0; i<3; i++){
                delayGroup.getChildAt(i).setEnabled(true);
            }
            minorSwitch.setEnabled(true);
            sharpSwitch.setEnabled(true);
            frenchNotationSwitch.setEnabled(true);
            resetButton.setEnabled(false);
        });
    }
}