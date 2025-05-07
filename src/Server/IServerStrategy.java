package Server;

import java.io.InputStream;
import java.io.OutputStream;

public interface IServerStrategy {

    public void serverstrategy(InputStream in, OutputStream out,Configurations configurations);
}
