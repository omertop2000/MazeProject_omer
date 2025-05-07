package IO;

import java.io.IOException;
import java.io.OutputStream;

public class SimpleCompressorOutputStream extends OutputStream {

    private OutputStream out;

    public SimpleCompressorOutputStream(OutputStream out) {
        this.out = out;
    }
    @Override
    public void write(int b) throws IOException {

    }

    public void write(byte[] b) throws IOException { // nitan lidros
        byte prev = 0;
        int counter = 0;
        byte this_byte;
        for (int i = 0; i < b.length; i++) {
            this_byte = b[i];
            if (this_byte == prev && counter < 255)
                counter++;
            else if (this_byte == prev && counter == 256) {
                this.out.write(255);
                this.out.write(0);
                counter = 1;
            }
            else{
                this.out.write((byte)counter);
                prev = this_byte;
                counter = 1;
            }
        }
        this.out.write((byte)counter);
    }
}
