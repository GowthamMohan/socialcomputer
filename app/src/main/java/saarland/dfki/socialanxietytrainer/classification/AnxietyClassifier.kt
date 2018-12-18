package saarland.dfki.socialanxietytrainer.classification

/**
 * More like a real-time classifier
 * TODO
 * - think of a afterwards classifier, then we can also drop questionnaire
 * - considering mapping the ClassificationKind on a Function to avoid if else cascades
 */
class AnxietyClassifier : IAnxietyClassifier {

    override fun classifyAnxietyLevel(classifierData: List<Pair<ClassificationKind, Any>>): AnxietyLevel {
        var heartbeatClass = AnxietyLevel.HIGH
        var voiceClass = AnxietyLevel.HIGH
        var questionnaireClass = AnxietyLevel.HIGH

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


    fun classifyHeartbeat(value: Any): AnxietyLevel {
        TODO("not implemented")
    }

    fun classifyVoice(value: Any): AnxietyLevel {
        TODO("not implemented")
    }

    fun classifyQuestionnaire(value: Any): AnxietyLevel {
        TODO("not implemented")
    }

}