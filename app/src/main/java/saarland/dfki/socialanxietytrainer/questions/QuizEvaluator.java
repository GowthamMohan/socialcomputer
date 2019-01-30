package saarland.dfki.socialanxietytrainer.questions;

import android.app.Activity;

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
            Preferences.Companion.setAnxietyLevelCategory1(activity, AnxietyLevel.LOW);
        }
        else if( scores[0] <= 18) {
            Preferences.Companion.setAnxietyLevelCategory1(activity,AnxietyLevel.MILD);
        }
        else {
            Preferences.Companion.setAnxietyLevelCategory1(activity,AnxietyLevel.HIGH);
        }

        //category 2: Speaking in public/talking with people in authority
        if(scores[1] <= 16) {
            Preferences.Companion.setAnxietyLevelCategory2(activity,AnxietyLevel.LOW);
        }
        else if(scores[1] <= 23) {
            Preferences.Companion.setAnxietyLevelCategory2(activity,AnxietyLevel.MILD);
        }
        else {
            Preferences.Companion.setAnxietyLevelCategory2(activity,AnxietyLevel.HIGH);
        }

        //category 3:Interactions with the opposite sex
        if( scores[2] <= 17) {
            Preferences.Companion.setAnxietyLevelCategory3(activity,AnxietyLevel.LOW);
        }
        else if( scores[2] <= 24) {
            Preferences.Companion.setAnxietyLevelCategory3(activity,AnxietyLevel.MILD);
        }
        else {
            Preferences.Companion.setAnxietyLevelCategory3(activity,AnxietyLevel.HIGH);
        }

        //category 4: Criticism and embarrassment
        if(scores[3] <= 17) {
            Preferences.Companion.setAnxietyLevelCategory4(activity,AnxietyLevel.LOW);
        }
        else if( scores[3] <= 24) {
            Preferences.Companion.setAnxietyLevelCategory4(activity,AnxietyLevel.MILD);
        }
        else {
            Preferences.Companion.setAnxietyLevelCategory4(activity,AnxietyLevel.HIGH);
        }

        //category 5: Assertive expression of annoyance, disgust or displeasure
        if( scores[4] <= 17) {
            Preferences.Companion.setAnxietyLevelCategory5(activity,AnxietyLevel.LOW);
        }
        else if( scores[4] <= 24) {
            Preferences.Companion.setAnxietyLevelCategory5(activity,AnxietyLevel.MILD);
        }
        else {
            Preferences.Companion.setAnxietyLevelCategory5(activity,AnxietyLevel.HIGH);
        }

    }


    public void cutoff_values_male() {
        //category 1: Talking with Strangers
        if(scores[0] <= 13) {
            Preferences.Companion.setAnxietyLevelCategory1(activity, AnxietyLevel.LOW);
        }
        else if( scores[0] <= 20) {
            Preferences.Companion.setAnxietyLevelCategory1(activity,AnxietyLevel.MILD);
        }
        else {
            Preferences.Companion.setAnxietyLevelCategory1(activity,AnxietyLevel.HIGH);
        }

        //category 2: Speaking in public/talking with people in authority
        if(scores[1] <= 14) {
            Preferences.Companion.setAnxietyLevelCategory2(activity,AnxietyLevel.LOW);
        }
        else if(scores[1] <= 21) {
            Preferences.Companion.setAnxietyLevelCategory2(activity,AnxietyLevel.MILD);
        }
        else {
            Preferences.Companion.setAnxietyLevelCategory2(activity,AnxietyLevel.HIGH);
        }

        //category 3:Interactions with the opposite sex
        if( scores[2] <= 14) {
            Preferences.Companion.setAnxietyLevelCategory3(activity,AnxietyLevel.LOW);
        }
        else if( scores[2] <= 21) {
            Preferences.Companion.setAnxietyLevelCategory3(activity,AnxietyLevel.MILD);
        }
        else {
            Preferences.Companion.setAnxietyLevelCategory3(activity,AnxietyLevel.HIGH);
        }

        //category 4: Criticism and embarrassment
        if(scores[3] <= 15) {
            Preferences.Companion.setAnxietyLevelCategory4(activity,AnxietyLevel.LOW);
        }
        else if( scores[3] <= 22) {
            Preferences.Companion.setAnxietyLevelCategory4(activity,AnxietyLevel.MILD);
        }
        else {
            Preferences.Companion.setAnxietyLevelCategory4(activity,AnxietyLevel.HIGH);
        }

        //category 5: Assertive expression of annoyance, disgust or displeasure
        if( scores[4] <= 15) {
            Preferences.Companion.setAnxietyLevelCategory5(activity,AnxietyLevel.LOW);
        }
        else if( scores[4] <= 22) {
            Preferences.Companion.setAnxietyLevelCategory5(activity,AnxietyLevel.MILD);
        }
        else {
            Preferences.Companion.setAnxietyLevelCategory5(activity,AnxietyLevel.HIGH);
        }

    }


}
