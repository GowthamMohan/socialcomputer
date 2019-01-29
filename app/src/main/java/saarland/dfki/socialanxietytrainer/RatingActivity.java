package saarland.dfki.socialanxietytrainer;

import android.content.Intent;
import android.os.Bundle;
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
        MainActivity.Companion.getClassificationManager().consume(ClassificationKind.QUESTIONNAIRE,comfortableness_rating);
        Intent intent = new Intent(RatingActivity.this,MainActivity.class);
        startActivity(intent);

    }
}
