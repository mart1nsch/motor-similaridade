package com;

import java.util.ArrayList;

public class DataRa {

    private String numRa;
    private ArrayList<Double> vectors;

    public DataRa() {
        this.numRa = "";
        this.vectors = new ArrayList<>();
    }

    public String getNumRa() {
        return numRa;
    }

    public ArrayList<Double> getVectors() {
        return vectors;
    }

    public void setNumRa(String numRa) {
        this.numRa = numRa;
    }

    public void addVector(Double value) {
        this.vectors.add(value);
    }

}
