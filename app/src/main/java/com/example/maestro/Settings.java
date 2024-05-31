package com.example.maestro;

public class Settings {
    private boolean allowedMinor;
    private boolean allowedSharp;
    private boolean frenchNotation;
    private int delay;

    public Settings(MainActivity mainActivity){
        this.allowedMinor= mainActivity.getMinorSwitch().isChecked();
        this.allowedSharp= mainActivity.getSharpSwitch().isChecked();
        this.delay = mainActivity.getRadioValue();
    }

    public int getDelay() {
        return delay;
    }

    public boolean isAllowedMinor() {
        return allowedMinor;
    }

    public boolean isAllowedSharp() {
        return allowedSharp;
    }

    public boolean isFrenchNotation() {
        return frenchNotation;
    }

    public void setAllowedMinor(boolean allowedMinor) {
        this.allowedMinor = allowedMinor;
    }

    public void setAllowedSharp(boolean allowedSharp) {
        this.allowedSharp = allowedSharp;
    }

    public void setFrenchNotation(boolean frenchNotation) {
        this.frenchNotation = frenchNotation;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
