package Server;

import algorithms.mazeGenerators.Maze;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

// this 2 imports will help us use thread pool
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class Server implements Runnable{
    private int port;
    private int listeningIntervalMS;
    private IServerStrategy strategy;
    private volatile boolean stop;
    private ExecutorService ThreadPool;
    private Configurations configurations;


    public Server(int port, int listeningIntervalMS, IServerStrategy strategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;
        // creating singleton of configuration:
        this.configurations = Configurations.getInstance();
        this.ThreadPool = Executors.newFixedThreadPool(this.configurations.getThread_pool_size()); // will help us manage threads
    }

    public void start(){
        Thread serverThread = new Thread(() -> {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);
            System.out.println("Starting server at port = " + port);

            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client accepted: " + clientSocket.toString());
                    // This thread will handle the new Client
                    ThreadPool.submit(()->{handleClient(clientSocket);});

                } catch (SocketTimeoutException e){
                    System.out.println("Socket timeout");
                }
            }
            serverSocket.close();
            this.ThreadPool.close();
            return;
        }
        catch (IOException e) {
            System.out.println("IOException");
        }
        });
        if(!stop)
            serverThread.start();
    }

    private void handleClient(Socket clientSocket) {
        try {
            strategy.serverstrategy(clientSocket.getInputStream(), clientSocket.getOutputStream(),this.configurations);
            System.out.println("Done handling client: " + clientSocket.toString());
            clientSocket.close();
        } catch (IOException e){
            System.out.println("IOException");
        }
    }

    public void stop(){
        System.out.println("Stopping server...");
        stop = true;
    }

    @Override
    public void run() { }

}
