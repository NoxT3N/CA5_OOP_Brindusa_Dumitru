package Server;

//File started by Brindusa Dumitru

import BusinessObjects.JsonConverter;
import Server.DAOs.MySqlInstrumentDao;
import DTOs.Instrument;
import Server.Exceptions.DaoException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {
    BufferedReader socketReader; //allows server to receive messages from the client
    PrintWriter socketWriter; //allows server to send messages to the client
    Socket clientSocket;
    final int clientNr; //ID nr assigned to this client
    MySqlInstrumentDao InstrumentDao;
    JsonConverter jc;

    public ClientHandler(Socket clientSocket, int clientNr) {
        this.InstrumentDao = new MySqlInstrumentDao();
        this.jc = JsonConverter.getInstance();

        this.clientSocket = clientSocket;
        this.clientNr = clientNr;

        try{
            this.socketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.socketWriter = new PrintWriter(clientSocket.getOutputStream(),true);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        //implementing protocol
        try{
            String request;
            while((request = socketReader.readLine()) != null){
                System.out.println("Server: (ClientHandler): Read command from client " + clientNr + ": " + request);

                if(request.equals("dp all")){
                    List<Instrument> instruments = InstrumentDao.getAllInstruments();
                    String response = jc.instrumentListToJSON(instruments);

                    socketWriter.println(response);
                    System.out.println("Server: List of all instruments sent to the client");
                }
                else if(request.startsWith("dp")){
                    String[] pieces = request.split(" ");
                    Instrument instrument = InstrumentDao.getInstrumentById(Integer.parseInt(pieces[1]));

                    String response = jc.instrumentToJSON(instrument);

                    socketWriter.println(response);
                    System.out.println("Server: Instrument with ID " + pieces[1] + " sent to the client");
                }
                else if(request.startsWith("add")){
                    String[] pieces = request.split(" ");

                    Instrument instrument = new Instrument();

                    // Error catching, if at any point the try block fails, the catch runs
                    try {
                        instrument.setName(pieces[1]);
                        instrument.setPrice(Double.parseDouble(pieces[2]));
                        instrument.setType(pieces[3]);

                        InstrumentDao.insertInstrument(instrument);
                    } catch (IllegalArgumentException e) {
                        String response = "Error when inserting Instrument.";
                        socketWriter.println(response);
                        System.out.println("Server: Error when inserting instrument");
                        e.printStackTrace();
                    }

                    String response = "Successfully inserted Instrument.";

                    socketWriter.println(response);
                    System.out.println("Server: Instrument with ID " + pieces[1] + " sent to the client");
                }
                else if(request.startsWith("del")){

                }
                else if(request.equals("imgs")){

                }
                else if(request.equals("exit")){

                }
            }
        }catch (IOException e){
            System.out.println(e);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        } finally {
            this.socketWriter.close();
            try{
                this.socketReader.close();
                this.clientSocket.close();
            }catch (IOException e){
                System.out.println(e);
            }
        }
        System.out.println("Server: (ClientHandler): Handler for Client " + clientNr+ " is terminating .....");
    }
}
