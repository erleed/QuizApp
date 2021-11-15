package com.android.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    /***
     *Used to display test results
     */
    TextView result;

    /***
     * Name a major PC manufacturer?
     */
    TextView q1MajorPCManuf;

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
            {100, 101, 108, 108}                //0
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
        result = findViewById(R.id.result);
        q1MajorPCManuf = findViewById(R.id.question_1);
        question_2[0] = findViewById(R.id.question_2_1);
        question_2[1] = findViewById(R.id.question_2_2);
        question_2[2] = findViewById(R.id.question_2_3);
        question_2[3] = findViewById(R.id.question_2_4);
        question_3 = findViewById(R.id.question_3_1);
        question_4 = findViewById(R.id.question_4);
        question_5 = findViewById(R.id.question_5);
    }

    /**
     * Evaluate question 1.
     *
     * @return The number of correct responses chosen, and of type int.
     */
    public int eval_question_1() {
        int q1score = 0;
        answer1[8][0] = 0;
        String q1Answer = q1MajorPCManuf.getText().toString();
        for (int i = 0; i < answer1.length - 1; i++) {
            String str = "";
            for (int j = 0; j < answer1[i].length; j++) {
                str = str + Character.toString((char) answer1[i][j]);
            }
            if (str.toLowerCase().matches(q1Answer.toLowerCase())) {
                answer1[8][0] = 1;
                str = null;
                break;
            }
        }
        if (answer1[8][0] == 1) {
            answer1[8][0] = 0;
            q1score = 1;
        }
        return q1score;
    }

    /**
     * Evaluate question 2
     *
     * @return The number of correct responses chosen, and of type int.
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

    /***
     * Evaluate question 3.
     * @param view View parameter not in use.
     * @return The number of correct responses chosen, and of type int.
     */
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

    /**
     * Evaluate question 4.
     *
     * @return The number of correct responses chosen, and of type int.
     */
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

    /**
     * Evaluate question 5.
     *
     * @return The number of correct responses chosen, and of type int.
     */
    private int eval_question_5() {
        int q5score = 0;
        String answer = question_5.getText().toString();
        if ("ethernet".matches(answer.toLowerCase()))
            q5score++;
        return q5score;
    }

    /**
     * The submit method calculates the total number of correct answers selected.
     */
    public void submit(View view) {
        Context context = getApplicationContext();          //Create context variable for use in toast.
        int duration = Toast.LENGTH_LONG;           //Capture duration long for toast message.
        double score = 0;           //Score: variable for storing total correct answers.
        score = score + eval_question_1();
        score = score + eval_question_2();
        score = score + eval_question_3(question_3);
        score = score + eval_question_4();
        score = score + eval_question_5();
        score = (score / 6.00) * 100.00;
        if (score > 90) {           //If else if evaluating total score to send appropriate message.
            result.setText(R.string.A_score);
            Toast toast = Toast.makeText(context, (CharSequence) getString(R.string.A_score), duration);    //Create toast message
            toast.show();           //Show toast message.
        } else if (score > 80) {
            result.setText(R.string.B_score);
            Toast toast = Toast.makeText(context, (CharSequence) getString(R.string.B_score), duration);
            toast.show();
        } else if (score > 66) {
            result.setText(R.string.C_score);
            Toast toast = Toast.makeText(context, (CharSequence) getString(R.string.C_score), duration);
            toast.show();
        } else {
            result.setText(R.string.low_score);
            Toast toast = Toast.makeText(context, (CharSequence) getString(R.string.low_score), duration);
            toast.show();
        }
    }
}