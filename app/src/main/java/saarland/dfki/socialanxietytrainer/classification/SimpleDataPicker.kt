package saarland.dfki.socialanxietytrainer.classification

class SimpleDataPicker(private val memory: IClassifierMemory) : IDataPickerStrategy {
    override fun getValues(c: ClassificationKind): List<Any> {
        return memory.getAllValues(c).map { it.second }
    }

    override fun getValue(c: ClassificationKind): Any {
        return memory.getLastValue(c).second
    }
}