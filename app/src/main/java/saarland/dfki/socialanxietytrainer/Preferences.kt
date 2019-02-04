package saarland.dfki.socialanxietytrainer

import android.app.Activity
import android.content.Context
import android.widget.TextView
import saarland.dfki.socialanxietytrainer.classification.AnxietyLevel

class Preferences {

    companion object {
        private const val SCORE_PREFS_NAME = "score"
        private const val ANXIETY_LEVEL_PREFS_NAME = "anxiety_level"
        private const val RESTING_HEART_RATE_NAME = "resting_heart_rate"
        private const val GENDER_NAME = "gender"

        private const val ANXIETY_LEVEL_CATETORY_1_NAME = "anxiety_level_category_1"
        private const val ANXIETY_LEVEL_CATETORY_2_NAME = "anxiety_level_category_2"
        private const val ANXIETY_LEVEL_CATETORY_3_NAME = "anxiety_level_category_3"
        private const val ANXIETY_LEVEL_CATETORY_4_NAME = "anxiety_level_category_4"
        private const val ANXIETY_LEVEL_CATETORY_5_NAME = "anxiety_level_category_5"



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


        /** Resting heart rate **/

        fun getRestingHeartRate(c: Context): Int {
            val restingHeartRate = c.getSharedPreferences(c.getString(R.string.shared_preferences), Context.MODE_PRIVATE).getInt(RESTING_HEART_RATE, -1);
            return restingHeartRate
        }

        fun setRestingHeartRate(c: Context, resting_heart_rate: Int) {
            val sharedPreferences = c.getSharedPreferences(
                    c.getString(R.string.shared_preferences), Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putInt(RESTING_HEART_RATE, resting_heart_rate)
            editor.commit()
        }

        /** set anxiety levels for categories **/


        fun setAnxietyLevelCategory1(c:Context,level:AnxietyLevel) {
            val shared_preferences = c.getSharedPreferences("shared", Context.MODE_PRIVATE)
            val editor = shared_preferences.edit()
            editor.putString(ANXIETY_LEVEL_CATETORY_1_NAME, level.toString())
            editor.commit()

        }
        fun setAnxietyLevelCategory2(c:Context,level:AnxietyLevel) {
            val shared_preferences = c.getSharedPreferences("shared", Context.MODE_PRIVATE)
            val editor = shared_preferences.edit()
            editor.putString(ANXIETY_LEVEL_CATETORY_1_NAME, level.toString())
            editor.commit()

        }
        fun setAnxietyLevelCategory3(c:Context,level:AnxietyLevel) {
            val shared_preferences = c.getSharedPreferences("shared", Context.MODE_PRIVATE)
            val editor = shared_preferences.edit()
            editor.putString(ANXIETY_LEVEL_CATETORY_1_NAME, level.toString())
            editor.commit()

        }
        fun setAnxietyLevelCategory4(c:Context,level:AnxietyLevel) {
            val shared_preferences = c.getSharedPreferences("shared", Context.MODE_PRIVATE)
            val editor = shared_preferences.edit()
            editor.putString(ANXIETY_LEVEL_CATETORY_1_NAME, level.toString())
            editor.commit()

        }
        fun setAnxietyLevelCategory5(c:Context,level:AnxietyLevel) {
            val shared_preferences = c.getSharedPreferences("shared", Context.MODE_PRIVATE)
            val editor = shared_preferences.edit()
            editor.putString(ANXIETY_LEVEL_CATETORY_1_NAME, level.toString())
            editor.commit()

        }

        /** get anxiety levels for categories **/

        fun getAnxietyLevelCatgeory1(c:Context): AnxietyLevel {
            val anxietyLevel = c.getSharedPreferences("shared",Context.MODE_PRIVATE).getString(ANXIETY_LEVEL_CATETORY_1_NAME,"None")?: "None"
            return AnxietyLevel.fromString(anxietyLevel)
        }
        fun getAnxietyLevelCatgeory2(c:Context): AnxietyLevel {
            val anxietyLevel = c.getSharedPreferences("shared",Context.MODE_PRIVATE).getString(ANXIETY_LEVEL_CATETORY_2_NAME,"None")?: "None"
            return AnxietyLevel.fromString(anxietyLevel)
        }
        fun getAnxietyLevelCatgeory3(c:Context): AnxietyLevel {
            val anxietyLevel = c.getSharedPreferences("shared",Context.MODE_PRIVATE).getString(ANXIETY_LEVEL_CATETORY_3_NAME,"None")?: "None"
            return AnxietyLevel.fromString(anxietyLevel)
        }
        fun getAnxietyLevelCatgeory4(c:Context): AnxietyLevel {
            val anxietyLevel = c.getSharedPreferences("shared",Context.MODE_PRIVATE).getString(ANXIETY_LEVEL_CATETORY_4_NAME,"None")?: "None"
            return AnxietyLevel.fromString(anxietyLevel)
        }
        fun getAnxietyLevelCatgeory5(c:Context): AnxietyLevel {
            val anxietyLevel = c.getSharedPreferences("shared",Context.MODE_PRIVATE).getString(ANXIETY_LEVEL_CATETORY_5_NAME,"None")?: "None"
            return AnxietyLevel.fromString(anxietyLevel)
        }


        /** gender **/
        fun getGender(c:Context): String {
            val gender = c.getSharedPreferences("shared",Context.MODE_PRIVATE).getString(GENDER_NAME,"None");
            return gender;
        }

        fun setGender(c:Context, gender:String) {
            assert(gender == "Female"|| gender == "Male")
            val shared_preferences = c.getSharedPreferences("shared", Context.MODE_PRIVATE)
            val editor = shared_preferences.edit()
            editor.putString(GENDER_NAME, gender)
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