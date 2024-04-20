package Client;

//File started by Brindusa Dumitru

import BusinessObjects.JsonConverter;
import DTOs.Instrument;

import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.List;
import java.util.Scanner;

public class Client {
    JsonConverter jc = JsonConverter.getInstance();

    private static DataOutputStream dOut = null;
    private static DataInputStream dIn = null;

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    public void start(){
        try(
                Socket socket = new Socket("localhost", 8888);
                //ServerSocket serverSocket = new ServerSocket(8888);
                PrintWriter socketWriter = new PrintWriter(socket.getOutputStream(),true);
                BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ){
            System.out.println("Client message: The Client is running and has connected to the server");
            Scanner kb = new Scanner((System.in));
            String request;

            do{
                System.out.println("\nList of valid commands:\n");
                System.out.println("dp <ID>\t- Display Instrument by ID");
                System.out.println("dp all\t- Display All Instrument");
                System.out.println("add <name> <price> <type> - Add an Instrument");
                System.out.println("del <ID> - Delete Instrument By ID");
                System.out.println("imgs\t- Get Images List");
                System.out.println("exit\t- Stop client\n");

                request = kb.nextLine().toLowerCase().trim();
                socketWriter.println(request); //send request to server

                if(request.equals("dp all")){
                    //Feature 10 - “Display all Entities”
                    //Brindusa Dumitru

                    String jsonList = socketReader.readLine();
                    List<Instrument> instruments = jc.JSONtoInstrumentList(jsonList);

                    System.out.printf("%2s\t%-60s\t%-10s\t%-10s\n","ID","Name","Price","Type");
                    for (Instrument i:instruments) {
                        System.out.printf("%d\t%-60s\t%-10.2f\t%-10s\n",i.getId(),i.getName(),i.getPrice(),i.getType());
                    }

                }
                else if(request.startsWith("dp")){
                    //Feature 9 - “Display Entity by Id
                    //Ciara
                    String json = socketReader.readLine();
                    Instrument instrument = jc.JSONtoInstrument(json);

                    System.out.println("ID: " + instrument.getId());
                    System.out.println("Name: " + instrument.getName());
                    System.out.println("Price: " + instrument.getPrice());
                    System.out.println("Type: " + instrument.getType());
                }
                else if(request.startsWith("add")){
                    //Feature 11 – “Add an Entity”
                    //Jake
                    String response = socketReader.readLine();
                    System.out.println(response);
                }
                else if(request.startsWith("del")){
                    String response = socketReader.readLine();
                    System.out.println("Client: Response from the server: "+response);
                }
                else if(request.equals("imgs")){
                    //Feature 13 - "Get Images List"
                    //Ciara
                    String jsonArray = socketReader.readLine();
                    String[] names = jc.JSONtoStringArray(jsonArray);

                    for (int i = 1; i < names.length+1; i++) {
                        System.out.println(i + ". " + names[i-1]);
                    }

                    int choice = kb.nextInt();
                    String response = jc.integerToJSON(choice);
                    socketWriter.println(response);

                    try {
                        //Socket clientSocket = socket.accept();
                        //System.out.println("Connected");
                        dIn = new DataInputStream(socket.getInputStream());
                        dOut = new DataOutputStream(socket.getOutputStream());
                        receiveFile("ClientImages/" + names[choice-1] + ".jpg");

                        //dIn.close();
                        //dOut.close();
                    } catch (Exception e) {
                      e.printStackTrace();
                    }
                }
                else if(request.equals("exit")){
                    String respone = socketReader.readLine();
                    System.out.println("Client: Response from the server: "+respone);
                    break;
                }
                else{
                    System.out.println("Please type in a valid command");
                }

            }while(true);

        }catch (IOException e){
            System.out.println("Client error: "+e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void receiveFile(String fileName) throws Exception {
        //System.out.println("Hello");
        FileOutputStream fos = new FileOutputStream(fileName);

        long bytes_remaining = dIn.readLong();
        System.out.println("Server: file size in bytes = " + bytes_remaining);

        byte[] buffer = new byte[4 * 1024];

        System.out.println("Server: bytes remaining to be read from socket: ");
        int bytes_read = 0;

        while (bytes_remaining > 0 && (bytes_read = dIn.read(buffer, 0, (int)Math.min(buffer.length, bytes_remaining))) != -1) {
            fos.write(buffer, 0, bytes_read);

            bytes_remaining = bytes_remaining - bytes_read;

            System.out.println(bytes_remaining + ", ");
        }

        System.out.println("File received");

        fos.close();
    }
}
