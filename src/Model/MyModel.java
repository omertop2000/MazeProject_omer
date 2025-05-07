package Model;

import Client.Client;
import Client.IClientStrategy;
import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.search.AState;
import algorithms.search.Solution;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MyModel extends Observable implements IModel{
    private Maze maze;
    private int playerRow;
    private int playerCol;
    private Solution solution;

    public void setPlayerRow(int playerRow) {
        this.playerRow = playerRow;
    }

    public void setPlayerCol(int playerCol) {
        this.playerCol = playerCol;
    }

    @Override
    public void generateMaze(int rows, int cols) {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{rows, cols}; toServer.writeObject(mazeDimensions); //send maze dimensions to server
                        toServer.flush();
                        byte[] compressedMaze = (byte[]) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[(rows * cols) + 100 /*CHANGE SIZE ACCORDING TO YOU MAZE SIZE*/]; //allocating byte[] for the decompressed maze -
                        is.read(decompressedMaze); //Fill decompressedMaze with bytes
                        maze = new Maze(decompressedMaze);
                        playerRow = maze.getStartPosition().getRowIndex();
                        playerCol = maze.getStartPosition().getColumnIndex();
                    } catch (Exception e) { e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) { e.printStackTrace();
        }
        //we will call the server to generate the maze. we're using a client which asks to generate a maze
        setChanged();
        notifyObservers("maze generated");
        movePlayer(playerRow,playerCol);
    }



    @Override
    public Maze getMaze() {
        return maze;
    }

    @Override
    public void solveMaze() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new
                    IClientStrategy() {
                        @Override
                        public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                            try {
                                ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                                ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                                toServer.flush();
                                toServer.writeObject(maze); //send maze to server
                                toServer.flush();
                                Solution mazeSolution = (Solution) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                                ArrayList<AState> mazeSolutionSteps = mazeSolution.getSolutionPath();
                                //solution = new Solution(mazeSolutionSteps);
                                solution = new Solution(mazeSolution);
                            } catch (Exception e) { e.printStackTrace();
                            }
                        }
                    });
            client.communicateWithServer();
        } catch (UnknownHostException e) { e.printStackTrace();
        }
        //since we have the solve maze server, we will have to ask it to solve it.
        setChanged();
        notifyObservers("maze solved");
    }

    public void dragMoving(double deltaX, double deltaY){
        int rowDelta = (int)Math.round(deltaY);
        int colDelta = (int)Math.round(deltaX);

        if (rowDelta == 0 && colDelta !=0) {
            if (colDelta<0)
                updatePlayerLocation(MovementDirection.LEFT);
            else if (colDelta>0)
                updatePlayerLocation(MovementDirection.RIGHT);
        }
        else if (rowDelta != 0 && colDelta == 0) {
            if (rowDelta<0)
                updatePlayerLocation(MovementDirection.UP);
            else if (rowDelta>0)
                updatePlayerLocation(MovementDirection.DOWN);
        }
        else if (rowDelta == 1 && colDelta == 1) {
            updatePlayerLocation(MovementDirection.DOWNRIGHT);
        }
        else if (rowDelta == 1 && colDelta == -1) {
            updatePlayerLocation(MovementDirection.DOWNLEFT);
        }
        else if (rowDelta == -1 && colDelta == -1) {
            updatePlayerLocation(MovementDirection.UPLEFT);
        }
        else if (rowDelta == -1 && colDelta == 1) {
            updatePlayerLocation(MovementDirection.UPRIGHT);
        }

    }

    @Override
    public Solution getSolution() {
        return solution;
    }

    @Override
    public int getPlayerRow() {
        return playerRow;
    }

    @Override
    public int getPlayerCol() {
        return playerCol;
    }

    @Override
    public void assignObserver(Observer o) {
        this.addObserver(o);
    }

    @Override
    public void updatePlayerLocation(MovementDirection direction) {

        int[][] mazeBody = maze.getMatrix();

        switch (direction){
            case UP -> {
                if (playerRow > 0 && (mazeBody[playerRow - 1][playerCol]  == 0))
                    movePlayer(playerRow - 1, playerCol);
            }
            case UPRIGHT -> {
                if (playerRow > 0 && playerCol < mazeBody[0].length - 1 && mazeBody[playerRow - 1][playerCol+1]  == 0 && (mazeBody[playerRow - 1][playerCol]  == 0 || mazeBody[playerRow][playerCol+1]  == 0 ))
                    movePlayer(playerRow - 1, playerCol+1);
            }
            case UPLEFT -> {
                if (playerRow > 0 && playerCol > 0 && mazeBody[playerRow - 1][playerCol - 1]  == 0 && (mazeBody[playerRow - 1][playerCol]  == 0 || mazeBody[playerRow][playerCol-1]  == 0 ))
                    movePlayer(playerRow - 1, playerCol-1);
            }
            case DOWN -> {
                if (playerRow < mazeBody.length - 1 && (mazeBody[playerRow + 1][playerCol]  == 0) )
                    movePlayer(playerRow + 1, playerCol);
            }

            case DOWNRIGHT -> {
                if (playerRow < mazeBody.length - 1 && playerCol < mazeBody[0].length - 1 && mazeBody[playerRow + 1][playerCol+1]  == 0 && (mazeBody[playerRow + 1][playerCol]  == 0 || mazeBody[playerRow][playerCol+1]  == 0 ))
                    movePlayer(playerRow + 1, playerCol+1);
            }

            case DOWNLEFT -> {
                if (playerRow < mazeBody.length - 1 && playerCol > 0 && mazeBody[playerRow + 1][playerCol - 1]  == 0 && (mazeBody[playerRow + 1][playerCol]  == 0 || mazeBody[playerRow][playerCol-1]  == 0 ))
                    movePlayer(playerRow + 1, playerCol-1);
            }

            case LEFT -> {
                if (playerCol > 0 && (mazeBody[playerRow][playerCol- 1]  == 0))
                    movePlayer(playerRow, playerCol - 1);
            }
            case RIGHT -> {
                if (playerCol < mazeBody[0].length - 1 && (mazeBody[playerRow ][playerCol + 1]  == 0))
                    movePlayer(playerRow, playerCol + 1);
            }
        }
    }

    private void movePlayer(int playerRow, int playerCol) {
        this.playerRow = playerRow;
        this.playerCol = playerCol;
        setChanged();
        notifyObservers("player moved");
    }


    private void notifyMovement() {
        setChanged(); // <- in order to notify something changed, we need to use set changed and the "notify observer"
        notifyObservers("player moved");
    }


    public void saveMaze(String name){
        try {
            File theDir = new File("Saved_Mazes");
            if (!theDir.exists()) {
                theDir.mkdirs();
            }
            byte[] list = maze.toByteArray();
            String filename = "Saved_Mazes/" + name;
            OutputStream out = new MyCompressorOutputStream(new FileOutputStream("tempMaze.maze"));
            out.write(list);
            out.flush();
            Path p = Paths.get("tempMaze.maze");
            byte[] compressedList = Files.readAllBytes(p);

            FileOutputStream fos = new FileOutputStream(filename);

            try {
                fos.write(compressedList);
            } catch (Throwable var13) {
                try {
                    fos.close();
                } catch (Throwable var12) {
                    var13.addSuppressed(var12);
                }
                throw var13;
            }
            fos.close();
        } catch (IOException var14) {
            var14.printStackTrace();
        }
    }

    @Override
    public void loadMaze(String mazeName) {
        try {
            byte[] byteArray = Files.readAllBytes(Paths.get(System.getProperty("user.dir")+"/Saved_Mazes/" + mazeName));
            InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(byteArray));
            byte[] decompressedMaze = new byte[byteArray.length * 100 + 10  /*CHANGE SIZE ACCORDING TO YOU MAZE SIZE*/]; //allocating byte[] for the decompressed maze -
            is.read(decompressedMaze); //Fill decompressedMaze with bytes
            maze = new Maze(decompressedMaze);
            playerRow = maze.getStartPosition().getRowIndex();
            playerCol = maze.getStartPosition().getColumnIndex();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setChanged();
        notifyObservers("maze loaded");
        movePlayer(playerRow,playerCol);
    }
}
