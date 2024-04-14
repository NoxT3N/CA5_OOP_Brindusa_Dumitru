package Client;

//File started by Brindusa Dumitru

import BusinessObjects.JsonConverter;
import DTOs.Instrument;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Client {
    JsonConverter jc = JsonConverter.getInstance();
    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    public void start(){
        try(
                Socket socket = new Socket("localhost", 8888);
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

                    System.out.println("Feat 9");

                }
                else if(request.startsWith("add")){
                    //Feature 11 – “Add an Entity”
                    //Jake

                }
                else if(request.startsWith("del")){

                }
                else if(request.equals("imgs")){

                }
                else if(request.equals("exit")){

                }
                else{
                    System.out.println("Please type in a valid command");
                }

            }while(true);

        }catch (IOException e){
            System.out.println("Client error: "+e);
        }
    }
}
