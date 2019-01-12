package saarland.dfki.socialanxietytrainer.heartrate;

import saarland.dfki.socialanxietytrainer.BandConnectAcitivity;

public class HeartRateSimulator {

    private SimulationType type;
    private SimulationThread t;
    private BandConnectAcitivity activity;


    //default values
    public HeartRateSimulator() {
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

    public SimulationType getType() { return  type; }
    public void setActivity(BandConnectAcitivity activity) {
        this.activity = activity;
    }
}
