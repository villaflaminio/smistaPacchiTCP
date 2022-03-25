package main.java.com.meroneangelo.progSmistamento.service.server;

import main.java.com.meroneangelo.progSmistamento.model.Pacco;
import main.java.com.meroneangelo.progSmistamento.model.TransitoPacco;
import main.java.com.meroneangelo.progSmistamento.service.ComuniService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServerService {
    //Singleton pattern
    private static ServerService instance;
    private List<Pacco> listPacchi;
    private List<TransitoPacco> listTransitoPacchi;

    private ServerService() {
        listPacchi = getListPacchiFromCsv();
        listTransitoPacchi = getListPacchiTransitoFromCsv();
    }

    public static ServerService getInstance() {
        // Crea l'oggetto solo se NON esiste:
        if (instance == null) {
            instance = new ServerService();
        }
        return instance;
    }


    public static List<Pacco> getListPacchiFromCsv() {
        String csvFile = "src/main/java/resources/pacchi.csv";
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

    public static List<TransitoPacco> getListPacchiTransitoFromCsv() {
        String csvFile = "src/main/java/resources/pacchiTransito.csv";

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

    public static void savePacchiToCsv(List<Pacco> Pacco)  {
        FileWriter writer = null;
        try {
            writer = new FileWriter("src/main/java/resources/pacchi.csv");
            StringBuilder line = new StringBuilder();
            for (Pacco sample : Pacco) {
                line.append(sample.toString());
                line.append("\n");
            }
            writer.write(line.toString());
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TransitoPacco movePacco(int paccoId){
        Pacco pacco = null;
        for (Pacco p : listPacchi) {
            if (p.getId() == paccoId) {
                pacco = p;
            }
        }
        //se diverso da null vuoldire che il pacco e' ancora in un centro di accettazione
        if (pacco != null) {
            TransitoPacco transitoPacco = new TransitoPacco(pacco.getId(), );



        }else {

        }

        return pacco;
    }
        for (TransitoPacco p : listTransitoPacchi) {
            if (p.getId() == paccoId) {
                p.setInTransito(true);
                return p;
            }
        }
    }

    public boolean isIdUsed(int id) {

        if(listTransitoPacchi != null) {
        for (TransitoPacco p : listTransitoPacchi) {
            if (p.getId() == id) {
                return true;
            }
        }}
        return false;
    }

    public List<Pacco> getListPacchi() {
        return listPacchi;
    }

    public void setListPacchi(List<Pacco> listPacchi) {
        this.listPacchi = listPacchi;
    }

    public List<TransitoPacco> getListTransitoPacchi() {
        return listTransitoPacchi;
    }

    public void addPacco(Pacco p) {
        listPacchi.add(p);
        savePacchiToCsv(listPacchi);
    }
    public void addTransitoPacco(TransitoPacco p) {
        listTransitoPacchi.add(p);
    }
    public void setListTransitoPacchi(List<TransitoPacco> listTransitoPacchi) {
        this.listTransitoPacchi = listTransitoPacchi;
    }


}