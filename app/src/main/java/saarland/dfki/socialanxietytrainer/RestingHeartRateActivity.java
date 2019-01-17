package saarland.dfki.socialanxietytrainer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class RestingHeartRateActivity extends AppCompatActivity {

    private BandConnectAcitivity bandConnectAcitivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resting_heart_rate);
        SharedPreferences sharedPreferences = getSharedPreferences("saarland.dfki.socialanxietytrainer.heartrate_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        bandConnectAcitivity = MainActivity.Companion.getBandConnectAcitivity();


        if(bandConnectAcitivity == null || !bandConnectAcitivity.isConnected()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Band not connected.", Toast.LENGTH_SHORT).show();
                }
            });
            Intent intent = new Intent(RestingHeartRateActivity.this, MainActivity.class);
            startActivity(intent);
        }






    }
}
