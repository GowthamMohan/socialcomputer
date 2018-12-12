package saarland.dfki.socialanxietytrainer;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.microsoft.band.BandClient;
import com.microsoft.band.BandClientManager;
import com.microsoft.band.BandException;
import com.microsoft.band.BandInfo;
import com.microsoft.band.ConnectionState;
import com.microsoft.band.UserConsent;
import com.microsoft.band.sensors.HeartRateConsentListener;

import saarland.dfki.socialanxietytrainer.BandConnectAcitivity;

//Sources:
//https://www.sitepoint.com/getting-started-with-microsoft-band-sdk/
//Microsoft Band SDK and Samples for Android, BandHeartRateAppActivity.java


public class BandConnectTask extends AsyncTask<Void, Void, Void> {

    private BandClient client;
    private BandConnectAcitivity activity;
    private boolean consent;
    public String msg;

    public BandConnectTask(BandConnectAcitivity activity ) {
        this.activity = activity;
        consent = false;
    }



    @Override
    protected Void doInBackground(Void... params) {
        BandInfo[] devices = BandClientManager.getInstance().getPairedBands();
            if(devices.length != 0 && BandClientManager.getInstance().create(activity.getBaseContext(), devices[0]) != null) {
                client = BandClientManager.getInstance().create(activity.getBaseContext(), devices[0]);
                activity.setClient(client);
                askForPermission();
                if (!consent) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity.getApplicationContext(), "Access to heart rate sensor denied. Please accept for the app to work.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return null;
                }
                try {
                    ConnectionState state = client.connect().await();
                    if (state == ConnectionState.CONNECTED) {
                        activity.setConnected(true);
                        client.getSensorManager().registerHeartRateEventListener(activity.getHeartRateEventListener());
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity.getApplicationContext(), "Connected", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                    else {
                        //this case should not happen due to exception handling. Delete if verified to be unnecessary
                        activity.setConnected(false);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity.getApplicationContext(), "Connection failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                        Log.e("BandConnectActivity","Connection failed");

                    }

                } catch (InterruptedException e) {
                    activity.setConnected(false);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity.getApplicationContext(), "Connection Interrupted.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Log.e("BandConnectTask","Connection Interrupted.");
                } catch (BandException e) {
                    activity.setConnected(false);
                    switch (e.getErrorType()) {
                        case SERVICE_ERROR:
                            msg = "Microsoft Health App is not installed on the phone.";
                            break;
                        case PERMISSION_ERROR:
                            msg = "No permission to acces heartrate sensor.";
                            break;
                        case UNSUPPORTED_SDK_VERSION_ERROR:
                            msg = "SDK not supported.";
                            break;
                        case TIMEOUT_ERROR:
                            msg = "Timeout.";
                            break;
                        default:
                            msg = "Unknown error.";
                            break;
                    }
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                    Log.e("BandConnectTask",msg);
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

    public void askForPermission() {
            if(client.getSensorManager().getCurrentHeartRateConsent() != UserConsent.GRANTED) {
                client.getSensorManager().requestHeartRateConsent(activity, new HeartRateConsentListener() {
                    @Override
                    public void userAccepted(boolean consentGiven) {
                        consent = true;
                    }
                });
            }
            else if (client.getSensorManager().getCurrentHeartRateConsent() == UserConsent.GRANTED) {
                consent = true;
            }
            else {
                consent = false;
            }
    }
}
