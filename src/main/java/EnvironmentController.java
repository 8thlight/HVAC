/**
 * Created by rosanna_corvino on 6/14/16.
 */
public class EnvironmentController {
    private HVAC hvac;

    public boolean heatStatus;
    public boolean coolStatus;
    public boolean fanStatus;
    public int heatOnCount;
    public int heatOffCount;
    public int coolOnCount;
    public int coolOffCount;

    public EnvironmentController(HVAC hvac){
        this.hvac = hvac;

        heatStatus = false;
        coolStatus = false;
        fanStatus = false;
        heatOnCount = 0;
        heatOffCount = 0;
        coolOnCount = 0;
        coolOffCount = 0;
    }

    public void tick(){
        if (hvac.temp() < 65){
            turnOnHeat();
        }

        if (hvac.temp() > 75){
            turnOnCool();
        }
    }

    private void turnOnHeat(){
        hvac.heat(true);
        hvac.fan(true);
        turnOffCool();

        heatOnCheckHeatStatusAdjustCount();

        heatStatus = true;
    }

    private void turnOnCool(){
        hvac.cool(true);
        hvac.fan(true);
        turnOffHeat();

        coolOnCheckCoolStatusAdjustCount();

        coolStatus = true;
    }

    private void turnOffHeat(){
        hvac.heat(false);

        heatOffCheckHeatStatusAdjustCount();

        heatStatus = false;

        if (heatOffCount <= 5){
            turnFanOff();
        }
        else{
            turnFanOn();
        }
    }

    private void turnOffCool(){
        hvac.cool(false);

        coolOffCheckStatusAdjustCount();

        coolStatus = false;

        if (coolOffCount <= 3){
            turnFanOff();
        }
        else{
            turnFanOn();
        }
    }

    private void turnFanOn(){
        hvac.fan(true);
        fanStatus = true;
    }

    private void turnFanOff(){
        hvac.fan(false);
        fanStatus = false;
    }

    private void heatOnCheckHeatStatusAdjustCount(){
        if (heatStatus == false){
            heatOnCount = 1;
            heatOffCount = 0;
        }
        else{
            heatOnCount = heatOnCount + 1;
        }
    }

    private void coolOnCheckCoolStatusAdjustCount(){
        if (coolStatus == false){
            coolOnCount = 1;
            coolOffCount = 0;
            turnFanOff();
        }
        else{
            coolOnCount = coolOnCount + 1;
        }
    }

    private void heatOffCheckHeatStatusAdjustCount(){
        if (heatStatus == true){
            heatOnCount = 0;
            heatOffCount = 1;
            turnFanOff();
        }
        else{
            heatOffCount = heatOffCount + 1;
        }
    }

    private void coolOffCheckStatusAdjustCount(){
        if (coolStatus == true){
            coolOnCount = 0;
            coolOffCount = 1;
        }
        else{
            coolOffCount = coolOffCount + 1;
        }
    }
}