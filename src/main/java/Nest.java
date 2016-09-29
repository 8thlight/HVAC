/**
 * Created by rosanna_corvino on 6/14/16.
 */
public class Nest implements HVAC {
    public int temperature;

    public Nest(){
    }

    @Override
    public void heat(boolean on) {
    }

    @Override
    public void cool(boolean on) {
    }

    @Override
    public void fan(boolean on) {

    }

    @Override
    public int temp() {
        return temperature;
    }

}
