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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_connect_acitivity);
        sim = new HeartRateSimulator();

    }



    public void connect(View v) {
        sim.simulateHeartRate(SimulationType.CALM);


    }
    public void disconnect(View v) {
        sim.stopSimulation();


    }

    /* method for developing only!
            press I on keybord to simulate a calm heartrate
            press O on keybord to simulate a slightly increased heartrate (a bit nervous or excited)
            press P on keybord to simulate a very high heartrate (fear)

            keybord input only works when in BandConnectActivity, so mode change has to be made here. (will be improved so that it works in the whole APP (TODO)
            If keybord input does not work, click on the Emulator and try again


    * */

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_I:
                sim.simulateHeartRate(SimulationType.CALM);
                return true;
            case KeyEvent.KEYCODE_O:
                sim.simulateHeartRate(SimulationType.EXCITED);
                return true;
            case KeyEvent.KEYCODE_P:
                sim.simulateHeartRate(SimulationType.FRIGHTENED);
                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }


}
