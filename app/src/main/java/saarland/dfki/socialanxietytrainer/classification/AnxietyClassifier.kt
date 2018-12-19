package saarland.dfki.socialanxietytrainer.classification

/**
 * More like a real-time classifier
 * TODO
 * - think of a afterwards classifier, then we can also drop questionnaire
 * - considering mapping the ClassificationKind on a Function to avoid if else cascades
 */
class AnxietyClassifier(dataPickerStrategy: IDataPickerStrategy) : IAnxietyClassifier {

    override fun classifyCurrentAnxietyLevel(classifierData: List<Pair<ClassificationKind, Any>>): AnxietyLevel {
        var heartbeatClass = AnxietyLevel.NONE
        var voiceClass = AnxietyLevel.NONE
        var questionnaireClass = AnxietyLevel.NONE

        assert(classifierData.size <= ClassificationKind.values().size)
        for (data in classifierData) {
            when (data.first) {
                ClassificationKind.HEARTBEAT -> heartbeatClass = classifyHeartbeat(data.second)
                ClassificationKind.VOICE -> voiceClass = classifyVoice(data.second)
                ClassificationKind.QUESTIONNAIRE -> questionnaireClass = classifyQuestionnaire(data.second)
            }
        }

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun classifyOverallAnxietyLevel(): AnxietyLevel {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private fun classifyHeartbeat(value: Any): AnxietyLevel {
        TODO("not implemented")
    }

    /**
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

    private fun classifyQuestionnaire(value: Any): AnxietyLevel {
        TODO("not implemented")
    }

}