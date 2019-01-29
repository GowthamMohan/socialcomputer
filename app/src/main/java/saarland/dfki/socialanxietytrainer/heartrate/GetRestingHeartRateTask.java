package saarland.dfki.socialanxietytrainer.heartrate;

import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import saarland.dfki.socialanxietytrainer.BandConnectAcitivity;
import saarland.dfki.socialanxietytrainer.MainActivity;

public class GetRestingHeartRateTask implements Callable<Integer> {

    private BandConnectAcitivity bandConnectAcitivity;
    private long duration;
    private long start_time;
    private int added_values;
    private int counter;



    public GetRestingHeartRateTask(long duration) {
        this.start_time = System.currentTimeMillis();
        this.duration = duration;
        this.added_values = 0;
        this.counter = 0;
        this.bandConnectAcitivity = MainActivity.Companion.getBandConnectAcitivity();


    }


    @Override
    public Integer call() {
        while(System.currentTimeMillis() - start_time <= duration) {
            if(bandConnectAcitivity.isConnected()){
                added_values += bandConnectAcitivity.getHeartrate();
                counter++;
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e){
                    return -2;
                }
            }
            else{
               return -1;
            }
        }
        return added_values / counter;
    }
}
