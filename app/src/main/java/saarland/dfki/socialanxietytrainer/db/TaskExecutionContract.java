package saarland.dfki.socialanxietytrainer.db;

import android.provider.BaseColumns;

public final class TaskExecutionContract {

    private TaskExecutionContract() {

    }

    public static class TaskExecutionTable implements BaseColumns {
        public static final String TABLE_NAME = "task_execution";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_HEARTBEAT = "heartbeat";
        public static final String COLUMN_VOICE = "voice";
        // TODO For questionnaire, if it is really a question or just a 1-5 star rating?
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_CLASSIFICATION_KIND = "classification_kind";
        public static final String COLUMN_CLASSIFICATION_VAL = "classification_val";
    }
}
