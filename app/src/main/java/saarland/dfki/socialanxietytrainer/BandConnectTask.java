package saarland.dfki.socialanxietytrainer;

import android.os.AsyncTask;

import com.microsoft.band.BandClient;
import com.microsoft.band.BandClientManager;
import com.microsoft.band.BandException;
import com.microsoft.band.BandInfo;
import com.microsoft.band.BandIOException;
import com.microsoft.band.ConnectionState;
import com.microsoft.band.sensors.BandHeartRateEvent;
import com.microsoft.band.sensors.BandHeartRateEventListener;
import com.microsoft.band.sensors.HeartRateConsentListener;


public class BandConnectTask extends AsyncTask<Void, Void, Void> {

    private BandClient client;
    private BandConnectAcitivity activity;

    public BandConnectTask(BandClient client, BandConnectAcitivity activity ) {
        this.client = client;
        this.activity = activity;
    }

    //https://www.sitepoint.com/getting-started-with-microsoft-band-sdk/

    @Override
    protected Void doInBackground(Void... params) {
        BandInfo[] devices = BandClientManager.getInstance().getPairedBands();
        client = BandClientManager.getInstance().create(activity.getBaseContext(), devices[0]);
        try {
            ConnectionState state = client.connect().await();
            if(state == ConnectionState.CONNECTED) {
                // do work on success
            } else {
                // do work on failure
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BandException e) {
            e.printStackTrace();
        }
        return null;
    }
}
