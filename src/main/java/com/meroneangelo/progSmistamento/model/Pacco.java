package main.java.com.meroneangelo.progSmistamento.model;

public class Pacco {
    private int id;
    private String centroAccettazione;
    private String destinazione;
    private String dataAccettazione;

    public Pacco(int id, String centroAccettazione, String destinazione, String dataAccettazione) {
        this.id = id;
        this.centroAccettazione = centroAccettazione;
        this.destinazione = destinazione;
        this.dataAccettazione = dataAccettazione;
    }
}
