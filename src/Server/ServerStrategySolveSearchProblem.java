package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.*;

public class ServerStrategySolveSearchProblem implements IServerStrategy{

    private String Cache;

    public ServerStrategySolveSearchProblem() {
        String tempDirectoryPath = System.getProperty("java.io.tmpdir");
        Cache = tempDirectoryPath;
    }
    @Override
    public void serverstrategy(InputStream in, OutputStream out,Configurations configurations) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(in);
            ObjectOutputStream toClient = new ObjectOutputStream(out);
            Maze maze = (Maze) fromClient.readObject();
            File Cache_directory = new File(Cache); // opening temp directory
            File[] filesList = Cache_directory.listFiles(); // getting all files in temp directory
            String rows = String.valueOf(maze.getRows());
            String cols = String.valueOf(maze.getCols());
            String Sx = String.valueOf(maze.getStartPosition().getRowIndex());
            String Sy = String.valueOf(maze.getStartPosition().getColumnIndex());
            String Gx = String.valueOf(maze.getGoalPosition().getRowIndex());
            String Gy = String.valueOf(maze.getGoalPosition().getColumnIndex());
            String file_name_search = "maze - " + rows + cols +Sx + Sy +Gx +Gy;
            assert filesList != null;
            for(File file : filesList) { // checking each file
                // we will check name of files in the format: maze-R,C,Sx,Sy,Gx,Gy
                // R - amount of rows C - amount of columns and so on
                if (file.getName().equals(file_name_search)) { // searching for maze with similar start point end point and size
                    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                        Maze cur_maze = null;
                        Object o1 = ois.readObject(); // getting each file first object which is the maze
                        if (o1 instanceof Maze) {
                            cur_maze = (Maze) o1;
                        } else {
                            ois.close();
                            continue;
                        }
                        if (cur_maze.equals(maze)) { // even if maze characteristic are similar, still checking if the mazes are equal
                            Solution solution = (Solution) ois.readObject();
                            toClient.writeObject(solution);
                            toClient.flush();

                            fromClient.close();
                            toClient.close();
                            ois.close();
                            return;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else continue ; // continue searching more files
            }
            SearchableMaze searchableMaze = new SearchableMaze(maze);
            Solution solution;
            if(configurations.getSolving_alg().equals("BestFirstSearch"))
                solution = new BestFirstSearch().solve(searchableMaze);
            else if (configurations.getSolving_alg().equals("BreadthFirstSearch"))
                solution = new BreadthFirstSearch().solve(searchableMaze);
            else
                solution = new DepthFirstSearch().solve(searchableMaze);
            //adding to cache a valid solution after solving:
            File tempfile = new File(Cache,file_name_search);  // creating file in temp directory named solution
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(tempfile))) {
                oos.writeObject(maze); // first object will be the maze
                oos.writeObject(solution); // writing to that file
            } catch (IOException e) {
                e.printStackTrace();
            }
            toClient.writeObject(solution);
            toClient.flush();
            fromClient.close();
            toClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
