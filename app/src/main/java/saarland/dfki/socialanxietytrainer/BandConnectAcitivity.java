package saarland.dfki.socialanxietytrainer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.view.KeyEvent;



import saarland.dfki.socialanxietytrainer.heartrate.HeartRateSimulator;
import saarland.dfki.socialanxietytrainer.heartrate.SimulationType;


import com.microsoft.band.UserConsent;
import com.microsoft.band.sensors.BandHeartRateEvent;
import com.microsoft.band.sensors.BandHeartRateEventListener;
import com.microsoft.band.sensors.HeartRateConsentListener;
import com.microsoft.band.BandClient;
import com.microsoft.band.BandClientManager;
import com.microsoft.band.BandException;
import com.microsoft.band.BandInfo;
import com.microsoft.band.BandIOException;
import com.microsoft.band.ConnectionState;
import com.microsoft.band.sensors.BandHeartRateEvent;
import com.microsoft.band.sensors.BandHeartRateEventListener;
import com.microsoft.band.sensors.HeartRateConsentListener;


public class BandConnectAcitivity extends AppCompatActivity {



    private BandClient client = null;

    private HeartRateSimulator sim;
    private MainActivity mainActivity;

    private boolean connected;

    private int heartrate;
    private BandHeartRateEventListener heartRateEventListener;

    private boolean simulate = false; //<----change here if you need simulation!!!



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_connect_acitivity);
        sim =MainActivity.Companion.getSimulator();

        heartRateEventListener = new
                BandHeartRateEventListener() {
                    @Override
                    public void onBandHeartRateChanged(final BandHeartRateEvent event) {
                        new Runnable() {
                            @Override
                            public void run() {
                                heartrate = event.getHeartRate();
                            }
                        };
                    }
                };
    }


    //to use for simulation
    public void connect(View v) {
        if(simulate) {
            sim.setConnected(true);
            sim.simulateHeartRate(SimulationType.CALM);
        }
        else {
            connectMicrosotBand(v);
        }

    }
    public void disconnect(View v) {
        if(simulate) {
            sim.stopSimulation();
            sim.setConnected(false);
        }
        else {
            disconnectMicrosoftBand(v);
        }


    }

    //to use if real watch is connected
    public void connectMicrosotBand(View v) {
        if(!connected) {
            BandConnectTask task = new BandConnectTask(client, this);
            task.execute();
            askForPermission();
        }
    }

    public void disconnectMicrosoftBand(View v) {
        if(connected) { {
            try {
                client.getSensorManager().unregisterHeartRateEventListener(heartRateEventListener);
                connected = false;
                client = null;
            }
            catch(BandIOException ex) {
            }
        }
        }
    }

    public void setConnected(boolean b) {
        connected = b;
    }

    public void askForPermission() {
        if(connected) {
            if(client.getSensorManager().getCurrentHeartRateConsent() != UserConsent.GRANTED) {
                client.getSensorManager().requestHeartRateConsent(this, new HeartRateConsentListener() {
                    @Override
                    public void userAccepted(boolean consentGiven) {
                        recieveData();
                    }
                });
            }
            else if (client.getSensorManager().getCurrentHeartRateConsent() == UserConsent.GRANTED) {
                recieveData();
            }

        }
    }

    public void recieveData() {
        try {
            client.getSensorManager().registerHeartRateEventListener(heartRateEventListener);
        } catch(BandException ex) {
        }
    }




        /* onKeyUp method is for developing only!  Allows you to manually change the heartrate:
            press I on keybord to simulate a calm heartrate
            press O on keybord to simulate a slightly increased heartrate (a bit nervous or excited)
            press P on keybord to simulate a very high heartrate (fear)
            If input does not work, click on the emulator and try again
            IMPORTANT: Only works in activities that have this method! If you need to manipulate the heartrate in another activity,
            simply copy & paste this method and add it to that activity

    * */
        @Override
        public boolean onKeyUp(int keyCode, KeyEvent event) {
           HeartRateSimulator simulator = MainActivity.Companion.getSimulator();
            switch (keyCode) {
                case KeyEvent.KEYCODE_I :
                    simulator.simulateHeartRate(SimulationType.CALM);
                    return true;

            case KeyEvent.KEYCODE_O:
                    simulator.simulateHeartRate(SimulationType.EXCITED);
                    return true;

                case KeyEvent.KEYCODE_P :
                    simulator.simulateHeartRate(SimulationType.FRIGHTENED);
                    return true;
                default:
                    return super.onKeyUp(keyCode, event);
            }
        }





}
