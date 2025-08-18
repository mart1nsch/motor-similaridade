package com;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Escolha a operação: ");
        System.out.println("1. Popular dados");
        System.out.println("2. Busca");

        int choice = scanner.nextInt();

        if (choice == 1) {

            populateData();

        } else if (choice == 2) {

            System.out.println("Descrição a ser buscada: ");

            scanner.nextLine();
            String stringSearch = scanner.nextLine();

            search(stringSearch);

        }

        scanner.close();

    }

    public static Characteristic[] getCharacteristics() {

        Characteristic[] characteristics = {
                new Characteristic("ESTOQUE"),
                new Characteristic("PEDIDO_VENDA"),
                new Characteristic("FATURAMENTO")
        };

        // ESTOQUE
        characteristics[0].addWord("BAIXA");
        characteristics[0].addWord("DIVERGENCIA");
        characteristics[0].addWord("ERRADO");
        characteristics[0].addWord("ESTO7");
        characteristics[0].addWord("ESTOQUE");
        characteristics[0].addWord("FUTURO");
        characteristics[0].addWord("IDENTIFICACAO");
        characteristics[0].addWord("LOCAL");
        characteristics[0].addWord("LOCALIZACAO");
        characteristics[0].addWord("NEGATIVO");
        characteristics[0].addWord("PRODUTO");
        characteristics[0].addWord("RESERVA");
        characteristics[0].addWord("RESERVADO");
        characteristics[0].addWord("ROTULO");
        characteristics[0].addWord("REQUISICAO");
        characteristics[0].addWord("INFORMADA");
        characteristics[0].addWord("GERADA");

        // PEDIDO_VENDA
        characteristics[1].addWord("CONFIRMACAO");
        characteristics[1].addWord("CONFIRMAR");
        characteristics[1].addWord("PEDI1");
        characteristics[1].addWord("PEDIDO");
        characteristics[1].addWord("VENDA");

        // FATURAMENTO
        characteristics[2].addWord("CONFIRMAR");
        characteristics[2].addWord("EMISSAO");
        characteristics[2].addWord("FATU2");
        characteristics[2].addWord("FATURAR");
        characteristics[2].addWord("FISCAL");
        characteristics[2].addWord("NF");
        characteristics[2].addWord("NFS");
        characteristics[2].addWord("NOTA");
        characteristics[2].addWord("SAIDA");

        return characteristics;

    }

    public static Data[] getData() {

        Data[] data = {
                new Data("1", "DIVERGENCIA NO ESTOQUE"),
                new Data("2", "ERRO AO CONFIRMAR PEDIDO DE VENDA DE ESTOQUE FUTURO"),
                new Data("3", "ERRO ESTOQUE INSERTOS PRODUZIDOS E QUE NÃO APARECEM NO RESERVADO Ticket 93950"),
                new Data("4", "ESTOQUE DO ITEM 6903 NO LOCAL 06"),
                new Data("5", "ERRO ESTOQUE FUTURO TICKET 135171"),
                new Data("6", "pedi1 - pedido de venda "),
                new Data("7", "Ticket 194602 - Erro ao Confirmar o Pedido de Venda"),
                new Data("8", "Erro de estoque ao confirmar Pedido de Venda"),
                new Data("9", "ERRO AO FAZER PEDIDO DE VENDA URGENTE"),
                new Data("10", "Erro ao abrir o programa pedido de venda "),
                new Data("11", "ERRO AO EMITIR NOTA FISCAL"),
                new Data("12", "erro estoque nota fiscal")
        };

        return data;

    }

    public static void populateData() {

        Characteristic[] characteristics = getCharacteristics();

        Data[] ra = getData();

        StringBuilder info = new StringBuilder();

        for (int i=0; i<ra.length; i++) {

            Similarity similarity = new Similarity(characteristics, ra[i]);
            similarity.calculateVectors();

            info.append(ra[i].getNumRA());

            for (int j=0; j<similarity.getVectors().length; j++) {

                info.append(",").append(similarity.getVectors()[j]);

            }

            info.append(",\n");

        }

        populateArchive(info);

    }

    public static void populateArchive(StringBuilder info) {

        try {

            FileWriter file = new FileWriter("./data.txt");
            file.write(info.toString());
            file.close();

        } catch (IOException e) {

            System.out.println("Erro no arquivo: " + e);

        }

    }

    public static void search(String stringSearch) {

        String raFirstScore = "";
        String raSecondScore = "";
        String raThirdScore = "";
        double firstScore;
        double secondScore;
        double thirdScore;

        Characteristic[] characteristics = getCharacteristics();

        Data dataSearch = new Data("X", stringSearch);

        Similarity similarity = new Similarity(characteristics, dataSearch);
        similarity.calculateVectors();

        ArrayList<DataRa> allData = new ArrayList<>();

        try {

            File file = new File("./data.txt");
            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {

                DataRa dataRa = new DataRa();

                String dataLine = reader.nextLine();

                boolean foundNumRa = false;
                String doublePlaceHolder = "";

                for (int i=0; i<dataLine.length(); i++) {

                    if (!foundNumRa && dataLine.charAt(i) != ',') {
                        dataRa.setNumRa(dataRa.getNumRa() + dataLine.charAt(i));
                    } else if (!foundNumRa) {
                        foundNumRa = true;
                    } else if (dataLine.charAt(i) != ',') {
                        doublePlaceHolder += dataLine.charAt(i);
                    } else {
                        dataRa.addVector(Double.parseDouble(doublePlaceHolder));
                        doublePlaceHolder = "";
                    }

                }

                allData.add(dataRa);

            }

            reader.close();

        } catch (Exception e) {

            System.out.println("Erro ao abrir o arquivo: " + e);

        }

        DataRa[] differences = new DataRa[allData.size()];

        for (int i=0; i<differences.length; i++) {
            differences[i] = new DataRa();
        }

        for (int i=0; i<differences.length; i++) {

            differences[i].setNumRa(allData.get(i).getNumRa());

            for (int j=0; j<allData.get(i).getVectors().size(); j++) {

                double calculation = allData.get(i).getVectors().get(j) - similarity.getVectors()[j];

                if (calculation < 0) {
                    calculation *= -1;
                }

                differences[i].addVector(calculation);

            }

        }

        raFirstScore = differences[0].getNumRa();
        raSecondScore = differences[0].getNumRa();
        raThirdScore = differences[0].getNumRa();

        double sumFirstVectors = 0;

        for (int j=0; j<differences[0].getVectors().size(); j++) {
            sumFirstVectors += differences[0].getVectors().get(j);
        }

        firstScore = sumFirstVectors;
        secondScore = sumFirstVectors;
        thirdScore = sumFirstVectors;

        for (int i=1; i<differences.length; i++) {

            double sumVectors = 0;

            for (int j=0; j<differences[i].getVectors().size(); j++) {
                sumVectors += differences[i].getVectors().get(j);
            }

            if (firstScore > sumVectors) {

                thirdScore = secondScore;
                raThirdScore = raSecondScore;

                secondScore = firstScore;
                raSecondScore = raFirstScore;

                firstScore = sumVectors;
                raFirstScore = differences[i].getNumRa();

            } else if (secondScore > sumVectors) {

                thirdScore = secondScore;
                raThirdScore = raSecondScore;

                secondScore = sumVectors;
                raSecondScore = differences[i].getNumRa();

            } else if (thirdScore > sumVectors) {

                thirdScore = sumVectors;
                raThirdScore = differences[i].getNumRa();

            }

        }

        firstScore = 100 - firstScore;
        secondScore = 100 - secondScore;
        thirdScore = 100 - thirdScore;

        System.out.println("---- COLOCAÇÕES ----");
        System.out.println("1 RA: " + raFirstScore);
        System.out.println("1 Score: " + firstScore);
        System.out.println("2 RA: " + raSecondScore);
        System.out.println("2 Score: " + secondScore);
        System.out.println("3 RA: " + raThirdScore);
        System.out.println("3 Score: " + thirdScore);

    }

}
