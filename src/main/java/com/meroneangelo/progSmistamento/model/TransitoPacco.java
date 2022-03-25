package main.java.com.meroneangelo.progSmistamento.model;

import java.util.Date;

public class TransitoPacco {
    private int id;
    private int sequence;
    private String centroSmistamentoCorrente;
    private String centroSmistamentoDestinazione;
    private String dataMovimento;
    private Boolean isArrivato;
    private String centroAccettazione;

    public TransitoPacco(int id,int sequence, String centroSmistamentoCorrente, String centroSmistamentoDestinazione,String centroAccettazione, String dataMovimento , Boolean isArrivato) {
        this.id = id;
        this.sequence = sequence;
        this.centroAccettazione = centroAccettazione;
        this.centroSmistamentoCorrente = centroSmistamentoCorrente;
        this.centroSmistamentoDestinazione = centroSmistamentoDestinazione;
        this.dataMovimento = dataMovimento;
        this.isArrivato = isArrivato;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCentroSmistamentoCorrente() {
        return centroSmistamentoCorrente;
    }

    public void setCentroSmistamentoCorrente(String centroSmistamentoCorrente) {
        this.centroSmistamentoCorrente = centroSmistamentoCorrente;
    }

    public String getCentroSmistamentoDestinazione() {
        return centroSmistamentoDestinazione;
    }

    public void setCentroSmistamentoDestinazione(String centroSmistamentoDestinazione) {
        this.centroSmistamentoDestinazione = centroSmistamentoDestinazione;
    }

    public String getDataMovimento() {
        return dataMovimento;
    }

    public void setDataMovimento(String dataMovimento) {
        this.dataMovimento = dataMovimento;
    }

    public Boolean getArrivato() {
        return isArrivato;
    }

    public void setArrivato(Boolean arrivato) {
        isArrivato = arrivato;
    }

    public int getSequence() {
        return sequence;
    }

    public String getCentroAccettazione() {
        return centroAccettazione;
    }

    public void setCentroAccettazione(String centroAccettazione) {
        this.centroAccettazione = centroAccettazione;
    }

    @Override
    public String toString() {
        return id + ";" + sequence + ";" + centroSmistamentoCorrente + ";" + centroSmistamentoDestinazione + ";" + dataMovimento + ";" + isArrivato;
    }
}
