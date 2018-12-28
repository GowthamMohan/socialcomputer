package saarland.dfki.socialanxietytrainer.audioanalysis

interface IPipeLineExecutor {
    fun notifyPipeState(running: Boolean)
}