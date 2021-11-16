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
    TextView editTQ1_PCManuf;

    /***
     * What are examples of output devices?
     */
    CheckBox[] checkbQ2_OutputDevices = new CheckBox[4];

    /***
     * Which is a popular PC processor manufacturer in the US.
     */
    RadioButton radioBQ3_PCProcessors;

    /***
     * What is your favorite OS?
     */
    EditText editTQ4_PopularOS;

    /***
     * Wired family of technologies to connect PC's in a network.
     */
    EditText editTQ5_ConnectTech;

    /**
     * Answers for 1st questions.
     */
    int[][] pcManufacturer = {
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

    String[] operatingSystems = {"chrome os", "windows", "linux", "macos", "mac os", "mac"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = findViewById(R.id.result);
        editTQ1_PCManuf = findViewById(R.id.editTQ1_PCManuf);
        checkbQ2_OutputDevices[0] = findViewById(R.id.checkbQ2_OutputDevices_1);
        checkbQ2_OutputDevices[1] = findViewById(R.id.checkbQ2_OutputDevices_2);
        checkbQ2_OutputDevices[2] = findViewById(R.id.checkbQ2_OutputDevices_3);
        checkbQ2_OutputDevices[3] = findViewById(R.id.checkbQ2_OutputDevices_4);
        radioBQ3_PCProcessors = findViewById(R.id.radioBQ3_PCProcessors_1);
        editTQ4_PopularOS = findViewById(R.id.editTQ4_PopularOS);
        editTQ5_ConnectTech = findViewById(R.id.editTQ5_ConnectTech);
    }

    /**
     * Evaluate question 1.
     *
     * @return The number of correct responses chosen, and of type int.
     */
    public int eval_question_1() {
        int q1score = 0;
        pcManufacturer[8][0] = 0;
        String q1Answer = editTQ1_PCManuf.getText().toString();
        for (int i = 0; i < pcManufacturer.length - 1; i++) {
            String str = "";
            for (int j = 0; j < pcManufacturer[i].length; j++) {
                str = str + Character.toString((char) pcManufacturer[i][j]);
            }
            if (str.toLowerCase().matches(q1Answer.toLowerCase())) {
                pcManufacturer[8][0] = 1;
                str = null;
                break;
            }
        }
        if (pcManufacturer[8][0] == 1) {
            pcManufacturer[8][0] = 0;
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
        if (checkbQ2_OutputDevices[1].isChecked()) {
            q2Score = q2Score + 1;
        }
        if (checkbQ2_OutputDevices[3].isChecked()) {
            q2Score = q2Score + 1;
        }
        if (checkbQ2_OutputDevices[0].isChecked() || checkbQ2_OutputDevices[2].isChecked()) {
            q2Score = 0;
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
            case R.id.radioBQ3_PCProcessors_1:
                if (checked)
                    q3Score = 1;
                break;
            case R.id.radioBQ3_PCProcessors_2:
            case R.id.radioBQ3_PCProcessors_3:
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
        String answer = editTQ4_PopularOS.getText().toString().toLowerCase(Locale.ROOT);
        for (int i = 0; i < operatingSystems.length; i++) {
            if (operatingSystems[i].matches(answer)) {
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
        String answer = editTQ5_ConnectTech.getText().toString();
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
        score = score + eval_question_3(radioBQ3_PCProcessors);
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