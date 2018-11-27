package saarland.dfki.socialanxietytrainer;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.microsoft.band.BandClient;
import com.microsoft.band.BandClientManager;
import com.microsoft.band.BandException;
import com.microsoft.band.BandInfo;
import com.microsoft.band.ConnectionState;

import saarland.dfki.socialanxietytrainer.BandConnectAcitivity;


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
        if(devices.length != 0 ) {

            client = BandClientManager.getInstance().create(activity.getBaseContext(), devices[0]);
        }

        if(client != null ) {
            try {
                ConnectionState state = client.connect().await();
                if (state == ConnectionState.CONNECTED) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity.getApplicationContext(),"Connected",Toast.LENGTH_SHORT).show();
                        }
                    });

                    activity.setConnected(true);
                } else {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity.getApplicationContext(),"Connection failed",Toast.LENGTH_SHORT).show();
                        }
                    });

                    activity.setConnected(false);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BandException e) {
                e.printStackTrace();
            }
        }

        else {
            activity.setConnected(false);
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity.getApplicationContext(),"No microsoft band found.",Toast.LENGTH_SHORT).show();
                }
            });

            Log.e("BandConnectTask","microsoft band not found");
        }
        return null;
    }
}
