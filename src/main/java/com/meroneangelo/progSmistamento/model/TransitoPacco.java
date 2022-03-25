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
}
