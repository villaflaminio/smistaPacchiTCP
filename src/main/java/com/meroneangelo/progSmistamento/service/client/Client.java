package main.java.com.meroneangelo.progSmistamento.service.client;

import main.java.com.meroneangelo.progSmistamento.service.ComuniService;

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

                String userInput = input.nextLine();

                switch (userInput) {
                    case "a":
                        System.out.println("inserire il cap del centro di accettazione");
                        String capAccettazione = input.nextLine();
                        System.out.println("Hai selezionato il centro di " + comuniService.getCentroAccettazione(capAccettazione));

                        System.out.println("inserire il cap in cui inviare il pacco");
                        String capDestinazione = input.nextLine();
                        System.out.println("");

                        String sendToServer = capAccettazione + ";" + capDestinazione;
                        ;
                        messageToServer.writeUTF("invia");
                        messageToServer.writeUTF("invia");
                        messageToServer.writeUTF(sendToServer);

                        System.out.println(replyFromServer.readUTF());
                        break;
                    case "b":
                        System.out.println("lettura ");
                        messageToServer.writeUTF(userInput.toLowerCase());
                        messageToServer.writeUTF(userInput.toLowerCase());
                        File file = null;
                        String clientIP = null;

                        String data = replyFromServer.readUTF();
                        if (data.equals("No file found with such connection port name from server!")) {
                            System.out.println(data + "\n");

                            userInput = "close";

                            messageToServer.writeUTF(userInput.toLowerCase());
                            messageToServer.writeUTF(userInput.toLowerCase());

                            System.out.println("Closing this connection : " + clientSocket);
                            replyFromServer.close();
                            messageToServer.close();
                            clientSocket.close();
                            System.out.println("Connection closed");
                            break;
                        } else {
                            clientIP = clientSocket.getLocalPort() + "_" + clientSocket.getPort();
                            clientIP += ".txt";
                            file = new File(clientIP);
                            System.out.println("Data Read from Server: " + replyFromServer.readUTF());
                        }


                        try {
                            FileWriter fileWriter = new FileWriter(file);
                            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                            PrintWriter printWriter = new PrintWriter(bufferedWriter);
                            printWriter.print(data);

                            printWriter.close();
                            bufferedWriter.close();
                            fileWriter.close();
                            System.out.println("File with name: " + clientIP + " saved on client side. \n");
                        } catch (Exception e) {
                            System.out.println("Cannot write file");
                        }
                        break;
                    case "c":
                        System.out.println("Write the name of the file to save: ");
                        break;
                    case "d":
                        System.out.println("Write the name of the file to read: ");
                        break;
                    case "e":
                        messageToServer.writeUTF(userInput.toLowerCase());
                        messageToServer.writeUTF(userInput.toLowerCase());

                        System.out.println("Closing this connection : " + clientSocket);
                        replyFromServer.close();
                        messageToServer.close();
                        clientSocket.close();
                        System.out.println("Connection closed");
                        break;
                    default:
                        System.out.println("Write Save to save data \n Write Read to read data \n Enter close to terminate connection \n");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
