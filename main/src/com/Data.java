package com;

import java.util.ArrayList;

public class Data {

    private String numRA;
    private String description;
    private ArrayList<String> words;

    public Data(String numRA, String description) {
        this.numRA = numRA;
        this.description = description.toUpperCase();
    }

    public String getNumRA() {
        return numRA;
    }

    public String getDescription() {
        return description;
    }

    public void setWords(ArrayList<String> words) {
        this.words = words;
    }

    public ArrayList<String> getWords() {
        return words;
    }

}
