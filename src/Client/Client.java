package Client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

public class Client {
    private InetAddress serverIP;
    private int serverPort;
    private IClientStrategy strategy;
    public Maze maze;
    public Solution solution;

    public Client(InetAddress serverIP, int serverPort, IClientStrategy strategy) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.strategy = strategy;
    }

    public void communicateWithServer(){
        try(Socket serverSocket = new Socket(serverIP, serverPort)){
            System.out.println("connected to server - IP = " + serverIP + ", Port = " + serverPort);
            strategy.clientStrategy(serverSocket.getInputStream(), serverSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
