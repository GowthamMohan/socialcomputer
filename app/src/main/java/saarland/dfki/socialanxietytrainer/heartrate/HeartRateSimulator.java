package saarland.dfki.socialanxietytrainer.heartrate;

import saarland.dfki.socialanxietytrainer.BandConnectAcitivity;

public class HeartRateSimulator {

    private SimulationType type;
    private int simulated_heartrate;
    private SimulationThread t;
    private BandConnectAcitivity activity;


    //default values
    public HeartRateSimulator() {
        simulated_heartrate = 60;
        type = SimulationType.CALM;
    }

    public void simulateHeartRate(SimulationType type) {
        if(activity!= null) {
            if (t == null) {
                t = new SimulationThread(this, type,activity);
                t.start();
            } else {
                t.interrupt();
                t = new SimulationThread(this, type,activity);
                t.start();
            }
        }
    }

    public void stopSimulation() {
        if(t!= null ) {
            t.interrupt();
        }
    }

    public int getHeartRate() {
        return simulated_heartrate;
    }

    public void setHeartrate(int val) {
        simulated_heartrate = val;
    }

    public SimulationType getType() { return  type; }

    public void setActivity(BandConnectAcitivity activity) {
        this.activity = activity;
    }
}
