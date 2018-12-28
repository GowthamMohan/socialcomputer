package saarland.dfki.socialanxietytrainer.classification

interface IAnxietyClassifier {
    fun classifyCurrentAnxietyLevel(classifierData: List<Pair<ClassificationKind, Any>>): AnxietyLevel
    fun classifyOverallAnxietyLevel(): AnxietyLevel
}