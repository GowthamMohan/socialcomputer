package saarland.dfki.socialanxietytrainer;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import saarland.dfki.socialanxietytrainer.classification.AnxietyLevel;
import saarland.dfki.socialanxietytrainer.db.DbHelper;
import saarland.dfki.socialanxietytrainer.questions.Question;
import saarland.dfki.socialanxietytrainer.questions.QuizEvaluator;

public class QuizActivity extends AppCompatActivity {
    private TextView textViewQuestion;

    private TextView textViewQuestionCount;

    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private RadioButton rb5;
    private Button buttonConfirmNext;

    private ColorStateList textColorDefaultRb;

    private List<Question> questionList;
    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;

    private int score;
    private boolean answered;


    private int[] scores;

    private QuizEvaluator quizEvaluator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        DbHelper dbHelper = DbHelper.getInstace(this);

        scores = new int[]{0,0,0,0,0};

        textViewQuestion = findViewById(R.id.text_view_question);
        /* textViewScore = findViewById(R.id.text_view_score);
        textViewCountDown = findViewById(R.id.text_view_countdown); */
        textViewQuestionCount = findViewById(R.id.text_view_question_count);
        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        rb4 = findViewById(R.id.radio_button4);
        rb5 = findViewById(R.id.radio_button5);
        buttonConfirmNext = findViewById(R.id.button_confirm_next);

        textColorDefaultRb = rb1.getTextColors();

        questionList = dbHelper.getAllQuestions();
        questionCountTotal = questionList.size();
        /* Collections.shuffle(questionList); */

        showNextQuestion();

        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked()|| rb4.isChecked()|| rb5.isChecked()) {
                        processSelection();
                        showNextQuestion();

                    } else {
                        Toast.makeText(QuizActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void showNextQuestion() {
        rb1.setTextColor(textColorDefaultRb);
        rb2.setTextColor(textColorDefaultRb);
        rb3.setTextColor(textColorDefaultRb);
        rb4.setTextColor(textColorDefaultRb);
        rb5.setTextColor(textColorDefaultRb);
        rbGroup.clearCheck();

        if (questionCounter < questionCountTotal) {
            currentQuestion = questionList.get(questionCounter);

            textViewQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());
            rb5.setText(currentQuestion.getOption5());

            questionCounter++;
            textViewQuestionCount.setText("Question: " + questionCounter + "/" + questionCountTotal);
            answered = false;
            /* buttonConfirmNext.setText("Confirm"); */
        } else {
            computeAnxietyLevels();
            finishQuiz();
        }
    }

    //returns score of currently selected answer
    private int computeAnswerScore() {
        int tmp_score = 0;
        if(rb1.isSelected()) {
            tmp_score = 1;
        }
        else if(rb2.isSelected()) {
            tmp_score = 2;
        } else if (rb3.isSelected()) {
            tmp_score = 3;
        }
        else if (rb4.isSelected()) {
            tmp_score = 4;
        }
        else if(rb5.isSelected()){
            tmp_score = 5;
        }
        return tmp_score;
    }


    private void processSelection() {
        int id = questionCounter  + 1;
        int tmp_score = computeAnswerScore();
        if(Arrays.asList(10, 13, 15, 17, 19, 22).contains(id)) {
            //interactions with strangers
          scores[0] +=    tmp_score;
        }
        else if(Arrays.asList(3, 7, 12, 18, 25, 29).contains(id)) {
              //Speaking in public/Talking with people in authority
              scores[1] +=    tmp_score;
        }
        else if(Arrays.asList(4, 6, 20, 23, 27, 30).contains(id)) {
            //Interactions with the opposite sex
              scores[2] +=    tmp_score;
        }
        else if(Arrays.asList(1, 8, 16, 21, 24, 28).contains(id)) {
              //Critcism and embarrassment
              scores[3] +=    tmp_score;
        }
        else {
              //Assertive expression of annoyance, disgust or displeasure
              scores[4] +=    tmp_score;
        }
    }

    //processes the total result
    private void computeAnxietyLevels() {
        quizEvaluator = new QuizEvaluator(scores,this);

        if(Preferences.Companion.getGender(this) == getString(R.string.gender_name_female)) {
            quizEvaluator.cutoff_values_female();
        }
        else if(Preferences.Companion.getGender(this) == getString(R.string.gender_name_male)){
            quizEvaluator.cutoff_values_male();
        }
    }



    /* private void checkAnswer() {
        answered = true;

         RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNr = rbGroup.indexOfChild(rbSelected) + 1;

         if (answerNr == currentQuestion.getAnswerNr()) {
            score++;
            textViewScore.setText("Score: " + score);
        }

        showSolution();
    } */

     /* private void showSolution() {
       /* rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);

        switch (currentQuestion.getAnswerNr()) {
            case 1:
                rb1.setTextColor(Color.GREEN);
                textViewQuestion.setText("Your Selection");
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                textViewQuestion.setText("Your Selection");
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                textViewQuestion.setText("Your Selection");
                break;
            case 4:
                rb4.setTextColor(Color.GREEN);
                textViewQuestion.setText("Your Selection");
                break;
            case 5:
                rb5.setTextColor(Color.GREEN);
                textViewQuestion.setText("Your Selection");
                break;
        }
        if (questionCounter < questionCountTotal) {
            buttonConfirmNext.setText("Next");
        } else{
            buttonConfirmNext.setText("Finish");
        }
    } */

    private void finishQuiz() {
        Intent intent = new Intent(QuizActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
