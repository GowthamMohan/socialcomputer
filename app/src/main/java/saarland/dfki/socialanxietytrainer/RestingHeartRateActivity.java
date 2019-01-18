package saarland.dfki.socialanxietytrainer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import saarland.dfki.socialanxietytrainer.heartrate.GetRestingHeartRateTask;

public class RestingHeartRateActivity extends AppCompatActivity {

    private BandConnectAcitivity bandConnectAcitivity;
    private int restingHeartRate;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resting_heart_rate);
        sharedPreferences = getSharedPreferences("saarland.dfki.socialanxietytrainer.heartrate_preferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        bandConnectAcitivity = MainActivity.Companion.getBandConnectAcitivity();
        //----------------------------------------------------------------------------------------------------------------------------
        if(bandConnectAcitivity == null || !bandConnectAcitivity.isConnected()) {
           showErrorMessage(-1);
            try{
                Thread.sleep(3000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            super.onBackPressed();
        }
       //-----------------------------------------------------------------------------------------------------------------------------
    }


    //onClick
    public void trackRestingHeartRate(View v) {
        //duration: 60s
        GetRestingHeartRateTask getRestingHeartRateTask = new GetRestingHeartRateTask(60000);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Integer> result = executorService.submit(getRestingHeartRateTask);
        try{
            int tmp = result.get();
            if(tmp < 0) {
                showErrorMessage(tmp);
            }
            else {
                processResult(tmp);
            }
        }catch (InterruptedException e){
            showErrorMessage(-2);
            executorService.shutdown();
        }catch (ExecutionException e){
            showErrorMessage(-2);
            executorService.shutdown();
        }
        executorService.shutdown();

    }

    public void processResult(int result) {
        restingHeartRate = result;
        editor.putInt("RestingHeartRate",restingHeartRate);
        editor.commit();
        updateTextView();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),"Finished.", Toast.LENGTH_SHORT);
            }
        });
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        super.onBackPressed();
    }


    public void updateTextView() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView textView = findViewById(R.id.textView_display_resting_hr);
                textView.setText(textView.getText() + " " + restingHeartRate);
            }
        });
    }


    public void showErrorMessage(int errorCode) {
        switch (errorCode) {
            case -1:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Microsoft Band not connected. Please connect it and try again.", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case -2:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Process was interrupted. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
                default: break;
        }

    }
}
