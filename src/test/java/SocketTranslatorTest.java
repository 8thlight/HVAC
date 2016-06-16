import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by rosanna_corvino on 6/15/16.
 */

public class SocketTranslatorTest {

    @Test
    public void dataSentToTranslator(){
        EnvironmentInterface environmentController = new MockEnvironmentController();
        SocketTranslator socketTranslator = new SocketTranslator(environmentController);
        socketTranslator.sendData("45 85");

        assertEquals(socketTranslator.sentData, "45 85");
    }

    @Test
    public void dataSentToEnvironmentController(){
        EnvironmentInterface environmentController = new MockEnvironmentController();
        SocketTranslator socketTranslator = new SocketTranslator(environmentController);
        socketTranslator.sendData("45 85");

        assertTrue(environmentController.getMaxTemp() == 85);
        assertTrue(environmentController.getMinTemp() == 45);
    }
}

class MockEnvironmentController implements EnvironmentInterface{
    int minTemp;
    int maxTemp;

    @Override
    public void setMinTemp(int minTemp){
        this.minTemp = minTemp;
    }

    @Override
    public void setMaxTemp(int maxTemp){
        this.maxTemp = maxTemp;
    }

    @Override
    public Integer getMinTemp() {
        return minTemp;
    }

    @Override
    public Integer getMaxTemp() {
        return maxTemp;
    }
}


