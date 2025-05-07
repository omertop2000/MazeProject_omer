package IO;

import java.io.IOException;
import java.io.InputStream;

public class SimpleDecompressorInputStream extends InputStream {

    private InputStream in;

    public SimpleDecompressorInputStream(InputStream in) {
        this.in = in;
    }
    @Override
    public int read() throws IOException {
        return 0;
    }

    public int read(byte[] savedMazeBytes) throws IOException {
        byte[] byte_arr = in.readAllBytes();
        for(int i = 0 ; i < byte_arr.length /2; i = i+2){
            if(byte_arr[i] == 0){ // if we observing '0'
                for(int j = 0; j < byte_arr[i+1]; j++){ // load '0' the amount of times 0 was compressed
                    savedMazeBytes[j] = 0;
                }
            }
            else{ // observing 1
                for(int j = 0; j < byte_arr[i+1]; j++){ // load '0' the amount of times 0 was compressed
                    savedMazeBytes[j] = 1;
                }
            }
        }
        return 0;
    }
}
