import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

import static org.junit.Assert.*;

/**
 * Created by rosanna_corvino on 6/15/16.
 */

class PhonyTranslator implements Translator {
    public String sentData;

    @Override
    public void sendData(String s) {
        this.sentData = s;
    }
}
public class SocketImplTest {
    public Socket socket;

    @Before
    public void setup() throws IOException {

    }

    @After
    public void cleanup(){

    }

    @Test
    public void canCreateSocket() throws IOException, InterruptedException {
        final SocketImpl socketImpl = new SocketImpl();
        assertNull(socketImpl.serverSocket);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socketImpl.createSocket();
                } catch (SocketException e) {

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        Thread.sleep(1000);

        assertNotNull(socketImpl.serverSocket);
        socketImpl.closeSocket();
    }

    @Test
    public void passesDataFromTheSocketToTheTranslator() throws IOException {
        final SocketImpl socketImpl = new SocketImpl();

        new Thread() {
            @Override
            public void run() {
                try {
                    socketImpl.createSocket();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        PhonyTranslator phonyTranslator = new PhonyTranslator();
        socketImpl.setCommandTranslator(phonyTranslator);

        socket = new Socket("localhost", 5000);
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.println("80 32");
        out.flush();

        socketImpl.acceptData();

        assertEquals("80 32", phonyTranslator.sentData);
        socketImpl.closeSocket();
    }

    @Test
    public void passDataMultipleTimesToSocket() throws IOException {
        final SocketImpl socketImpl = new SocketImpl();

        new Thread() {
            @Override
            public void run() {
                try {
                    socketImpl.createSocket();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        PhonyTranslator phonyTranslator = new PhonyTranslator();
        socketImpl.setCommandTranslator(phonyTranslator);

        socket = new Socket("localhost", 5000);

        PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.println("30 80");
        out.flush();
        socketImpl.acceptData();

        System.out.println(phonyTranslator.sentData);

        out.println("40 90");
        out.flush();
        socketImpl.acceptData();

        System.out.println(phonyTranslator.sentData);
        socketImpl.closeSocket();
    }
}
