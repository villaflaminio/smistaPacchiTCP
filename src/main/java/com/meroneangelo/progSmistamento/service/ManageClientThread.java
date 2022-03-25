package main.java.com.meroneangelo.progSmistamento.service;

import main.java.com.meroneangelo.progSmistamento.model.Pacco;
import main.java.com.meroneangelo.progSmistamento.service.server.Server;
import main.java.com.meroneangelo.progSmistamento.service.server.ServerService;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ManageClientThread implements Runnable {

     Socket socket;
     DataInputStream dataInputStream;
     DataOutputStream dataOutputStream;
    ComuniService comuniService =  ComuniService.getInstance();
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


        while (true)
        {
            try {

                input = dataInputStream.readUTF();
                if(input.toLowerCase().equals("close"))
                {
                    System.out.println("Closing this connection : " + this.socket);
                    this.socket.close();
                    Server.connectionCount--;
                    System.out.println("Connection closed");

                    System.out.println("Active connections: " + Server.connectionCount);
                    break;
                }
                else
                {
                    if(input.equals("invia"))
                    {
                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            do {
                                input = dataInputStream.readUTF();
                            }while (input.equals("invia"));

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
                        }

                        catch (Exception e) {
                            System.out.println("Cannot write file");
                        }
                    }
                    else if(input.equals("transito")){
                        try {
                            do {
                                input = dataInputStream.readUTF();
                            } while (input.equals("invia"));




                        }catch (Exception e) {
                            System.out.println("errore di stream");
                        }
                    }
                    else if(input.toLowerCase().equals("read"))
                    {
                        try {

                            FileReader fileReader = new FileReader(file);
                            BufferedReader bufferedReader = new BufferedReader(fileReader);
                            output = bufferedReader.readLine();
                            if(output == null)
                            {
                                dataOutputStream.writeUTF("No Information found for client: " + this.socket.getRemoteSocketAddress());
                                dataOutputStream.writeUTF("No Information found for client: " + this.socket.getRemoteSocketAddress());
                            }
                            else
                            {
                                dataOutputStream.writeUTF("Information for client: " + this.socket.getRemoteSocketAddress() + "\n" + output );
                                dataOutputStream.writeUTF("Information for client: " + this.socket.getRemoteSocketAddress() + "\n" + output );
                            }


                        } catch (Exception e) {
                            System.out.println("No file found with such connection port name from server!");
                            dataOutputStream.writeUTF("No file found with such connection port name from server!");
                            System.out.println("Closing this connection : " + this.socket);
                            this.socket.close();
                            Server.connectionCount--;
                            System.out.println("Connection closed");

                            System.out.println("Active connections: " + Server.connectionCount);
                            break;
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
