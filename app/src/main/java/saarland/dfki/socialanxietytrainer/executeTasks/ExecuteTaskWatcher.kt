package saarland.dfki.socialanxietytrainer.executeTasks

import android.app.Activity
import android.widget.TextView
import saarland.dfki.socialanxietytrainer.R
import saarland.dfki.socialanxietytrainer.classification.ClassificationManager
import java.util.concurrent.atomic.AtomicBoolean

/**
 * The [ExecuteTaskWatcher] checks the current classification in a certain time interval.
 * TODO: Check here later for 'dangerous' anxiety levels and give a warning.
 */
class ExecuteTaskWatcher(private val parentActivity: Activity, private val cm: ClassificationManager) : Thread() {
    private val terminate = AtomicBoolean(false)
    private val timeInterval: Long = 1000

    override fun run() {
        while (!terminate.get()) {
            parentActivity.runOnUiThread {
                val textView = parentActivity.findViewById<TextView>(R.id.textView_curr_anxiety_level)
                textView.text = "Current anxiety level: ${cm.getCurrentClassification()}"
                textView.invalidate()
            }

            sleep(timeInterval)
        }
    }

    fun terminate() {
        terminate.set(true)
    }
}