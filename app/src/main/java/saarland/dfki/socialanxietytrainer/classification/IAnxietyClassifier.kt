package saarland.dfki.socialanxietytrainer.classification

interface IAnxietyClassifier {
    fun classifyAnxietyLevel(classifierData: List<Pair<ClassificationKind, Any>>): AnxietyLevel
}