package saarland.dfki.socialanxietytrainer.classification

import java.time.Instant
import java.util.*

/**
 * Does not store the data persistently.
 */
class VolatileMemory : IClassifierMemory {

    private val heartbeatData: LinkedList<Pair<Instant, Any>> = LinkedList()
    private val voiceData: LinkedList<Pair<Instant, Any>> = LinkedList()
    private val questionnaireData: LinkedList<Pair<Instant, Any>> = LinkedList()

    override fun memorize(time: Instant, c: ClassificationKind, value: Any) {
        when (c) {
            ClassificationKind.HEARTBEAT -> heartbeatData.add(Pair(time, value))
            ClassificationKind.VOICE -> voiceData.add(Pair(time, value))
            ClassificationKind.QUESTIONNAIRE -> questionnaireData.add(Pair(time, value))
        }
    }

    fun getLastHeartbeatEntry(): Pair<Instant, Any> {
        return heartbeatData.last
    }

    fun getLastVoiceEntry(): Pair<Instant, Any> {
        return voiceData.last
    }

}