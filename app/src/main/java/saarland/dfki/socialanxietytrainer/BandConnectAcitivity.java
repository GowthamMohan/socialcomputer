package saarland.dfki.socialanxietytrainer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.Button;
import android.view.View;
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


    private Button connectButton;
    private BandClient client = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_connect_acitivity);
        connectButton = findViewById(R.id.connectButton);
    }



    public void connect(View v) {
            new BandConnectTask(client,this).execute();
    }
}
