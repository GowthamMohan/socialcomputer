package saarland.dfki.socialanxietytrainer

import android.app.Activity
import android.content.Context
import android.widget.TextView

class ScoreManager {
    companion object {
        const val SHARED_PREFS_NAME = "shared"
        private const val SCORE_PREFS_NAME = "score"

        private fun getScore(c: Context): Int {
            val settings = c.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
            // Default value is 0
            return settings.getInt(SCORE_PREFS_NAME, 0)
        }

        private fun setScore(c: Context, score: Int) {
            val settings = c.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
            val editor = settings.edit()
            editor.putInt(SCORE_PREFS_NAME, score)
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
    }
}