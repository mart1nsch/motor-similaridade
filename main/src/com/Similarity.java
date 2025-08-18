package com;

import java.util.ArrayList;

public class Similarity {

    private Characteristic[] characteristics;
    private Data data;
    private Double[] vectors;

    public Similarity(Characteristic[] characteristics, Data data) {
        this.characteristics = characteristics;
        this.data = data;
        this.vectors = new Double[characteristics.length];
    }

    public Double[] getVectors() {
        return vectors;
    }

    public void calculateVectors() {

        for (int i=0; i<vectors.length; i++) {

            int countWordsLike = 0;

            // No futuro isso aqui pode ser trocado por uma binary search para voar na velocidade, mas temos
            // que ordernar as palavras que definem cada caracteristica dos modulos para isso
            for (int j=0; j<data.getWords().size(); j++) {

                for (int k=0; k<characteristics[i].getWords().size(); k++) {

                    if (characteristics[i].getWords().get(k).contains(data.getWords().get(j))) {

                        countWordsLike++;
                        break;

                    }

                }

            }

            vectors[i] = (double) Math.round(((double) countWordsLike / characteristics[i].getWords().size()) * 100);

        }

    }

}
