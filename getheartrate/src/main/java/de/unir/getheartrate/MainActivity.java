package de.unir.getheartrate;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends WearableActivity implements SensorEventListener {

    private TextView heartrateDisplay;
    private TextView accuracyDisplay;
    private String TAG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        heartrateDisplay = (TextView) findViewById(R.id.heartrateDisplay);
        accuracyDisplay = (TextView) findViewById(R.id.accuracyDisplay);
        TAG = "MainActivityWearable";
        // Enables Always-on
        setAmbientEnabled();

        heartrateDisplay.setText(0);
        accuracyDisplay.setText(0);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_HEART_RATE) {
            String msg = "" + (int) sensorEvent.values[0];
            heartrateDisplay.setText(msg);
            Log.d(TAG, sensorEvent.values.toString());
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        if(sensor.getType() == Sensor.TYPE_HEART_RATE){
            accuracyDisplay.setText(i);
        }

    }
}
