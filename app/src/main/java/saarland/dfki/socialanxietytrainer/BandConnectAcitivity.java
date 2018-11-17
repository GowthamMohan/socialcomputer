package saarland.dfki.socialanxietytrainer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;
import android.view.KeyEvent;

import com.microsoft.band.BandClient;

import saarland.dfki.socialanxietytrainer.heartrate.HeartRateSimulator;
import saarland.dfki.socialanxietytrainer.heartrate.SimulationType;


public class BandConnectAcitivity extends AppCompatActivity {



    private BandClient client = null;
    private HeartRateSimulator sim;
    private MainActivity mainActivity;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_connect_acitivity);
        sim =MainActivity.Companion.getSimulator();


    }



    public void connect(View v) {
        sim.setConnected(true);
        sim.simulateHeartRate(SimulationType.CALM);



    }
    public void disconnect(View v) {
        sim.stopSimulation();
        sim.setConnected(false);


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
