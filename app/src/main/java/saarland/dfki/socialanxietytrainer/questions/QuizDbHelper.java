package saarland.dfki.socialanxietytrainer.questions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import saarland.dfki.socialanxietytrainer.questions.QuizContract.*;


import java.util.ArrayList;
import java.util.List;


public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "InitialQuestionnaire.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION4 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION5 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER" +
                ") ";


        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    private void fillQuestionsTable() {
        /* Question q1 = new Question("A is correct", "A", "B", "C", 1);
        addQuestion(q1);
        Question q2 = new Question("B is correct", "A", "B", "C", 2);
        addQuestion(q2);
        Question q3 = new Question("C is correct", "A", "B", "C", 3);
        addQuestion(q3);
        Question q4 = new Question("A is correct again", "A", "B", "C", 1);
        addQuestion(q4);
        Question q5 = new Question("B is correct again", "A", "B", "C", 2);
        addQuestion(q5); */
        Question q1 = new Question("1. Greeting someone and being ignored", "Not at all or very slight", "Slight", "Moderate", "High", "Extremely High", 1);
        addQuestion(q1);
        Question q2 = new Question("2. Having to ask a neighbor to stop making noise", "Not at all or very slight", "Slight", "Moderate", "High", "Extremely High", 1);
        addQuestion(q2);
        Question q3 = new Question("3. Speaking in public", "Not at all or very slight", "Slight", "Moderate", "High", "Extremely High", 2);
        addQuestion(q3);
        Question q4 = new Question("4. Asking someone I find attractive for a date", "Not at all or very slight", "Slight", "Moderate", "High", "Extremely High", 3);
        addQuestion(q4);
        Question q5 = new Question("5. Complaining to the waiter about my food ", "Not at all or very slight", "Slight", "Moderate", "High", "Extremely High", 1);
        addQuestion(q5);
        Question q6 = new Question("6. Feeling watched by people I find attractive ", "Not at all or very slight", "Slight", "Moderate", "High", "Extremely High", 2);
        addQuestion(q6);
        Question q7 = new Question("7. Participating in a meeting with people in authority", "Not at all or very slight", "Slight", "Moderate", "High", "Extremely High", 1);
        addQuestion(q7);
        Question q8 = new Question("8. Talking to someone who isnâ€™t paying attention to what I am saying", "Not at all or very slight", "Slight", "Moderate", "High", "Extremely High", 2);
        addQuestion(q8);
        Question q9 = new Question("9. Refusing when asked to do something I donâ€™t like doing", "Not at all or very slight", "Slight", "Moderate", "High", "Extremely High", 2);
        addQuestion(q9);
        Question q10 = new Question("10. Making new friends", "Not at all or very slight", "Slight", "Moderate", "High", "Extremely High", 3);
        addQuestion(q10);
        Question q11 = new Question("11. Telling someone that they have hurt my feelings", "Not at all or very slight", "Slight", "Moderate", "High", "Extremely High", 1);
        addQuestion(q11);
        Question q12 = new Question("12.Having to speak in class, at work, or in a meeting ", "Not at all or very slight", "Slight", "Moderate", "High", "Extremely High", 1);
        addQuestion(q12);
        Question q13 = new Question("13. Maintaining a conversation with someone Iâ€™ve just met", "Not at all or very slight", "Slight", "Moderate", "High", "Extremely High", 4);
        addQuestion(q13);
        Question q14 = new Question("14. Expressing my annoyance to someone that is picking on me ", "Not at all or very slight", "Slight", "Moderate", "High", "Extremely High", 2);
        addQuestion(q14);
        Question q15 = new Question("15. Greeting each person at a social meeting when I donâ€™t know most of them ", "Not at all or very slight", "Slight", "Moderate", "High", "Extremely High", 1);
        addQuestion(q15);
        Question q16 = new Question("16. Being teased in public", "Not at all or very slight", "Slight", "Moderate", "High", "Extremely High", 4);
        addQuestion(q16);
        Question q17 = new Question("17. Talking to people I donâ€™t know at a party or a meeting", "Not at all or very slight", "Slight", "Moderate", "High", "Extremely High", 2);
        addQuestion(q17);
        Question q18 = new Question("18. Being asked a question in class by the teacher or by a superior in a meeting", "Not at all or very slight", "Slight", "Moderate", "High", "Extremely High", 1);
        addQuestion(q18);
        Question q19 = new Question("19. Looking into the eyes of someone I have just met while we are talking", "Not at all or very slight", "Slight", "Moderate", "High", "Extremely High", 2);
        addQuestion(q19);
        Question q20 = new Question("20. Being asked out by a person I am attracted to", "Not at all or very slight", "Slight", "Moderate", "High", "Extremely High", 4);
        addQuestion(q20);
        Question q21 = new Question("21. Making a mistake in front of other people", "Not at all or very slight", "Slight", "Moderate", "High", "Extremely High", 2);
        addQuestion(q21);
        Question q22 = new Question("22. Attending a social event where I know only one person", "Not at all or very slight", "Slight", "Moderate", "High", "Extremely High", 1);
        addQuestion(q22);
        Question q23 = new Question("23. Starting a conversation with someone I find attractive", "Not at all or very slight", "Slight", "Moderate", "High", "Extremely High", 4);
        addQuestion(q23);
        Question q24 = new Question("24. Being reprimanded about something I have done wrong", "Not at all or very slight", "Slight", "Moderate", "High", "Extremely High", 2);
        addQuestion(q24);
        Question q25 = new Question("25. While having dinner with colleagues, classmates or workmates, being asked to speak on behalf of the entire group", "Not at all or very slight", "Slight", "Moderate", "High", "Extremely High", 1);
        addQuestion(q25);
        Question q26 = new Question("26. Telling someone that their behavior bothers me and asking them to stop", "Not at all or very slight", "Slight", "Moderate", "High", "Extremely High", 4);
        addQuestion(q26);
        Question q27 = new Question("27. Asking someone I find attractive to dance", "Not at all or very slight", "Slight", "Moderate", "High", "Extremely High", 2);
        addQuestion(q27);
        Question q28 = new Question("28. Being criticized", "Not at all or very slight", "Slight", "Moderate", "High", "Extremely High", 4);
        addQuestion(q28);
        Question q29 = new Question("29. Talking to a superior or a person in authority ", "Not at all or very slight", "Slight", "Moderate", "High", "Extremely High", 1);
        addQuestion(q29);
        Question q30 = new Question("30. Telling someone I am attracted to that I would like to get to know them better", "Not at all or very slight", "Slight", "Moderate", "High", "Extremely High", 2);
        addQuestion(q30);
    }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_OPTION4, question.getOption4());
        cv.put(QuestionsTable.COLUMN_OPTION5, question.getOption5());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
                question.setOption5(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION5)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}