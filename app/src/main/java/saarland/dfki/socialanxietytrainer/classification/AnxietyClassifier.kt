package saarland.dfki.socialanxietytrainer.classification

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v4.content.ContextCompat.startActivity
import com.estimote.sdk.cloud.internal.ApiUtils.getSharedPreferences
import saarland.dfki.socialanxietytrainer.MainActivity
import saarland.dfki.socialanxietytrainer.RestingHeartRateActivity

/**
 * More like a real-time classifier
 * TODO
 * - think of a afterwards classifier, then we can also drop questionnaire
 * - considering mapping the ClassificationKind on a Function to avoid if else cascades
 */
class AnxietyClassifier(private val dataPickerStrategy: IDataPickerStrategy) : IAnxietyClassifier {

    /**
     * Weight maps for weighting each classification kind. The weights must sum up to 1.0.
     *
     * Use [weightMap_HBeat_Voice] for current classification and [weightMap_HBeat_Voice_Quest]
     * for overall classification, since there is no questionnaire while doing the task.
     */
    private val weightMap_HBeat_Voice = hashMapOf(ClassificationKind.HEARTBEAT to 0.5,
            ClassificationKind.VOICE to 0.5)
    private val weightMap_HBeat_Voice_Quest = hashMapOf(ClassificationKind.HEARTBEAT to 0.4,
            ClassificationKind.VOICE to 0.4,
            ClassificationKind.QUESTIONNAIRE to 0.2)

    private val weightMap_AnxietyLevel = hashMapOf(AnxietyLevel.LOW to 0.33,
            AnxietyLevel.MILD to 0.33,
            AnxietyLevel.HIGH to 0.33)

    private  var sharedPreferences: SharedPreferences? = null

    override fun classifyCurrentAnxietyLevel(classifierData: List<Pair<ClassificationKind, Any>>): AnxietyLevel {
        assert(classifierData.size <= ClassificationKind.values().size)

        // The passed classifierData is already added to memory. We only classify the values
        // returned by the dataPickerStrategy.
        val heartbeatClass = classifyHeartbeat()
        val voiceClass = classifyVoice()

        assert(heartbeatClass != AnxietyLevel.NONE || voiceClass != AnxietyLevel.NONE)
        if (heartbeatClass == AnxietyLevel.NONE) {
            // voiceClass != AnxietyLevel.NONE
            return voiceClass
        } else if (voiceClass == AnxietyLevel.NONE) {
            // heartbeatClass != AnxietyLevel.NONE
            return heartbeatClass
        } else {
            // heartbeatClass != AnxietyLevel.NONE && voiceClass != AnxietyLevel.NONE
            val heartbeatClassW = weightMap_AnxietyLevel.getValue(heartbeatClass)
            val voiceClassW = weightMap_AnxietyLevel.getValue(voiceClass)

            val continuousVal = (heartbeatClassW * weightMap_HBeat_Voice.getValue(ClassificationKind.HEARTBEAT)
                                + voiceClassW * weightMap_HBeat_Voice.getValue(ClassificationKind.VOICE)) / 2

            return continuousToDiscreteAnxietyLevel(continuousVal)
        }

    }

    private fun continuousToDiscreteAnxietyLevel(value: Double): AnxietyLevel {
        return when {
            value <= 0.33 -> AnxietyLevel.LOW
            value <= 0.66 -> AnxietyLevel.MILD
            else -> AnxietyLevel.HIGH
        }
    }

    override fun classifyOverallAnxietyLevel(): AnxietyLevel {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private fun classifyHeartbeat(value: Any): AnxietyLevel {
        assert(value is Int)
        assert(value in 0 .. 220)
        val heartrate = value as Int
        var restingHeartRate = MainActivity.bandConnectAcitivity!!.restingHeartRate
        if(restingHeartRate == -1) {
            restingHeartRate = 60 //default value because not set yet
        }
        return when {
            heartrate  <= restingHeartRate + 20 -> AnxietyLevel.LOW
            heartrate in restingHeartRate + 21 .. restingHeartRate + 40 -> AnxietyLevel.MILD
            else -> AnxietyLevel.HIGH
        }

    }

    private fun classifyHeartbeat(): AnxietyLevel {
        return try {
            val heartbeatVal = dataPickerStrategy.getValue(ClassificationKind.HEARTBEAT)
            classifyHeartbeat(heartbeatVal)
        } catch (e: NoSuchElementException) {
            AnxietyLevel.NONE
        }
    }

    /**
     * TODO
     * Very simple classification.
     */
    private fun classifyVoice(value: Any): AnxietyLevel {
        val valence = (value as Pair<Float, Float>).first
        val arousal = (value as Pair<Float, Float>).second

        assert(valence in 0.0..1.0)
        assert(arousal in 0.0..1.0)

        return when {
            arousal < 0.3 -> AnxietyLevel.LOW
            arousal < 0.6 -> AnxietyLevel.MILD
            else -> AnxietyLevel.HIGH
        }
    }

    private fun classifyVoice(): AnxietyLevel {
        return try {
            val voiceVal = dataPickerStrategy.getValue(ClassificationKind.VOICE)
            classifyVoice(voiceVal)
        } catch (e: NoSuchElementException) {
            AnxietyLevel.NONE
        }
    }

    private fun classifyQuestionnaire(value: Any): AnxietyLevel {
        TODO("not implemented")
    }

}