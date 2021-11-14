package com.android.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    /***
     *Used to display test results
     */
    TextView result;
    /***
     * Name a major PC manufacturer?
     */
    TextView question_1;
    /***
     * What are examples of output devices?
     */
    CheckBox[] question_2 = new CheckBox[4];
    /***
     * Which is a popular PC processor manufacturer in the US.
     */
    RadioButton question_3;
    /***
     * What is your favorite OS?
     */
    EditText question_4;
    /***
     * Wired family of technologies to connect PC's in a network.
     */
    EditText question_5;

    /**
     * Answers for 1st questions.
     */
    int[][] answer1 = {
            {100, 101, 108, 108}              //0
            , {72, 80}                          //1
            , {65, 99, 101, 114}                //2
            , {65, 112, 112, 108, 101}          //3
            , {65, 115, 117, 115}               //4
            , {76, 101, 110, 111, 118, 111}     //5
            , {83, 97, 109, 115, 117, 110, 103} //6
            , {83, 111, 110, 121}               //7
            , {0}                               //8
    };

    String[] answers4 = {"chrome os", "windows", "linux", "macos", "mac os", "mac"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        findViewById(R.id.QuizAppBanner).setElevation(6);
        result = findViewById(R.id.result);
        question_1 = findViewById(R.id.question_1);
        question_2[0] = findViewById(R.id.question_2_1);
        question_2[1] = findViewById(R.id.question_2_2);
        question_2[2] = findViewById(R.id.question_2_3);
        question_2[3] = findViewById(R.id.question_2_4);
        question_3 = findViewById(R.id.question_3_1);
        question_4 = findViewById(R.id.question_4);
        question_5 = findViewById(R.id.question_5);
    }

    public int eval_question_1() {
        int returnVal = 0;
        answer1[8][0] = 0;
        String q1Answer = question_1.getText().toString();
        for (int i = 0; i < answer1.length - 1; i++) {
            String str = "";
            for (int j = 0; j < answer1[i].length; j++) {
                str = str + Character.toString((char) answer1[i][j]);
            }
            if (str.toLowerCase().matches(q1Answer.toLowerCase())) {
                answer1[8][0] = 1;
                Log.v("MainActivity", str);
                str = null;
                break;
            }
        }
        if (answer1[8][0] == 1) {
            answer1[8][0] = 0;
            returnVal = 1;
        }
        return returnVal;
    }

    /**
     * Evaluate question 2
     *
     * @return return 1 if answered correctly.
     */
    private int eval_question_2() {
        int q2Score = 0;
        if (question_2[1].isChecked()) {
            q2Score = q2Score + 1;
        }
        if (question_2[3].isChecked()) {
            q2Score = q2Score + 1;
        }
        return q2Score;
    }

    public int eval_question_3(View view) {
        int q3Score = 0;
        //Is teh button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        //Check which radio button was clicked
        switch (view.getId()) {
            case R.id.question_3_1:
                if (checked)
                    q3Score = 1;
                break;
            case R.id.question_3_2:
            case R.id.question_3_3:
                if (checked)
                    q3Score = 0;
                break;
        }
        return q3Score;
    }

    private int eval_question_4() {
        int q4score = 0;
        String answer = question_4.getText().toString().toLowerCase(Locale.ROOT);
        for (int i = 0; i < answers4.length; i++) {
            if (answers4[i].matches(answer)) {
                q4score = 1;
            }
        }
        return q4score;
    }

    private int eval_question_5() {
        int q5score = 0;
        String answer = question_5.getText().toString();
        if ("ethernet".matches(answer.toLowerCase()))
            q5score++;
        return q5score;
    }

    public void submit(View view) {
        double score = 0;
        score = score + eval_question_1();
        score = score + eval_question_2();
        score = score + eval_question_3(question_3);
        score = score + eval_question_4();
        score = score + eval_question_5();
        score = (score/6.00) * 100.00;
        if (score > 90)  result.setText(R.string.A_score);
        else if (score > 80) result.setText(R.string.B_score);
        else if (score > 66) result.setText(R.string.C_score);
        else if (score < 66) result.setText(R.string.low_score);
        result.setGravity(Gravity.CENTER);
        }
}