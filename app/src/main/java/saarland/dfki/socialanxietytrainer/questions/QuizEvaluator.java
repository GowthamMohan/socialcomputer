package saarland.dfki.socialanxietytrainer.questions;

import android.app.Activity;

import saarland.dfki.socialanxietytrainer.MainActivity;
import saarland.dfki.socialanxietytrainer.Preferences;
import saarland.dfki.socialanxietytrainer.classification.AnxietyLevel;

public class QuizEvaluator {

    private int[] scores;
    private Activity activity;

    public QuizEvaluator(int[] scores, Activity activity) {
        this.scores = scores;
        this.activity = activity;
    }


    public void cutoff_values_female() {
        //category 1: Talking with Strangers
        if(scores[0] <= 11) {
            Preferences.Companion.setLevelC1(MainActivity.Companion.getContext(),3 -1);
        }
        else if( scores[0] <= 18) {
            Preferences.Companion.setLevelC1(MainActivity.Companion.getContext(),2 -1);
        }
        else {
            Preferences.Companion.setLevelC1(MainActivity.Companion.getContext(),1);
        }

        //category 2: Speaking in public/talking with people in authority
        if(scores[1] <= 16) {
            Preferences.Companion.setLevelC2(MainActivity.Companion.getContext(),3 -1);
        }
        else if(scores[1] <= 23) {
            Preferences.Companion.setLevelC2(MainActivity.Companion.getContext(),2 -1);
        }
        else {
            Preferences.Companion.setLevelC2(MainActivity.Companion.getContext(),1);
        }

        //category 3:Interactions with the opposite sex
        if( scores[2] <= 17) {
            Preferences.Companion.setLevelC3(MainActivity.Companion.getContext(),3 -1);
        }
        else if( scores[2] <= 24) {
            Preferences.Companion.setLevelC3(MainActivity.Companion.getContext(),2 -1);
        }
        else {
            Preferences.Companion.setLevelC3(MainActivity.Companion.getContext(),1);
        }

        //category 4: Criticism and embarrassment
        if(scores[3] <= 17) {
            Preferences.Companion.setLevelC4(MainActivity.Companion.getContext(),3 -1);
        }
        else if( scores[3] <= 24) {
            Preferences.Companion.setLevelC4(MainActivity.Companion.getContext(),2 -1);
        }
        else {
        Preferences.Companion.setLevelC4(MainActivity.Companion.getContext(),1);
       }
        //category 5: Assertive expression of annoyance, disgust or displeasure
        if( scores[4] <= 17) {
             Preferences.Companion.setLevelC5(MainActivity.Companion.getContext(),3 -1);
        }
        else if( scores[4] <= 24) {
        Preferences.Companion.setLevelC5(MainActivity.Companion.getContext(),2 -1);
        }
        else {
                Preferences.Companion.setLevelC5(MainActivity.Companion.getContext(),1);
        }

    }
    public void cutoff_values_male() {
        //category 1: Talking with Strangers
        if(scores[0] <= 11) {
            Preferences.Companion.setLevelC1(MainActivity.Companion.getContext(),3 -1);
        }
        else if( scores[0] <= 18) {
            Preferences.Companion.setLevelC1(MainActivity.Companion.getContext(),2 -1);
        }
        else {
            Preferences.Companion.setLevelC1(MainActivity.Companion.getContext(),1);
        }

        //category 2: Speaking in public/talking with people in authority
        if(scores[1] <= 16) {
            Preferences.Companion.setLevelC2(MainActivity.Companion.getContext(),3 -1);
        }
        else if(scores[1] <= 23) {
            Preferences.Companion.setLevelC2(MainActivity.Companion.getContext(),2 -1);
        }
        else {
            Preferences.Companion.setLevelC2(MainActivity.Companion.getContext(),1);
        }

        //category 3:Interactions with the opposite sex
        if( scores[2] <= 17) {
            Preferences.Companion.setLevelC3(MainActivity.Companion.getContext(),3 -1);
        }
        else if( scores[2] <= 24) {
            Preferences.Companion.setLevelC3(MainActivity.Companion.getContext(),2 -1);
        }
        else {
            Preferences.Companion.setLevelC3(MainActivity.Companion.getContext(),1);
        }

        //category 4: Criticism and embarrassment
        if(scores[3] <= 17) {
            Preferences.Companion.setLevelC4(MainActivity.Companion.getContext(),3 -1);
        }
        else if( scores[3] <= 24) {
            Preferences.Companion.setLevelC4(MainActivity.Companion.getContext(),2 -1);
        }
        else {
            Preferences.Companion.setLevelC4(MainActivity.Companion.getContext(),1);
        }
        //category 5: Assertive expression of annoyance, disgust or displeasure
        if( scores[4] <= 17) {
            Preferences.Companion.setLevelC5(MainActivity.Companion.getContext(),3 -1);
        }
        else if( scores[4] <= 24) {
            Preferences.Companion.setLevelC5(MainActivity.Companion.getContext(),2 -1);
        }
        else {
            Preferences.Companion.setLevelC5(MainActivity.Companion.getContext(),1);
        }

    }






}
