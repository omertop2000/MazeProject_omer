package IO;

import java.io.IOException;
import java.io.OutputStream;

public class MyCompressorOutputStream extends OutputStream {

    private OutputStream out;

    public MyCompressorOutputStream (OutputStream out) {
        this.out = out;
    }

    @Override
    public void write(int b) throws IOException {
        return ;
    }

    public void write(byte[] b) throws IOException { //
        this.out.flush();
        for(int i = 0; i < 24; i++){    //rows, cols, starting point.x, start point.y,  goal point.x, goal point.y
            this.out.write(b[i]);
        }

        int remain = b.length - 24;
        int j = 24;

        while (remain >= 8){
            String binaryValue = "";
            for (int i = 0; i < 8; i++) {
                binaryValue += "" + b[i + j];  // convert each byte and build a string of binary size 8
            }

            int intValue = Integer.parseInt(binaryValue, 2);  // ---> 0-255
            this.out.write((byte)intValue);

            j += 8;
            remain -= 8;
        }

        if (remain > 0){
            for (int i = b.length - 1 - remain; i < b.length; i++) {
                this.out.write(b[i]);
            }
        }
        this.out.flush();

    }

}

