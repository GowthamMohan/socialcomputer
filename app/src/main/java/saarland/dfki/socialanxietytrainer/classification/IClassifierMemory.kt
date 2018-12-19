package saarland.dfki.socialanxietytrainer.classification

import java.util.*

interface IClassifierMemory {
    fun memorize(time: Date, c: ClassificationKind, value: Any)
}