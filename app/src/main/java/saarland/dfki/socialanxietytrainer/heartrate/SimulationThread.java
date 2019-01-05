package saarland.dfki.socialanxietytrainer.heartrate;

import android.util.Log;

import saarland.dfki.socialanxietytrainer.BandConnectAcitivity;

public class SimulationThread extends Thread {

    private HeartRateSimulator simulator;
    private SimulationType type;
    private BandConnectAcitivity acitivity;


    public SimulationThread(HeartRateSimulator sim, SimulationType type, BandConnectAcitivity activity) {
        this.simulator = sim;
        this.type = type;
        this.acitivity = activity;

    }

    @Override
    public void run() {

        double x = 0;
        double tmp = 0;

        while (!isInterrupted()) {
            try {
                sleep(500);
            } catch (InterruptedException e) {
                interrupt();
            }
            x = (x + 1) % 360;
            tmp = Math.toRadians(2 * x);
            tmp = Math.sin(tmp);
            if (type == SimulationType.CALM) {
                tmp = (tmp * 10 + 70); //set to interval 60 - 80 bpm, normal pulse
            } else if (type == SimulationType.EXCITED) {
                tmp = (tmp * 10) + 90;  //set to interval 80 - 100 bpm
            } else {
                tmp = (tmp * 10) + 110; //set to interval 100 - 120 bpm, very anxious
            }
            simulator.setHeartrate((int)tmp);
            acitivity.setHeartrate((int) tmp);
            Log.d("SimThread", Integer.toString((int) tmp) + "bpm");

        }
    }

}
