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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCentroAccettazione() {
        return centroAccettazione;
    }

    public void setCentroAccettazione(String centroAccettazione) {
        this.centroAccettazione = centroAccettazione;
    }

    public String getDestinazione() {
        return destinazione;
    }

    public void setDestinazione(String destinazione) {
        this.destinazione = destinazione;
    }

    public String getDataAccettazione() {
        return dataAccettazione;
    }

    public void setDataAccettazione(String dataAccettazione) {
        this.dataAccettazione = dataAccettazione;
    }


}
