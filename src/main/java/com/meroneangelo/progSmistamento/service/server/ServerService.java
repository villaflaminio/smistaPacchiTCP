package main.java.com.meroneangelo.progSmistamento.service.server;

import main.java.com.meroneangelo.progSmistamento.model.Pacco;
import main.java.com.meroneangelo.progSmistamento.model.TransitoPacco;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServerService {

    public static List<Pacco> getListPacchiFromCsv(String csvFile) {
        String csvFile = "src/main/resources/pacco.csv";
        String line = "";
        String cvsSplitBy = ";";
        List<Pacco> listPacco = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {
                String[] paccoData = line.split(cvsSplitBy);
                Pacco p = new Pacco(Integer.parseInt(paccoData[0]), paccoData[1],paccoData[2], paccoData[3]);

                listPacco.add(p);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(listPacco);
        return listPacco;
    }

    public static List<TransitoPacco> getListPacchiTransitoFromCsv(String csvFile) {
        String csvFile = "src/main/resources/pacco.csv";

        String line = "";
        String cvsSplitBy = ";";
        List<TransitoPacco> listTransitoPacco = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {
                String[] paccoData = line.split(cvsSplitBy);
                TransitoPacco p = new TransitoPacco(Integer.parseInt(paccoData[0]), paccoData[1],paccoData[2], paccoData[3] , Boolean.parseBoolean(paccoData[4]));

                listTransitoPacco.add(p);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(listTransitoPacco);
        return listTransitoPacco;
    }

}