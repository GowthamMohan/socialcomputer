package saarland.dfki.socialanxietytrainer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.KeyEvent;
import android.widget.Toast;
import android.widget.TextView;

import saarland.dfki.socialanxietytrainer.classification.ClassificationKind;
import saarland.dfki.socialanxietytrainer.classification.ClassificationManager;
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

//Sources:
//https://www.sitepoint.com/getting-started-with-microsoft-band-sdk/
//Microsoft Band SDK and Samples for Android, BandHeartRateAppActivity.java

public class BandConnectAcitivity extends AppCompatActivity {

    private BandClient client = null;
    private HeartRateSimulator sim;
    public static boolean connected;
    private int heartrate;
    private BandHeartRateEventListener heartRateEventListener;
    private boolean simulate = true; //<----change here!!!
    private ClassificationManager classificationManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_connect_acitivity);
        sim =MainActivity.Companion.getSimulator();
        sim.setActivity(this);
        classificationManager = MainActivity.Companion.getClassificationManager();
        heartRateEventListener = new BandHeartRateEventListener() {
            @Override
            public void onBandHeartRateChanged(final BandHeartRateEvent event) {
                    if (event != null) {
                    heartrate = event.getHeartRate();
                    classificationManager.consume(ClassificationKind.HEARTBEAT, heartrate);
                   // Log.d("BandConnectActivity",heartrate + "bpm");
                }
            }
        };
    }

    public void connect(View v) {
        //simulation
        if(simulate) {
            if(!connected) {
                sim.simulateHeartRate(SimulationType.CALM);
                connected = true;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Connected.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Already connected.", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }
        //real microsoft band
        else {
            connectMicrosotBand(v);
        }

    }
    public void disconnect(View v) {
        //simulation
        if (simulate) {
            if (connected) {
                sim.stopSimulation();
                connected = false;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Disconnected.", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Nothing to disconnect.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        //real microsoft band
        else {
            disconnectMicrosoftBand(v);
        }
    }


    public void connectMicrosotBand(View v) {
        if (!connected) {
            BandConnectTask task = new BandConnectTask(this);
            task.execute();
        }
        else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Already connected.", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    public void disconnectMicrosoftBand(View v) {
        if (connected && client != null) {
            try {
                if (heartRateEventListener != null) {
                    client.getSensorManager().unregisterHeartRateEventListener(heartRateEventListener);
                }
                client.disconnect().await();
                connected = false;
                client = null;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Disconnected.", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Disconnection failed. Try again.", Toast.LENGTH_SHORT).show();
                    }
                });
                Log.e("BandConnectionTask",e.getMessage());
            }
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Nothing to disconnect", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    public synchronized void setConnected(boolean b) {
        connected = b;
    }

    public synchronized boolean isConnected() {return connected;};

    public void setClient(BandClient client){
        this.client = client;
    }

    public BandHeartRateEventListener getHeartRateEventListener() {
        return heartRateEventListener;
    }

    public synchronized void setHeartrate(int heartrate) {
        this.heartrate = heartrate;
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
            //don't react if no simulated watch is connected
            if(!connected){
                return super.onKeyUp(keyCode, event);
            }
            //kept general to be able to copy it everywhere
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
