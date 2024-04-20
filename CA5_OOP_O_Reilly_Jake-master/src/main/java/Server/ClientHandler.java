package Server;

//File started by Brindusa Dumitru

import BusinessObjects.JsonConverter;
import Server.DAOs.MySqlInstrumentDao;
import DTOs.Instrument;
import Server.Exceptions.DaoException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {
    BufferedReader socketReader; //allows server to receive messages from the client
    PrintWriter socketWriter; //allows server to send messages to the client
    Socket clientSocket;
    //Socket serverSocket;
    final int clientNr; //ID nr assigned to this client
    MySqlInstrumentDao InstrumentDao;
    JsonConverter jc;

    private static DataOutputStream dOut = null;
    private static DataInputStream dIn = null;

    public ClientHandler(Socket clientSocket, int clientNr) throws IOException {
        this.InstrumentDao = new MySqlInstrumentDao();
        this.jc = JsonConverter.getInstance();

        this.clientSocket = clientSocket;
        this.clientNr = clientNr;

        try{
            this.socketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.socketWriter = new PrintWriter(clientSocket.getOutputStream(),true);
            //this.serverSocket = new ServerSocket(8888);
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
                    //System.out.println("Hi");
                    //try (ServerSocket sSocket = new ServerSocket(8888)) {
                    String[] names = {
                            "Fender Vintera 50s Telecaster MN, Sonic Blue",
                            "Epiphone Les Paul Muse, Purple Passion Metallic",
                            "Ibanez FRH10N-RGF, Rose Gold Metallic Flat",
                            "Yamaha Stage Custom Hip 20 inch 4pc Shell Pack, Matte Surf Green",
                            "Roland A-49 MIDI Controller Keyboard, White",
                            "Stagg 12 Key Rainbow Xylophone, With Mallets",
                            "Odyssey OBE1200 Premiere Junior Oboe",
                            "Casio SA 50 Mini Portable Keyboard",
                            "Fender Blues Deluxe Harmonica, A",
                            "Schecter J-4 Bass, Sea Foam Green"
                    };

                    String response = jc.stringArrayToJSON(names);
                    socketWriter.println(response);

                    String json = socketReader.readLine();
                    int choice = jc.JSONtoInteger(json);

                    try {
                        //Socket cSocket = sSocket.accept();
                        dOut = new DataOutputStream(clientSocket.getOutputStream());
                        dIn = new DataInputStream(clientSocket.getInputStream());

                        //int choice = socketReader.read();

                        sendFile("ServerImages/" + choice + ".jpg");

                        //dOut.close();
                        //dIn.close();
                        //cSocket.close();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if(request.equals("exit")){

                }
            }
        }catch (IOException e){
            System.out.println(e);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
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

    private static void sendFile(String path) throws Exception {
        System.out.println("Sending file to client");
        int bytes = 0;
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);

        dOut.writeLong(file.length());

        byte[] buffer = new byte[4 * 1024]; //4kb buffer

        while ((bytes = fis.read(buffer)) != -1) {
            dOut.write(buffer, 0, bytes);
            dOut.flush();
        }

        fis.close();
    }
}
