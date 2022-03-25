package main.java.com.meroneangelo.progSmistamento.service.client;

import main.java.com.meroneangelo.progSmistamento.service.ComuniService;

import javax.jws.soap.SOAPBinding;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client extends Thread {
    public static void main(String[] args) throws Exception {
       ComuniService comuniService =  ComuniService.getInstance();

        try {
            Socket clientSocket = new Socket("localhost", 4154);
            System.out.println("Client " + clientSocket.getLocalAddress() + " is active.");

            DataOutputStream messageToServer = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream replyFromServer = new DataInputStream(clientSocket.getInputStream());
            Scanner input = new Scanner(System.in);
            System.out.println(replyFromServer.readUTF());

            while (true) {
                System.out.println("a) invia un nuovo pacco");
                System.out.println("b) muovi un pacco");
                System.out.println("c) visualizza lo storico");
                System.out.println("d) porta il pacco a destinazione");
                System.out.println("e) visualizza tutti i pacchi ancora nelle stazioni di accettazione");
                System.out.println("x) Disconnetti");

                String userInput = input.nextLine();

                switch (userInput) {
                    case "a":
                        System.out.println("inserire il cap del centro di accettazione");
                        String capAccettazione = input.nextLine();
                        if(comuniService.getCentroAccettazione(capAccettazione) == null) {
                           throw new Exception("cap non trovato");
                        }
                        System.out.println("Hai selezionato il centro di " + comuniService.getCentroAccettazione(capAccettazione));
                        System.out.println("inserire il cap in cui inviare il pacco");
                        String capDestinazione = input.nextLine();
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
                        System.out.println("Data Read from Server: " + replyFromServer.readUTF());

                        break;

                    case "c":
                        System.out.println("Write the name of the file to read: ");
                        break;
                    case "d":
                        System.out.println("inserire il numero del pacco da portare a destinazione");
                        idPacco = input.nextInt();

                        messageToServer.writeUTF("destinazione_finale");
                        messageToServer.writeUTF("destinazione_finale");
                        messageToServer.writeUTF(String.valueOf(idPacco));
                        System.out.println("Data Read from Server: " + replyFromServer.readUTF());
                        break;
                    case "e":
                        messageToServer.writeUTF("accettazione");
                        messageToServer.writeUTF("accettazione");
                        System.out.println("Data Read from Server: " + replyFromServer.readUTF());
                        break;
                    case "x":
                        messageToServer.writeUTF(userInput.toLowerCase());
                        messageToServer.writeUTF(userInput.toLowerCase());

                        System.out.println("Closing this connection : " + clientSocket);
                        replyFromServer.close();
                        messageToServer.close();
                        clientSocket.close();
                        System.out.println("Connection closed");
                        break;;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
