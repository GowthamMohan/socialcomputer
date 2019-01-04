package saarland.dfki.socialanxietytrainer.audioanalysis

import hcm.ssj.core.ExceptionHandler
import hcm.ssj.core.Pipeline

abstract class BasePipelineRunner : Thread() {

    var ssj: Pipeline? = null

    abstract fun terminate()

    abstract fun isRunning(): Boolean

    fun setExceptionHandler(h: ExceptionHandler) {
        ssj!!.setExceptionHandler(h)
    }

}