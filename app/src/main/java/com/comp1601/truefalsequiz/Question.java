package com.comp1601.truefalsequiz;

import android.util.Log;

/**
 * A simple model of a single Question in the Android app. All Questions are immutable.
 */
public final class Question {

    /** Stores the question */
    private final String mQuestion;

    /** Stores the answer to the question */
    private final String mAnswer;

    /** Stores possible answers to the question. */
    private final String[] mSelections = new String[5];

    /** Tag for logging */
    private final String TAG = this.getClass().getSimpleName() + " @" + System.identityHashCode(this);


    /**
     * Creates a Question object from a String representation of a Question
     * @param aQuestionString a String representing a Question, in the format
     *                        QuestionText
     *                        [AnswerText]
     *                        Selection1|Selection2|Selection3|Selection4|Selection5|
     * @throws IllegalArgumentException if none of the selections contain the answer.
     */
    public Question(String aQuestionString) throws IllegalArgumentException {

        // Remove the question from the String and set it to mQuestion
        int questionIndex = aQuestionString.indexOf("[");
        mQuestion = aQuestionString.substring(0, questionIndex);
        aQuestionString = aQuestionString.substring(questionIndex + 1);

        // Remove the answer from the String and set it to mAnswer
        int answerIndex = aQuestionString.indexOf("]");
        mAnswer = aQuestionString.substring(0, answerIndex).trim();
        aQuestionString = aQuestionString.substring(answerIndex + 1);


        // Remove each selection from the String and set it to mSelections[i]
        for (int i = 0; i < mSelections.length; i++) {
            int selectionIndex = aQuestionString.indexOf("|");

            mSelections[i] = aQuestionString.substring(0, selectionIndex).trim();
            aQuestionString = aQuestionString.substring(selectionIndex + 1);
        }


        // Make sure the answer is one of the selections
        for (String selection : mSelections) {
            if (selection.equals(mAnswer)) {
                Log.i(TAG, "Question \"" + mQuestion + "\" successfully created.");
                return;
            }
        }

        // If not, throw an exception
        throw new IllegalArgumentException("Answer \"" + mAnswer
                + "\" isn't one of the possible multiple choice selections");
    }


    /**
     * Gets the text for the specified multiple choice option
     * @param selection a number from 1 to 5 inclusive, representing a multiple choice selection
     * @return a String containing the multiple choice selection, or an empty String if the
     *      selection does not exist
     */
    public String getSelection(int selection) {
        if (selection < 1 || selection > 5) return "";
        return mSelections[selection - 1];
    }

    /** Getter for question */
    public String getQuestion(){
        return mQuestion;
    }

    /** Getter for answer */
    public String getAnswer(){
        return mAnswer;
    }

    // No setters to make this class immutable
}