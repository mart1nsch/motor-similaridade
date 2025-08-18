package com;

import java.util.ArrayList;

public class Data {

    private String numRA;
    private String description;
    private ArrayList<String> words;

    public Data(String numRA, String description) {
        this.numRA = numRA;
        this.description = description.toUpperCase();
        this.words = new ArrayList<>();
        this.makeWords();
    }

    public String getNumRA() {
        return numRA;
    }

    public String getDescription() {
        return description;
    }

    private void makeWords() {

        this.words.add("");

        for (int i=0; i<this.description.length(); i++) {

            if (this.description.charAt(i) != ' ') {

                int maxIndex = (this.words.size() - 1);

                this.words.set(maxIndex, this.words.get(maxIndex) + this.description.charAt(i));

            } else {

                this.words.add("");

            }

        }

        int countRemoved = 0;

        for (int i=0; i<words.size(); i++) {

            if (words.get(i).length() <= 2) {

                words.remove(i);
                countRemoved++;
                i -= countRemoved;

            }

        }

    }

    public ArrayList<String> getWords() {
        return words;
    }

}
