package com.sizov.vitaly.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    public static final String EXTRA_ANSWER_IS_TRUE = "com.sizov.vitaly.geoquiz.answer_is_true";
    public static final String EXTRA_ANSWER_SHOWN = "com.sizov.vitaly.geoquiz.answer_shown";
    private static final String KEY_CHEATED = "cheated";
    private static final String TAG = "CheatActivity";
    private boolean mAnswerIsTrue;
    private boolean mShowAnswerPressed;
    private TextView mAnswerTextView;
    private TextView mShowApiLevel;
    private Button mShowAnswer;

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent i = new Intent(packageContext, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return i;
    }

    // декодирование интента результата
    public static boolean wasAnswerShown(Intent result) {
        if (result.getBooleanExtra(KEY_CHEATED, false)) {
            return true;
        }
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState() called");
        outState.putBoolean(KEY_CHEATED, mShowAnswerPressed);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);

        // Вывод версии API
        mShowApiLevel = (TextView) findViewById(R.id.show_api_level);
        mShowApiLevel.setText("API level " + String.valueOf(Build.VERSION.SDK_INT));

        if (savedInstanceState != null) {
            mShowAnswerPressed = savedInstanceState.getBoolean(KEY_CHEATED);
            setAnswerShownResult(mShowAnswerPressed);
        }

        mShowAnswer = (Button)findViewById(R.id.show_answer_button);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                mShowAnswerPressed = true;
                setAnswerShownResult(true);

                // Анимаация
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    int cx = mShowAnswer.getWidth() / 2 ;
                    int cy = mShowAnswer.getHeight() / 2;
                    float radius = mShowAnswer.getWidth();
                    Animator anim = ViewAnimationUtils
                            .createCircularReveal(mShowAnswer, cx, cy, radius, 0);
                    anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mAnswerTextView.setVisibility(View.VISIBLE);
                        mShowAnswer.setVisibility(View.VISIBLE);
                    }
                    });
                    anim.start();
                } else {
                mAnswerTextView.setVisibility(View.VISIBLE);
                mShowAnswer.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }
}
