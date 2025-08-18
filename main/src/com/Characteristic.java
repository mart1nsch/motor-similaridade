package com;

import java.util.ArrayList;

public class Characteristic {

    private String type;
    private ArrayList<String> words;

    public Characteristic(String type) {
        this.type = type;
        this.words = new ArrayList<>();
    }

    public String getType() {
        return type;
    }

    public ArrayList<String> getWords() {
        return words;
    }

    public void addWord(String word) {
        words.add(word);
    }

}
