import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by rosanna_corvino on 6/15/16.
 */

interface Translator {
    void sendData(String s);
};

public class SocketImpl {
    public ServerSocket serverSocket;
    public Socket socket;
    public Translator myTranslator;

    public SocketImpl() throws IOException {
    }

    public void createSocket() throws IOException {
        serverSocket = new ServerSocket(5000);
        socket = serverSocket.accept();
    }

    public void acceptData() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader((socket.getInputStream())));
        myTranslator.sendData(in.readLine());
    }

    public void closeSocket() throws IOException {
        serverSocket.close();
    }

    public void setCommandTranslator(Translator t) throws IOException {
        myTranslator = t;
    }
}
