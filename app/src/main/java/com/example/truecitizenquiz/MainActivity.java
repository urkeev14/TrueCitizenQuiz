package com.example.truecitizenquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnFalse;
    private Button btnTrue;
    private ImageButton btnNext;
    private ImageButton btnPrevious;
    private TextView tvQuestion;

    private Question[] questions;
    private int currentQuestionIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initCompnents();
        initQuestions();
    }

    private void initQuestions() {
        questions = new Question[]{
                new Question(R.string.question_amendments, false), //correct: 27
                new Question(R.string.question_constitution, true),
                new Question(R.string.question_declaration, true),
                new Question(R.string.question_independence_rights, true),
                new Question(R.string.question_religion, true),
                new Question(R.string.question_government, false),
                new Question(R.string.question_government_feds, false),
                new Question(R.string.question_government_senators, true),

        };
    }

    private void initCompnents() {
        Question question = new Question(R.string.question_declaration, true);

        btnFalse = findViewById(R.id.btnFalse);
        btnTrue = findViewById(R.id.btnTrue);
        btnNext = findViewById(R.id.btnNext);
        btnPrevious = findViewById(R.id.btnPrevious);

        tvQuestion = findViewById(R.id.tvQuestion);

        registerButtons();
    }

    private void registerButtons() {
        btnTrue.setOnClickListener(this);
        btnFalse.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnPrevious.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnFalse:
                checkIf(false);
                break;

            case R.id.btnTrue:
                checkIf(true);
                break;

            case R.id.btnNext:
                goToNextQuestion();
                break;

            case R.id.btnPrevious:
                goToPreviousQuestion();
                break;

        }
    }

    //TODO: Implementirati promenu boje acitivity-a nakon tacnog ili pogresnog odgovora
    private void checkIf(boolean userChoice) {
        boolean answerIsTrue = questions[currentQuestionIndex].isAnswerTrue();

        printAnwerToToast(userChoice, answerIsTrue);
        goToNextQuestion();
    }

    private void printAnwerToToast(boolean userChoice, boolean answerIsTrue) {
        int toastMessageID;
        if (userChoice == answerIsTrue) {
            toastMessageID = R.string.correct_answer;
        } else {
            toastMessageID = R.string.wrong_answer;
        }

        Toast.makeText(MainActivity.this, toastMessageID, Toast.LENGTH_SHORT).show();
    }

    private void waitABit() {
        try {
            Thread.currentThread().sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void goToNextQuestion() {
        currentQuestionIndex = (currentQuestionIndex + 1) % questions.length;
        Log.d("Current", "onClick" + currentQuestionIndex);
        tvQuestion.setText(questions[currentQuestionIndex].getAnswerResID());
    }

    private void goToPreviousQuestion() {
        currentQuestionIndex = (currentQuestionIndex - 1) % questions.length;

        if (currentQuestionIndex == -1)
            currentQuestionIndex = questions.length - 1;

        Log.d("Current", "onClick" + currentQuestionIndex);
        tvQuestion.setText(questions[currentQuestionIndex].getAnswerResID());
    }
}
