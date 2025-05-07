package Server;

import java.io.*;
import java.util.Properties;

public class Configurations {
    private int thread_pool_size;
    private String generating_alg;
    private String solving_alg;
    private static Configurations instance = new Configurations();

    private Configurations(){
        this.write_to_file_default();
        // writing default values to file
        this.read_from_file();
        // reading from the file to update fields
    }

    public static Configurations getInstance(){
        return instance;
    }

    public void setConfiguration(String generating_alg,String solving_alg,int thread_pool_size) {
        this.write_to_file(String.valueOf(thread_pool_size),generating_alg,solving_alg);
    }

    public String getGenerating_alg() {
        this.read_from_file();
        return generating_alg;
    }

    public int getThread_pool_size() {
        this.read_from_file();
        return thread_pool_size;
    }

    public String getSolving_alg() {
        this.read_from_file();
        return solving_alg;
    }

    private void write_to_file_default() {

        try (OutputStream output = new FileOutputStream("resources/config.properties.properties")) {

            Properties prop = new Properties();

            // set the properties value with default values as follows:
            prop.setProperty("threadPoolSize","5");
            prop.setProperty("mazeGeneratingAlgorithm","MyMazeGenerator");
            prop.setProperty("mazeSearchingAlgorithm","BestFirstSearch");

            // save properties to project root folder
            prop.store(output, null);

            System.out.println(prop);

        } catch (IOException io) {
            io.printStackTrace();
        }

    }

    private void write_to_file(String TP_size,String generating_alg,String solving_alg) {

        try (OutputStream output = new FileOutputStream("resources/config.properties.properties")) {

            Properties prop = new Properties();

            // set the properties value
            // user can change number of thread pool, algorithm of generating and logarithm of solving
            prop.setProperty("threadPoolSize",TP_size);
            prop.setProperty("mazeGeneratingAlgorithm",generating_alg);
            prop.setProperty("mazeSearchingAlgorithm",solving_alg);

            // save properties to project root folder
            prop.store(output, null);

            System.out.println(prop);

        } catch (IOException io) {
            io.printStackTrace();
        }

    }

    private void read_from_file() {
        try (InputStream input = new FileInputStream("resources/config.properties.properties")) {

            Properties prop = new Properties();
            // load a properties file
            prop.load(input);

            // get the property value and print it out
            this.generating_alg = prop.getProperty("mazeGeneratingAlgorithm");
            this.solving_alg = prop.getProperty("mazeSearchingAlgorithm");
            this.thread_pool_size = Integer.parseInt(prop.getProperty("threadPoolSize"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void setProperty(String mazeSearchingAlgorithm, String solution) {
        this.generating_alg = mazeSearchingAlgorithm;
        this.solving_alg = solution;
    }


    public void setConfigPath(String s) {
    }


    public String getmaze_gen_type() {
        return this.generating_alg;
    }

    public String getmaze_sol_type() {
        return this.solving_alg;
    }

    public String get_t_pool_size() {
        return Integer.toString(this.thread_pool_size);
    }
}
