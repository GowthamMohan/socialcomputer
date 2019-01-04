package saarland.dfki.socialanxietytrainer.classification

import java.util.*

interface IClassifierMemory {
    fun memorize(time: Date, c: ClassificationKind, value: Any)
    fun getLastValue(c: ClassificationKind): Pair<Date, Any>
    fun getAllValues(c: ClassificationKind): List<Pair<Date, Any>>
}