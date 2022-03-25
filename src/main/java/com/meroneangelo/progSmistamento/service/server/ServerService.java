package main.java.com.meroneangelo.progSmistamento.service.server;

import main.java.com.meroneangelo.progSmistamento.model.Pacco;
import main.java.com.meroneangelo.progSmistamento.model.TransitoPacco;
import main.java.com.meroneangelo.progSmistamento.service.ComuniService;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class ServerService {
    //Singleton pattern
    private static ServerService instance;
    private List<Pacco> listPacchi;
    private List<TransitoPacco> listTransitoPacchi;
    private ComuniService comuniService = ComuniService.getInstance();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    Random rand = new Random();

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
                TransitoPacco p = new TransitoPacco(Integer.parseInt(paccoData[0]), Integer.parseInt(paccoData[1]),paccoData[2], paccoData[3] ,paccoData[4], paccoData[5],Boolean.parseBoolean(paccoData[6]));

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

    public static void saveTransitoPaccoToCsv(List<TransitoPacco> transitoPacco)  {
        FileWriter writer = null;
        try {
            writer = new FileWriter("src/main/java/resources/pacchiTransito.csv");
            StringBuilder line = new StringBuilder();
            for (TransitoPacco sample : transitoPacco) {
                line.append(sample.toString());
                line.append("\n");
            }
            writer.write(line.toString());
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public List<TransitoPacco> getStorico(int paccoId) {
        Pacco pacco = null;
        List<TransitoPacco> spostamenti = new ArrayList<>();

        for (Pacco p : listPacchi) {
            if (p.getId() == paccoId) {
                pacco = p;
            }
            LocalDateTime today = LocalDateTime.now();
            String dataOra = formatter.format(today);
            TransitoPacco t = new TransitoPacco(pacco.getId(),1,p.getCentroAccettazione(),pacco.getDestinazione() , pacco.getCentroAccettazione(), dataOra,false);
        spostamenti.add(t);
        }
        if (pacco == null) {
            for (TransitoPacco p : listTransitoPacchi) {
                if (p.getId() == paccoId) {
                    spostamenti.add(p);
                }
            }
        }
        return spostamenti;
    }


    public List<TransitoPacco> movePacco(int paccoId , boolean destinazioneDefinitiva) {
        TransitoPacco transitoPacco ;
        HashMap<String, String> smistamenti = comuniService.getCentriSmistamento();
        List<TransitoPacco> sortedList = null;
        Pacco pacco = null;
        for (Pacco p : listPacchi) {
            if (p.getId() == paccoId) {
                pacco = p;
            }
        }
        //se diverso da null vuoldire che il pacco e' ancora in un centro di accettazione
        if (pacco != null) {
            LocalDateTime today = LocalDateTime.now();
            String dataOra = formatter.format(today);
            Object[] crunchifyKeys =smistamenti.keySet().toArray();
            Object key = crunchifyKeys[new Random().nextInt(crunchifyKeys.length)];


            boolean isArrivato = pacco.getDestinazione().equals(key);
            if (isArrivato) {
                System.out.println("Pacco " + pacco.getId() + " arrivato a destinazione");
                return null;
            }

             transitoPacco = new TransitoPacco(pacco.getId(),1,(String) key,pacco.getDestinazione() , pacco.getCentroAccettazione(), dataOra,isArrivato);
             listPacchi.remove(pacco);
             savePacchiToCsv(listPacchi);
            sortedList.add(transitoPacco);

        }else {
            List<TransitoPacco> movimentiPacco = new ArrayList<>();
            for (TransitoPacco p : listTransitoPacchi) {
                if (p.getId() == paccoId) {
                    movimentiPacco.add(p);
                }
            }
            if(movimentiPacco.size() == 0){
                throw new RuntimeException("Pacco non trovato");
            }

            sortedList = movimentiPacco.stream()
                    .sorted(Comparator.comparingInt(TransitoPacco::getSequence))
                    .collect(Collectors.toList());
            TransitoPacco lastTransitoPacco = sortedList.get(sortedList.size()-1);
            LocalDateTime today = LocalDateTime.now();
            String dataOra = formatter.format(today);
            Object[] crunchifyKeys =smistamenti.keySet().toArray();
            Object key = crunchifyKeys[new Random().nextInt(crunchifyKeys.length)];
            boolean isArrivato = lastTransitoPacco.getCentroSmistamentoDestinazione().equals(key);

            if (lastTransitoPacco.getArrivato()) {
                System.out.println("Pacco " + lastTransitoPacco.getId() + " arrivato a destinazione");
                return null;
            }else if(destinazioneDefinitiva){
                TransitoPacco transitoPacco1 = new TransitoPacco(lastTransitoPacco.getId(),sortedList.size(),lastTransitoPacco.getCentroSmistamentoDestinazione(), lastTransitoPacco.getCentroSmistamentoDestinazione(), lastTransitoPacco.getCentroAccettazione(),dataOra,true);
                sortedList.add(transitoPacco1);

                listTransitoPacchi.add(transitoPacco1);
            }

             transitoPacco = new TransitoPacco(lastTransitoPacco.getId(),sortedList.size(), (String) key,lastTransitoPacco.getCentroSmistamentoDestinazione(), lastTransitoPacco.getCentroAccettazione(),dataOra,isArrivato);
            sortedList.add(transitoPacco);

            listTransitoPacchi.add(transitoPacco);
            saveTransitoPaccoToCsv(listTransitoPacchi);
            return sortedList;
        }
        listTransitoPacchi.add(transitoPacco);
        saveTransitoPaccoToCsv(listTransitoPacchi);
        return sortedList;

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