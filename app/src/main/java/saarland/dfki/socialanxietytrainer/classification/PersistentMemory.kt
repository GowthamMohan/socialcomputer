package saarland.dfki.socialanxietytrainer.classification

import saarland.dfki.socialanxietytrainer.MainActivity
import java.util.*

/**
 * The class stores the data persistently in database.
 */
class PersistentMemory : IClassifierMemory {

    private val volatileMemory = VolatileMemory()

    override fun memorize(time: Date, c: ClassificationKind, value: Any) {
        volatileMemory.memorize(time, c, value)
        MainActivity.dbHelper!!.addClassificationValue(time, c, value.toString())
    }

    override fun getLastValue(c: ClassificationKind): Pair<Date, Any> {
        return volatileMemory.getLastValue(c)
    }

    override fun getAllValues(c: ClassificationKind): List<Pair<Date, Any>> {
        return volatileMemory.getAllValues(c)
    }

}