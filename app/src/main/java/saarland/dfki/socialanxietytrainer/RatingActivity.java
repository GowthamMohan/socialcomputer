package saarland.dfki.socialanxietytrainer;

import android.content.Intent;
import android.os.Bundle;
import android.os.ProxyFileDescriptorCallback;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;

import saarland.dfki.socialanxietytrainer.classification.ClassificationKind;

public class RatingActivity extends AppCompatActivity {

    RatingBar ratingBar;
    float comfortableness_rating;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                comfortableness_rating = v;
                Log.d("TAG",Float.toString(comfortableness_rating));
            }
        });
    }

    public void submitRating(View v){

        levelUp();

        MainActivity.Companion.getClassificationManager().consume(ClassificationKind.QUESTIONNAIRE,comfortableness_rating);
        Intent intent = new Intent(RatingActivity.this,MainActivity.class);
        startActivity(intent);

    }

    private void levelUp() {
        int count =  Preferences.Companion.getStreakCompleted(this);
        if(count == 2) {

            switch (getIntent().getIntExtra("category_id",0)) {
                case(0) :
                    Preferences.Companion.setLevelC1( this,Preferences.Companion.getLevelC1(this) + 1 % 4);
                   break;
                case(1):
                    Preferences.Companion.setLevelC2( this,Preferences.Companion.getLevelC2(this) + 1 % 4);
                    break;
                case(2):
                    Preferences.Companion.setLevelC3( this,Preferences.Companion.getLevelC3(this) + 1 % 4);
                    break;
                case(3):
                    Preferences.Companion.setLevelC4( this,Preferences.Companion.getLevelC4(this) + 1 % 4);
                    break;
                case(4):
                    Preferences.Companion.setLevelC5( this,Preferences.Companion.getLevelC5(this) + 1 % 4);
                    break;
                default:break;
            }


        }

        count = (count++)% 3;
        Preferences.Companion.setStreakCompleted(this,count);

    }


}
