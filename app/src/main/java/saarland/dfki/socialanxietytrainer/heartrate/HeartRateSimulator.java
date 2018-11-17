package saarland.dfki.socialanxietytrainer.heartrate;

public class HeartRateSimulator {


    SimulationType type;
    private double heartrate;
    private SimulationThread t;
    private boolean connected;

    //default values
    public HeartRateSimulator() {

        heartrate = 60;
        type = SimulationType.CALM;
        connected = false;
    }



    public void simulateHeartRate(SimulationType type) {
        if(connected) {
            if (t == null) {
                t = new SimulationThread(this, type);
                t.start();
            } else {
                t.interrupt();
                t = new SimulationThread(this, type);
                t.start();
            }
        }

    }



    public void stopSimulation() {
        if(t!= null ) {
            t.interrupt();
        }
    }




    public double getHeartRate() {
        return heartrate;
    }


    public void setHeartrate(double val) {
        heartrate = val;
    }
    public SimulationType getType(){return  type;}

    public void setConnected(boolean b) {
        connected = b;
    }



}
