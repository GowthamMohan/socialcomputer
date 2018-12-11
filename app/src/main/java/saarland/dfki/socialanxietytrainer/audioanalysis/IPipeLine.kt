package saarland.dfki.socialanxietytrainer.audioanalysis

interface IPipeLine {
    fun notifyPipeState(running: Boolean)
}