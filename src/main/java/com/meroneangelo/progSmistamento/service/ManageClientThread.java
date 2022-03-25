package main.java.com.meroneangelo.progSmistamento.service;

import main.java.com.meroneangelo.progSmistamento.model.Pacco;
import main.java.com.meroneangelo.progSmistamento.model.TransitoPacco;
import main.java.com.meroneangelo.progSmistamento.service.server.Server;
import main.java.com.meroneangelo.progSmistamento.service.server.ServerService;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class ManageClientThread implements Runnable {

    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    ComuniService comuniService = ComuniService.getInstance();
    ServerService serverService = ServerService.getInstance();


    public ManageClientThread(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
        this.socket = socket;
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;

    }

    @Override
    public void run() {
        String input, output;
        String clientIP = this.socket.getPort() + ".txt";
        File file = new File(clientIP);
        serverService = ServerService.getInstance();


        while (true) {
            try {

                input = dataInputStream.readUTF();
                if (input.toLowerCase().equals("close")) {
                    System.out.println("Closing this connection : " + this.socket);
                    this.socket.close();
                    Server.connectionCount--;
                    System.out.println("Connection closed");

                    System.out.println("Active connections: " + Server.connectionCount);
                    break;
                } else {
                    if (input.equals("invia")) {
                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            do {
                                input = dataInputStream.readUTF();
                            } while (input.equals("invia"));

                            String[] parts = input.split(";");
                            String capPartenza = parts[0]; // 004
                            String capArrivo = parts[1]; // 034556
                            int id;

                            //verifico che non esistano già delle spedizioni con questo id
                            do {
                                id = (int) (Math.random() * 10000) + 1;

                            } while (serverService.isIdUsed(id));

                            //genero un numero random da 1 a 1000
                            LocalDateTime today = LocalDateTime.now();
                            String dataOra = formatter.format(today);

                            String response = "il pacco " + id + " è stato inviato da " +
                                    comuniService.getCentroAccettazione(capPartenza) + " a " +
                                    comuniService.getCentroAccettazione(capArrivo) + " il " + dataOra;

                            Pacco nuovoPacco = new Pacco(id, capPartenza, capArrivo, dataOra);
                            serverService.addPacco(nuovoPacco);

                            dataOutputStream.writeUTF(response + " " + this.socket.getRemoteSocketAddress() + "\n\n");
                        } catch (Exception e) {
                            System.out.println("Cannot write file");
                        }
                    } else if (input.equals("transito")) {
                        String response = "";
                        try {
                            do {
                                input = dataInputStream.readUTF();
                            } while (input.equals("transito"));


                            List<TransitoPacco> tList = serverService.movePacco(Integer.parseInt(input), false);
                            for (TransitoPacco t : tList) {
                                response += ("il pacco " + t.getId() + " è stato inviato da " +
                                        comuniService.getCentroAccettazione(t.getCentroSmistamentoCorrente()) + " a " +
                                        comuniService.getCentroAccettazione(t.getCentroSmistamentoDestinazione()) + " il " + t.getDataMovimento());
                                response += "\n";
                            }

                            dataOutputStream.writeUTF(response + " " + this.socket.getRemoteSocketAddress() + "\n\n");


                        } catch (Exception e) {
                            System.out.println("errore di stream");
                        }
                    } else if (input.equals("destinazione_finale")) {
                        String response = "";
                        try {
                            do {
                                input = dataInputStream.readUTF();
                            } while (input.equals("destinazione_finale"));


                            List<TransitoPacco> tList = serverService.movePacco(Integer.parseInt(input), true);
                            for (TransitoPacco t : tList) {
                                response += ("il pacco " + t.getId() + " è stato inviato da " +
                                        comuniService.getCentroAccettazione(t.getCentroSmistamentoCorrente()) + " a " +
                                        comuniService.getCentroAccettazione(t.getCentroSmistamentoDestinazione()) + " il " + t.getDataMovimento());
                                response += "\n";

                            }

                            dataOutputStream.writeUTF(response + " " + this.socket.getRemoteSocketAddress() + "\n\n");


                        } catch (Exception e) {
                            System.out.println("errore di stream");
                        }
                    } else if (input.equals("storico")) {
                        String response = "";
                        try {
                            do {
                                input = dataInputStream.readUTF();
                            } while (input.equals("storico"));


                            List<TransitoPacco> tList = serverService.getStorico(Integer.parseInt(input));
                            for (TransitoPacco t : tList) {
                                response += ("il pacco " + t.getId() + " è stato inviato da " +
                                        comuniService.getCentroAccettazione(t.getCentroSmistamentoCorrente()) + " a " +
                                        comuniService.getCentroAccettazione(t.getCentroSmistamentoDestinazione()) + " il " + t.getDataMovimento());
                                response += "\n";

                            }

                            dataOutputStream.writeUTF(response + " " + this.socket.getRemoteSocketAddress() + "\n\n");


                        } catch (Exception e) {
                            System.out.println("errore di stream");
                        }
                    } else if (input.equals("accettazione")) {
                        String response = "";
                        try {

                            List<Pacco> tList = serverService.getListPacchi();
                            for (Pacco p : tList) {
                                response += ("il pacco " + p.getId() + " è stato inviato da " +
                                        comuniService.getCentroAccettazione(p.getCentroAccettazione()) + " a " +
                                        comuniService.getCentroAccettazione(p.getDestinazione()) + " il " + p.getDataAccettazione());
                                response += "\n";

                            }

                            dataOutputStream.writeUTF(response + " " + this.socket.getRemoteSocketAddress() + "\n\n");


                        } catch (Exception e) {
                            System.out.println("errore di stream");
                        }
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
