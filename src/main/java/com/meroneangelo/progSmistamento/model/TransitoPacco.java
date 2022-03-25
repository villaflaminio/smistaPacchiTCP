package main.java.com.meroneangelo.progSmistamento.model;

import java.util.Date;

public class TransitoPacco {
    private int id;
    private String centroSmistamentoCorrente;
    private String centroSmistamentoDestinazione;
    private String dataMovimento;
    private Boolean isArrivato;

    public TransitoPacco(int id, String centroSmistamentoCorrente, String centroSmistamentoDestinazione, String dataMovimento , Boolean isArrivato) {
        this.id = id;
        this.centroSmistamentoCorrente = centroSmistamentoCorrente;
        this.centroSmistamentoDestinazione = centroSmistamentoDestinazione;
        this.dataMovimento = dataMovimento;
        this.isArrivato = isArrivato;
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
}
