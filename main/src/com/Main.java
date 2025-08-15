package com;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        Data[] data = {
                new Data("220707019", "ERRO ESTOQUE DE AVULSO - APARECERAM PARES DO NADA NOS AVULSOS/DISPONIVEL"),
                new Data("220310004", "ERRO AO TENTAR ATIVAR PEDIDO ESTOQUE FUTURO COM ENTREGA CANCELADA"),
                new Data("220330004", "ERRO AO CONFIRMAR PEDIDO DE VENDA DE ESTOQUE FUTURO")
        };

        for (int i=0; i<data.length; i++) {

            data[i].setWords(getWordsFromString(data[i].getDescription()));

            /*System.out.println("---- RA ----");

            for (int j=0; j<data[i].getWords().size(); j++) {
                System.out.println(data[i].getWords().get(j));
            }*/

        }

        Data[] dataSearch = {
                new Data("000", "Erro de estoque estoque futuro ativa")
        };

        dataSearch[0].setWords(getWordsFromString(dataSearch[0].getDescription()));

        /*System.out.println("---- SEARCH ----");

        for (int i=0; i<dataSearch[0].getWords().size(); i++) {
            System.out.println(dataSearch[0].getWords().get(i));
        }*/

        System.out.println("---- SIMILARITY ----");

        for (int i=0; i<data.length; i++) {
            double similarity = calculateSimilarity(data[i], dataSearch[0].getWords());
            System.out.println("RA: " + data[i].getNumRA() + " Score: " + similarity);
        }

    }

    public static ArrayList<String> getWordsFromString(String string) {

        ArrayList<String> words = new ArrayList<>();
        words.add(""); // Para iniciar com uma palavra

        for (int i=0; i<string.length(); i++) {

            if (string.charAt(i) != ' ') { // Espaço
                int index = (words.size() - 1);
                words.set(index, words.get(index) + string.charAt(i));
            } else {
                words.add("");
            }

        }

        ArrayList<Integer> indexesDelete = new ArrayList<>();

        for (int i=0; i<words.size(); i++) {

            if (words.get(i).length() <= 2 && notContainsNumbers(words.get(i))) {
                indexesDelete.add(i);
            }

        }

        int deleted = 0;

        for (int i=0; i<indexesDelete.size(); i++) {

            words.remove((indexesDelete.get(i) - deleted));
            deleted++;

        }

        return words;

    }

    public static double calculateSimilarity(Data data, ArrayList<String> wordsSearch) {

        int countWordsLike = 0;

        for (int i=0; i<wordsSearch.size(); i++) {

            for (int j=0; j<data.getWords().size(); j++) {

                if (data.getWords().get(j).contains(wordsSearch.get(i))) {
                    countWordsLike++;
                    break;
                }

            }

        }

        return Math.round(((double) countWordsLike / wordsSearch.size()) * 100);

    }

    public static boolean notContainsNumbers(String string) {

        for (int i=0; i<string.length(); i++) {

            switch (string.charAt(i)) {
                case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9': return false;
            }

        }

        return true;
    }

}
