package saarland.dfki.socialanxietytrainer.classification

import hcm.ssj.core.Consumer
import hcm.ssj.core.Log
import hcm.ssj.core.SSJFatalException
import hcm.ssj.core.event.Event
import hcm.ssj.core.option.Option
import hcm.ssj.core.option.OptionList
import hcm.ssj.core.stream.Stream
import java.util.*

class ClassificationManager : Consumer() {

    private val memory: IClassifierMemory = PersistentMemory()
    private val dataPickerStrategy: IDataPickerStrategy = SimpleDataPicker(memory)
    private val classifier: IAnxietyClassifier = AnxietyClassifier(dataPickerStrategy)
    private var lastClassification = AnxietyLevel.NONE

    @Synchronized
    fun consume(c: ClassificationKind, data: Any) {
        Log.i("Consume: c: $c  data: $data")
        memory.memorize(Date(), c, data)
        lastClassification = classifier.classifyCurrentAnxietyLevel(listOf(Pair(c, data)))
        Log.i("Current classification: $lastClassification")
    }

    @Synchronized
    fun getCurrentClassification(): AnxietyLevel {
        return lastClassification
    }

    @Synchronized
    fun getOverallClassification(): AnxietyLevel {
        return classifier.classifyOverallAnxietyLevel()
    }

    /**
     * In future we might think of a better separation between classification manager
     * and consumer, but right now we only return this.
     */
    fun getConsumer(c: ClassificationKind): ClassificationManager {
        return this
    }



    /** Emo voice consumer implementation **/

    override fun getOptions(): OptionList {
        return options
    }

    inner class Options
    /**
     *
     */
    internal constructor() : OptionList() {
        val reduceNum = Option("reduceNum", true, Boolean::class.java, "")

        init {
            addOptions()
        }
    }

    val options = Options()


    @Throws(SSJFatalException::class)
    override fun enter(stream_in: Array<Stream>?) {
    }

    override fun consume(stream_in: Array<out Stream>, trigger: Event?) {
        for (k in stream_in.indices) {
            val num = if (options.reduceNum.get()) 1 else stream_in[k].num

            for (i in 0 until num) {
                assert(stream_in[k].dim == 2)
                val valence: Float = stream_in[k].ptrF()[i * stream_in[k].dim + 0]
                val arousal: Float = stream_in[k].ptrF()[i * stream_in[k].dim + 1]
                val p: Pair<Float, Float> = Pair(valence, arousal)
                consume(ClassificationKind.VOICE, p)
            }
        }
    }

    @Throws(SSJFatalException::class)
    override fun flush(stream_in: Array<Stream>?) {
    }

}