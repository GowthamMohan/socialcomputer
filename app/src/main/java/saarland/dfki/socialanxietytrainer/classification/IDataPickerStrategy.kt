package saarland.dfki.socialanxietytrainer.classification

interface IDataPickerStrategy {
    fun getValue(c: ClassificationKind): Any
    fun getValues(c: ClassificationKind): List<Any>
}