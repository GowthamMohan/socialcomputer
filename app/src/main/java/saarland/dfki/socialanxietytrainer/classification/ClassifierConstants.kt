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

    companion object {
        fun fromString(s: String): AnxietyLevel {
            return valueOf(s)
        }
    }
}

enum class ClassificationKind {
    HEARTBEAT {
        override fun toString() = "HEARTBEAT"
    },

    VOICE {
        override fun toString() = "VOICE"
    },

    QUESTIONNAIRE {
        override fun toString() = "QUESTIONNAIRE"
    };

    abstract override fun toString(): String
}