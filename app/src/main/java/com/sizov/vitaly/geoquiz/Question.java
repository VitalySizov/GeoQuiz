package com.sizov.vitaly.geoquiz;


public class Question {

    private int mTextResId; // Текст вопроса, точнее его индификатор
    private boolean mAnswerTrue; // Правильны ответ да/нет

    public Question(int textResId, boolean answerTrue) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
