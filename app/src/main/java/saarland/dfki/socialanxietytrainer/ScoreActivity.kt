package saarland.dfki.socialanxietytrainer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class ScoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        Preferences.setupScoreLabel(this)
    }

}