package main.java.com.meroneangelo.progSmistamento.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class ComuniService {
    //Singleton pattern

    private HashMap<String, String> centriAccettazione;
    private HashMap<String, String> centriSmistamento;
    private static ComuniService instance;

    public static HashMap<String, String> getListFromCsv(String csvFile) {
        String line = "";
        String cvsSplitBy = ";";
        HashMap<String, String> list = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                String[] country = line.split(cvsSplitBy);
                list.put(country[1], country[0]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }


    private ComuniService() {
        centriAccettazione = getListFromCsv("src/main/java/resources/comuniCapAccettazione.csv");
        centriSmistamento = getListFromCsv("src/main/java/resources/comuniCapSmistamento.csv");
    }

    public static ComuniService getInstance() {
        // Crea l'oggetto solo se NON esiste:
        if (instance == null) {
            instance = new ComuniService();
        }
        return instance;
    }


    public HashMap<String, String> getCentriAccettazione() {
        return centriAccettazione;
    }

    public String getCentroAccettazione(String cap) {
        return centriAccettazione.get(cap);
    }
    public void setCentriAccettazione(HashMap<String, String> centriAccettazione) {
        this.centriAccettazione = centriAccettazione;
    }

    public HashMap<String, String> getCentriSmistamento() {
        return centriSmistamento;
    }

    public void setCentriSmistamento(HashMap<String, String> centriSmistamento) {
        this.centriSmistamento = centriSmistamento;
    }
}
