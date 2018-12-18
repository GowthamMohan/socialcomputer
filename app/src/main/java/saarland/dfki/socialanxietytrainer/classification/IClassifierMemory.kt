package saarland.dfki.socialanxietytrainer.classification

import java.time.Instant

interface IClassifierMemory {
    fun memorize(time: Instant, c: ClassificationKind, value: Any)
}