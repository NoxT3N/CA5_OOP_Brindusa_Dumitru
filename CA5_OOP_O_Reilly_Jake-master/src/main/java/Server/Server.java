package Server;

//File started by Brindusa Dumitru

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    final int SERVER_PORT_NUMBER = 8888;

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public void start(){
        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        try{
            serverSocket = new ServerSocket(SERVER_PORT_NUMBER);
            System.out.println("Server has started");
            int clientNr = 0;

            while(true){
                System.out.println("Server: Listening/waiting for connections on port ..." + SERVER_PORT_NUMBER);
                // The .accept() method is a blocking method that waits for a client to connect
                clientSocket = serverSocket.accept();
                clientNr++;

                System.out.println("Server: Listening for connections on port ..." + SERVER_PORT_NUMBER);
                System.out.println("Server: Client " + clientNr + " has connected.");
                System.out.println("Server: Port number of remote client: " + clientSocket.getPort());
                System.out.println("Server: Port number of the socket used to talk with client " + clientSocket.getLocalPort());

                Thread t = new Thread(new ClientHandler(clientSocket,clientNr));
                t.start();

                System.out.println("Server: ClientHandler started in thread " + t.getName() + " for client " + clientNr + ". ");

            }

        }
        catch (IOException e){
            System.out.println(e);
        }
        finally {
            try{
                if(clientSocket != null){
                    serverSocket.close();
                }
            } catch (IOException e){
                System.out.println(e);
            }
            try{
                if(serverSocket != null){
                    serverSocket.close();
                }
            }catch (IOException e){
                System.out.println(e);
            }
        }
        System.out.println("Server: Server exiting");
    }


}
