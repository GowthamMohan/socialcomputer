package saarland.dfki.socialanxietytrainer

import android.app.Activity
import android.content.Context
import android.widget.TextView
import saarland.dfki.socialanxietytrainer.classification.AnxietyLevel

class Preferences {

    companion object {
        private const val SCORE_PREFS_NAME = "score"
        private const val ANXIETY_LEVEL_PREFS_NAME = "score"
        private const val RESTING_HEART_RATE = "resting_heart_rate"


        /** Score **/

        private fun getScore(c: Context): Int {
            val settings = c.getSharedPreferences(
                    c.getString(R.string.shared_preferences), Context.MODE_PRIVATE)
            // Default value is 0
            return settings.getInt(SCORE_PREFS_NAME, 0)
        }

        private fun setScore(c: Context, score: Int) {
            val settings = c.getSharedPreferences(
                    c.getString(R.string.shared_preferences), Context.MODE_PRIVATE)
            val editor = settings.edit()
            editor.putInt(SCORE_PREFS_NAME, score)
            editor.commit()
        }

        /** resting heart rate **/

        fun getRestingHeartRate(c: Context): Int {
            val resting_heart_rate = c.getSharedPreferences(c.getString(R.string.shared_preferences), Context.MODE_PRIVATE).getInt(RESTING_HEART_RATE, -1);
            return resting_heart_rate
        }

            val shared_preferences = c.getSharedPreferences(
        fun setRestingHeartRate(c: Context, resting_heart_rate: Int) {
                    c.getString(R.string.shared_preferences), Context.MODE_PRIVATE)
            val editor = shared_preferences.edit()
            editor.putInt(RESTING_HEART_RATE, resting_heart_rate)
            editor.commit()
        }
        fun updateScoreLabel(a: Activity, score: Int) {
            setScore(a.applicationContext, score)
            val scoreLabel = a.findViewById<TextView>(R.id.textView_score)
            scoreLabel.text = "${a.getString(R.string.score_title)} $score"
        }

        fun setupScoreLabel(a: Activity) {
            val score = getScore(a)
            updateScoreLabel(a, score)
        }


        /** Anxiety level **/

        fun getAnxietyLevel(c: Context): AnxietyLevel {
            val settings = c.getSharedPreferences(
                    c.getString(R.string.shared_preferences), Context.MODE_PRIVATE)
            // Default value is AnxietyLevel.HIGH
            val levelStr = settings.getString(ANXIETY_LEVEL_PREFS_NAME, "HIGH") ?: "HIGH"
            return AnxietyLevel.fromString(levelStr)
        }

        fun setAnxietyLevel(c: Context, level: AnxietyLevel) {
            val settings = c.getSharedPreferences(
                    c.getString(R.string.shared_preferences), Context.MODE_PRIVATE)
            val editor = settings.edit()
            editor.putString(ANXIETY_LEVEL_PREFS_NAME, level.toString())
            editor.commit()
        }



    }
}