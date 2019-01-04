package saarland.dfki.socialanxietytrainer.classification

enum class AnxietyLevel {
    LOW {
        override fun toString() = "LOW"
    },

    MILD {
        override fun toString() = "MILD"
    },

    HIGH {
        override fun toString() = "HIGH"
    },

    NONE {
        override fun toString() = "NONE"
    };

    abstract override fun toString(): String
}

enum class ClassificationKind {
    HEARTBEAT, VOICE, QUESTIONNAIRE
}