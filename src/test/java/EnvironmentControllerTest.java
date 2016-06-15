import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by rosanna_corvino on 6/14/16.
 */
public class EnvironmentControllerTest {
    @Test
    public void turnHeatOn(){
        Nest hvac = new Nest(60);
        EnvironmentController environmentController = new EnvironmentController(hvac);
        environmentController.tick();

        assertEquals(environmentController.heatStatus, true);
        assertEquals(environmentController.coolStatus, false);
    }

    @Test
    public void turnCoolOn(){
        Nest hvac = new Nest(80);
        EnvironmentController environmentController = new EnvironmentController(hvac);
        environmentController.tick();

        assertEquals(environmentController.heatStatus, false);
        assertEquals(environmentController.coolStatus, true);
    }

    @Test
    public void coolOnCountCheck(){
        Nest hvac = new Nest(80);
        EnvironmentController environmentController = new EnvironmentController(hvac);

        environmentController.tick();
        environmentController.tick();
        environmentController.tick();

        assertEquals(environmentController.coolOnCount, 3);
        assertEquals(environmentController.coolOffCount, 0);
        assertEquals(environmentController.heatOnCount, 0);
        assertEquals(environmentController.heatOffCount, 3);
    }

    @Test
    public void heatOnCountCheck(){
        Nest hvac = new Nest(60);
        EnvironmentController environmentController = new EnvironmentController(hvac);

        environmentController.tick();
        environmentController.tick();
        environmentController.tick();

        assertEquals(environmentController.heatOnCount, 3);
        assertEquals(environmentController.heatOffCount, 0);
        assertEquals(environmentController.coolOnCount, 0);
        assertEquals(environmentController.coolOffCount, 3);
    }

    @Test
    public void fanOffBeforeThreeMinutesCoolOff(){
        Nest hvac = new Nest(60);
        EnvironmentController environmentController = new EnvironmentController(hvac);

        environmentController.tick();
        environmentController.tick();

        assertEquals(environmentController.heatOnCount, 2);
        assertEquals(environmentController.heatOffCount, 0);
        assertEquals(environmentController.coolOnCount, 0);
        assertEquals(environmentController.coolOffCount, 2);
        assertEquals(environmentController.fanStatus, false);
    }

    @Test
    public void fanOnAfterThreeMinutesCoolOff(){
        Nest hvac = new Nest(60);
        EnvironmentController environmentController = new EnvironmentController(hvac);

        environmentController.tick();
        environmentController.tick();
        environmentController.tick();
        environmentController.tick();

        assertEquals(environmentController.heatOnCount, 4);
        assertEquals(environmentController.heatOffCount, 0);
        assertEquals(environmentController.coolOnCount, 0);
        assertEquals(environmentController.coolOffCount, 4);
        assertEquals(environmentController.fanStatus, true);
    }

    @Test
    public void fanOffBeforeFiveMinutesHeatOff(){
        Nest hvac = new Nest(80);
        EnvironmentController environmentController = new EnvironmentController(hvac);

        environmentController.tick();
        environmentController.tick();

        assertEquals(environmentController.heatOnCount, 0);
        assertEquals(environmentController.heatOffCount, 2);
        assertEquals(environmentController.coolOnCount, 2);
        assertEquals(environmentController.coolOffCount, 0);
        assertEquals(environmentController.fanStatus, false);
    }

    @Test
    public void fanOnAfterFiveMinutesHeatOff(){
        Nest hvac = new Nest(80);
        EnvironmentController environmentController = new EnvironmentController(hvac);

        environmentController.tick();
        environmentController.tick();
        environmentController.tick();
        environmentController.tick();
        environmentController.tick();
        environmentController.tick();

        assertEquals(environmentController.heatOnCount, 0);
        assertEquals(environmentController.heatOffCount, 6);
        assertEquals(environmentController.coolOnCount, 6);
        assertEquals(environmentController.coolOffCount, 0);
        assertEquals(environmentController.fanStatus, true);
    }

    @Test
    public void everythingOffWhenInGoodRange(){
        Nest hvac = new Nest(70);
        EnvironmentController environmentController = new EnvironmentController(hvac);

        assertEquals(environmentController.heatStatus, false);
        assertEquals(environmentController.coolStatus, false);
        assertEquals(environmentController.fanStatus, false);
        assertEquals(environmentController.heatOnCount, 0);
        assertEquals(environmentController.heatOffCount, 0);
        assertEquals(environmentController.coolOnCount, 0);
        assertEquals(environmentController.coolOffCount, 0);
    }
}
