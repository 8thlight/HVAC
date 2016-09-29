/**
 * Created by rosanna_corvino on 6/15/16.
 */
public class SocketTranslator implements Translator{
    public EnvironmentInterface environmentController;
    public String sentData;

    public SocketTranslator(EnvironmentInterface environmentController){
        this.environmentController = environmentController;
    }

    public void sendData(String s) {
        this.sentData = s;
        parseData(sentData);
    }

    public void parseData(String s){
        String[] splitStr = s.split("\\s+");
        environmentController.setMinTemp(Integer.parseInt(splitStr[0]));
        environmentController.setMaxTemp(Integer.parseInt(splitStr[1]));
    }
}
