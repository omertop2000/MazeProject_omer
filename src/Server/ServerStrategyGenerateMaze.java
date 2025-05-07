package Server;

import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.EmptyMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.SimpleMazeGenerator;

import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy {
    @Override
    public void serverstrategy(InputStream in, OutputStream out,Configurations configurations) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(in);
            ObjectOutputStream toClient = new ObjectOutputStream(out);
            int[] int_arr = (int[]) fromClient.readObject();
            int row = int_arr[0];
            int col = int_arr[1];
            Maze maze;
            if(configurations.getGenerating_alg().equals("MyMazeGenerator"))
                maze = new MyMazeGenerator().generate(row,col);
            else if (configurations.getGenerating_alg().equals("SimpleMazeGenerator"))
                maze = new SimpleMazeGenerator().generate(row,col);
            else
                maze = new EmptyMazeGenerator().generate(row,col);

            byte[] byte_to_send = maze.toByteArray();
            String mazeFileName = "new_maze_generating_maze";
            OutputStream out1 = new MyCompressorOutputStream(new FileOutputStream(mazeFileName));
            InputStream in1 = new FileInputStream(mazeFileName);
            out1.write(byte_to_send); // compressing to file
            toClient.writeObject(in1.readAllBytes()); // with normal input stream, reading from file
            in1.close();
            out1.close();
            toClient.close();
            fromClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
