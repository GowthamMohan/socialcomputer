package saarland.dfki.socialanxietytrainer.classification

import java.util.*

/**
 * The memory implementation does not store the data persistently.
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

    override fun getLastValue(c: ClassificationKind): Pair<Date, Any> {
        return when (c) {
            ClassificationKind.HEARTBEAT -> heartbeatData.last
            ClassificationKind.VOICE -> voiceData.last
            ClassificationKind.QUESTIONNAIRE -> questionnaireData.last
        }
    }

    override fun getAllValues(c: ClassificationKind): List<Pair<Date, Any>> {
        return when (c) {
            ClassificationKind.HEARTBEAT -> heartbeatData
            ClassificationKind.VOICE -> voiceData
            ClassificationKind.QUESTIONNAIRE -> questionnaireData
        }
    }

}