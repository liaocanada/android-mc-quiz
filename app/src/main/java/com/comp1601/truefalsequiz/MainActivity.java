package com.comp1601.truefalsequiz;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /* Constants */
    private final String TAG = this.getClass().getSimpleName() + " @" + System.identityHashCode(this);
    public static final int NUM_QUESTIONS = 10;


    /** Stores the questions */
    private List<Question> mQuestions = new ArrayList<>();
    /** Stores the index of the question that is currently displayed */
    int mCurrentQuestionIndex = 0;

    /**
     * Represents which multiple choice option is selected with an int, for each question
     * 0 - unselected
     * 1 to 5 - corresponding selected option
     */
    private int[] mSelections = new int[NUM_QUESTIONS];


    /* Android Widgets and Views */
    private Button mPreviousButton;
    private Button mNextButton;
    private Button mSubmitButton;
    private RadioGroup mSelectionButtonGroup;
    private RadioButton mSelectionButton1;
    private RadioButton mSelectionButton2;
    private RadioButton mSelectionButton3;
    private RadioButton mSelectionButton4;
    private RadioButton mSelectionButton5;
    private TextView mQuestionTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, getDeviceInfo());
        Log.i(TAG, "onCreate(Bundle)");

        // Initialize the widgets to control their behaviour
        mSelectionButtonGroup = findViewById(R.id.selection_button_group);
        mSelectionButton1 = findViewById(R.id.selection_button_1);
        mSelectionButton2 = findViewById(R.id.selection_button_2);
        mSelectionButton3 = findViewById(R.id.selection_button_3);
        mSelectionButton4 = findViewById(R.id.selection_button_4);
        mSelectionButton5 = findViewById(R.id.selection_button_5);
        mPreviousButton = findViewById(R.id.previous_button);
        mSubmitButton = findViewById(R.id.submit_button);
        mNextButton = findViewById(R.id.next_button);
        mQuestionTextView = findViewById(R.id.question_text_view);

        // Populate the mQuestions array
        mQuestions.add(new Question(getString(R.string.question1)));
        mQuestions.add(new Question(getString(R.string.question2)));
        mQuestions.add(new Question(getString(R.string.question3)));
        mQuestions.add(new Question(getString(R.string.question4)));
        mQuestions.add(new Question(getString(R.string.question5)));
        mQuestions.add(new Question(getString(R.string.question6)));
        mQuestions.add(new Question(getString(R.string.question7)));
        mQuestions.add(new Question(getString(R.string.question8)));
        mQuestions.add(new Question(getString(R.string.question9)));
        mQuestions.add(new Question(getString(R.string.question10)));

        // Add listener for the "Previous" button
        mPreviousButton.setOnClickListener(v -> {
            Log.i(TAG, "Previous button clicked");
            mCurrentQuestionIndex--;
            if (mCurrentQuestionIndex < 0)
                mCurrentQuestionIndex = mQuestions.size() - 1;  // Loop to the last question

            // Update the UI (question text, selections text)
            renderQuestion();
        });

        // Add listener for the "Previous" button
        mNextButton.setOnClickListener(v -> {
            Log.i(TAG, "Next button clicked");
            mCurrentQuestionIndex++;
            if (mCurrentQuestionIndex >= mQuestions.size())
                mCurrentQuestionIndex = 0;                      // Loop to the first question

            renderQuestion();
        });

        // Add listener for the "Submit" button
        mSubmitButton.setOnClickListener(v -> {
            Log.i(TAG, "Submit button clicked");

            int score = 0;

            for (int i = 0; i < NUM_QUESTIONS; i++) {
                // For each question, check if the selected answer equals the actual answer
                String selectedAnswer = mQuestions.get(i).getSelection(mSelections[i]);
                String actualAnswer = mQuestions.get(i).getAnswer();

                if (selectedAnswer != null && selectedAnswer.equals(actualAnswer))
                    score++; // Add one to score if they match

                // Reset all radio buttons to 0
                mSelections[i] = 0;
            }

            double percentage = score * 100.0 / NUM_QUESTIONS;

            // Customize message based on percentage
            String prefix = (percentage >= 50) ?
                    getString(R.string.prefix_pass) :
                    getString(R.string.prefix_fail);
            String punctuation = (percentage >= 50) ?
                    getString(R.string.punctuation_pass) :
                    getString(R.string.punctuation_fail);

            // Combine parts of message into a sentence
            String message = getString(
                    R.string.display_score_toast,
                    prefix, score, NUM_QUESTIONS, punctuation
            );

            // Display the message
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

            renderQuestion();
        });

        // Update the UI (Question text, selections text)
        renderQuestion();

    }


    /**
     * Handles RadioButton clicks
     * @param view the RadioButton that got clicked
     */
    public void onRadioButtonClicked(View view) {

        switch(view.getId()) {
            case R.id.selection_button_1:
                Log.i(TAG, "Radio button 1 clicked");
                mSelections[mCurrentQuestionIndex] = 1;     // Update the mSelections array
                mSelectionButton1.setChecked(true);         // Check the RadioButton
                break;

            case R.id.selection_button_2:
                Log.i(TAG, "Radio button 2 clicked");
                mSelections[mCurrentQuestionIndex] = 2;
                mSelectionButton2.setChecked(true);
                break;

            case R.id.selection_button_3:
                Log.i(TAG, "Radio button 3 clicked");
                mSelections[mCurrentQuestionIndex] = 3;
                mSelectionButton3.setChecked(true);
                break;

            case R.id.selection_button_4:
                Log.i(TAG, "Radio button 4 clicked");
                mSelections[mCurrentQuestionIndex] = 4;
                mSelectionButton4.setChecked(true);
                break;

            case R.id.selection_button_5:
                Log.i(TAG, "Radio button 5 clicked");
                mSelections[mCurrentQuestionIndex] = 5;
                mSelectionButton5.setChecked(true);
                break;
        }
    }

    /**
     * @return a String containing detailed information about the device running the app.
     */
    public String getDeviceInfo() {
        StringBuilder s = new StringBuilder("Device Info: ");

        try {
            s.append("\n OS Version: ").append(System.getProperty("os.version"))
                    .append("(").append(Build.VERSION.INCREMENTAL).append(")");
            s.append("\n OS API Level: ").append(Build.VERSION.SDK_INT);
            s.append("\n Device: ").append(Build.DEVICE);
            s.append("\n Model (and Product): ").append(Build.MODEL)
                    .append("(").append(Build.PRODUCT).append(")");
            s.append("\n Release: ").append(Build.VERSION.RELEASE);
            s.append("\n Brand: ").append(Build.BRAND);
            s.append("\n Display: ").append(Build.DISPLAY);
            s.append("\n Hardware: ").append(Build.HARDWARE);
            s.append("\n Build ID: ").append(Build.ID);
            s.append("\n Manufacturer: ").append(Build.MANUFACTURER);
            s.append("\n User: ").append(Build.USER);
            s.append("\n Host: ").append(Build.HOST);
        }
        catch (Exception e) {
            Log.e(TAG, "Error getting device info!");
            s.append("\n Error getting device info!");
        }

        return s.toString();
    }


    /**
     * Updates the question text, radio button text, and radio button selection.
     */
    private void renderQuestion() {
        // Update question and radio button text based on mCurrentQuestionIndex
        Question currentQuestion = mQuestions.get(mCurrentQuestionIndex);
        mQuestionTextView.setText(currentQuestion.getQuestion());
        mSelectionButton1.setText(currentQuestion.getSelection(1));
        mSelectionButton2.setText(currentQuestion.getSelection(2));
        mSelectionButton3.setText(currentQuestion.getSelection(3));
        mSelectionButton4.setText(currentQuestion.getSelection(4));
        mSelectionButton5.setText(currentQuestion.getSelection(5));

        // Update which radio button is selected, if any
        int selectedButton = mSelections[mCurrentQuestionIndex];
        mSelectionButtonGroup.clearCheck();
        mSelectionButton1.setChecked(selectedButton == 1);
        mSelectionButton2.setChecked(selectedButton == 2);
        mSelectionButton3.setChecked(selectedButton == 3);
        mSelectionButton4.setChecked(selectedButton == 4);
        mSelectionButton5.setChecked(selectedButton == 5);

        Log.i(TAG, "renderQuestion - selections: " + Arrays.toString(mSelections));
        Log.i(TAG, "renderQuestion - currently selected: Button " + selectedButton);

    }
}
