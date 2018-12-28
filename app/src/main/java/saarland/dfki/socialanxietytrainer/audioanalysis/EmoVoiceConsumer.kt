package saarland.dfki.socialanxietytrainer.audioanalysis

import hcm.ssj.core.Consumer
import hcm.ssj.core.Log
import hcm.ssj.core.SSJFatalException
import hcm.ssj.core.event.Event
import hcm.ssj.core.option.Option
import hcm.ssj.core.option.OptionList
import hcm.ssj.core.stream.Stream

class EmoVoiceConsumer : Consumer() {


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

        var msg: String;
        for (k in stream_in.indices) {
            val num = if (options.reduceNum.get()) 1 else stream_in[k].num
            msg = "EmoVoiceConsumer: "
            for (i in 0 until num) {
                for (j in 0 until stream_in[k].dim) {
                    msg += "" + stream_in[k].ptrF()[i * stream_in[k].dim + j] + " "
                }
            }
            Log.i(msg)
        }
    }

    @Throws(SSJFatalException::class)
    override fun flush(stream_in: Array<Stream>?) {
    }

}