package saarland.dfki.socialanxietytrainer.classification

import java.util.*

/**
 * Does not store the data persistently.
 */
class VolatileMemory : IClassifierMemory {

    private val heartbeatData: LinkedList<Pair<Date, Any>> = LinkedList()
    private val voiceData: LinkedList<Pair<Date, Any>> = LinkedList()
    private val questionnaireData: LinkedList<Pair<Date, Any>> = LinkedList()

    override fun memorize(time: Date, c: ClassificationKind, value: Any) {
        when (c) {
            ClassificationKind.HEARTBEAT -> heartbeatData.add(Pair(time, value))
            ClassificationKind.VOICE -> voiceData.add(Pair(time, value))
            ClassificationKind.QUESTIONNAIRE -> questionnaireData.add(Pair(time, value))
        }
    }

    fun getLastHeartbeatEntry(): Pair<Date, Any> {
        return heartbeatData.last
    }

    fun getLastVoiceEntry(): Pair<Date, Any> {
        return voiceData.last
    }

}