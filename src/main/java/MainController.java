import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by rosanna_corvino on 6/15/16.
 */
public class MainController {
    public static void main(String args[]) throws IOException {
        Integer minTemp = 65;
        Integer maxTemp = 75;

        if (args[0] != null){
            minTemp = Integer.valueOf(args[0]);
        }

        if (args[1] != null){
            maxTemp = Integer.valueOf(args[1]);
        }

        HVAC hvac = new Nest();
        EnvironmentInterface environmentController =
                new EnvironmentController(hvac, minTemp, maxTemp);
        Socket socket = new Socket("localhost", 5000);
        SocketImpl socketImpl = new SocketImpl();
        SocketTranslator socketTranslator = new SocketTranslator(environmentController);
        socketImpl.setCommandTranslator(socketTranslator);

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

        Scanner scanner = new Scanner(System.in);
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        while(scanner.hasNext()){
            String tempString = scanner.nextLine();
            String[] splitStr = tempString.split("\\s+");
            minTemp = Integer.valueOf(splitStr[0]);
            maxTemp = Integer.valueOf(splitStr[1]);

            System.out.println(minTemp);
            System.out.println(maxTemp);

            out.println(minTemp + " " + maxTemp);
            out.flush();
            socketImpl.acceptData();

            System.out.println("EC MIN = " + environmentController.getMinTemp());
            System.out.println("EC MAX = " + environmentController.getMaxTemp());
        }

        socket.close();
    }
}
