package main.java.com.meroneangelo.progSmistamento.service.client;

import main.java.com.meroneangelo.progSmistamento.service.ComuniService;

import javax.jws.soap.SOAPBinding;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client extends Thread {

    /*Il client instanzia un socket e una stringa per la ricezione dei messaggi dal server
    * e una per la spedizione dei messaggi al server
    * */
    public static void main(String[] args) throws Exception {
       ComuniService comuniService =  ComuniService.getInstance();

        try {
            Socket clientSocket = new Socket("localhost", 4154);
            System.out.println("Client " + clientSocket.getLocalAddress() + " is active.");

            DataOutputStream messageToServer = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream replyFromServer = new DataInputStream(clientSocket.getInputStream());
            Scanner input = new Scanner(System.in);
            System.out.println(replyFromServer.readUTF());

            //Inizio la comunicazione con il server
            //Il client riceve un messaggio dal server e lo stampa a schermo
            //Il client inserisce un messaggio e lo spedisce al server
            while (true) {
                System.out.println("a) invia un nuovo pacco");
                System.out.println("b) muovi un pacco");
                System.out.println("c) visualizza lo storico");
                System.out.println("d) porta il pacco a destinazione");
                System.out.println("e) visualizza tutti i pacchi ancora nelle stazioni di accettazione");
                System.out.println("x) Disconnetti");

                String userInput = input.nextLine();

                //Invia il messaggio al server secondo le possibilita sopra descritte
                switch (userInput) {
                    case "a":
                        System.out.println("inserire il cap del centro di accettazione");
                        String capAccettazione = input.nextLine();
                        if(comuniService.getCentroAccettazione(capAccettazione) == null) {
                           throw new Exception("cap non trovato");
                        }
                        System.out.println("Hai selezionato il centro di " + comuniService.getCentroAccettazione(capAccettazione));
                        String capDestinazione = "";
                        do {
                            System.out.println("inserire il cap in cui inviare il pacco (deve essere diverso dal cap di accettazione)");
                            capDestinazione = input.nextLine();
                        }while(!capDestinazione.matches("[0-9]{5}") && capDestinazione.equals(capAccettazione));
                        System.out.println("Hai selezionato il centro di " + comuniService.getCentroSmistamento(capDestinazione));

                        if(comuniService.getCentroSmistamento(capDestinazione) == null) {
                            throw new Exception("cap non trovato");
                        }
                        System.out.println("");

                        String sendToServer = capAccettazione + ";" + capDestinazione;
                        messageToServer.writeUTF("invia");
                        messageToServer.writeUTF("invia");
                        messageToServer.writeUTF(sendToServer);

                        System.out.println(replyFromServer.readUTF());
                        break;
                    case "b":
                        System.out.println("inserire il numero del pacco");
                        int idPacco = input.nextInt();

                        messageToServer.writeUTF("transito");
                        messageToServer.writeUTF("transito");
                        messageToServer.writeUTF(String.valueOf(idPacco));
                        System.out.println(replyFromServer.readUTF());
                        break;
                    case "c":
                        System.out.println("inserire il numero del pacco da cercare");
                        idPacco = input.nextInt();

                        messageToServer.writeUTF("storico");
                        messageToServer.writeUTF("storico");
                        messageToServer.writeUTF(String.valueOf(idPacco));
                        System.out.println(replyFromServer.readUTF());
                        break;
                    case "d":
                        System.out.println("inserire il numero del pacco da portare a destinazione");
                        idPacco = input.nextInt();

                        messageToServer.writeUTF("destinazione_finale");
                        messageToServer.writeUTF("destinazione_finale");
                        messageToServer.writeUTF(String.valueOf(idPacco));
                        System.out.println(replyFromServer.readUTF());
                        break;
                    case "e":
                        messageToServer.writeUTF("accettazione");
                        messageToServer.writeUTF("accettazione");
                        System.out.println(replyFromServer.readUTF());
                        break;
                    case "x":
                        messageToServer.writeUTF(userInput.toLowerCase());
                        messageToServer.writeUTF(userInput.toLowerCase());

                        System.out.println("Closing this connection : " + clientSocket);
                        replyFromServer.close();
                        messageToServer.close();
                        clientSocket.close();
                        System.out.println("Connection closed");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
