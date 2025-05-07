package IO;

import java.io.IOException;
import java.io.InputStream;


public class MyDecompressorInputStream extends InputStream {

    private InputStream in;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }
    @Override
    public int read() throws IOException {
        return 0;
    }

    public int read(byte[] savedMazeBytes) throws IOException {
        byte[] byte_arr = in.readAllBytes();
        for (int i = 0; i < 24; i++) {
            savedMazeBytes[i] = (byte) byte_arr[i];
        }

        //calculate Maze size
        String rowsBinary ="";
        String colsBinary = "";
        for (int i = 0; i < 4; i++) {
            rowsBinary += savedMazeBytes[i];
            colsBinary += savedMazeBytes[4 + i];
        }
        byte byteRowValue = Byte.parseByte(rowsBinary);
        byte byteColValue = Byte.parseByte(colsBinary);

        // Convert the byte to an int
        int rows = byteRowValue;
        int cols = byteColValue;


        int mazeSize = rows * cols;

        int countCheckForSize = 0;

        for (int i = 24; i < byte_arr.length; i++) {
            if (mazeSize - countCheckForSize < 8){
                for (int j = 0; j < mazeSize - countCheckForSize; j++) {
                    savedMazeBytes[(i - 24) * 8 + 24 + j ] = (byte) byte_arr[i + j];

                }
                break;
            }
            else{
                countCheckForSize += 8;
                String binaryString = String.format("%8s", Integer.toBinaryString(byte_arr[i] & 0xFF)).replace(' ', '0');
                //byte[] byteArray = binaryString.getBytes();
                for (int j = 0; j < binaryString.length(); j++) {
                    if (binaryString.charAt(j) == '1') {
                        savedMazeBytes[(i-24)*8 + j + 24] = 1;
                    }
                    else if (binaryString.charAt(j) == '0') {
                        savedMazeBytes[(i-24)*8 + j + 24] = 0;
                    }
                }
            }
        }

        return 0;
    }
}