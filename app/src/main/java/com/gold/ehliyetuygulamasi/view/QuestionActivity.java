package com.gold.ehliyetuygulamasi.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.gold.ehliyetuygulamasi.model.Question;
import com.gold.ehliyetuygulamasi.R;
import com.gold.ehliyetuygulamasi.databinding.ActivityQuestionBinding;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Random;

public class QuestionActivity extends AppCompatActivity {


    private InterstitialAd mInterstitialAd;
    AdRequest adRequest = new AdRequest.Builder().build();

    ActivityQuestionBinding binding;
    FirebaseFirestore database;

    ArrayList<Question> questions;

    int index = 0;
    Question question;
    int point = 4;

    CountDownTimer timer;
    int correctAnswer = 0;
    int wrongAnswer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        binding = ActivityQuestionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseFirestore.getInstance();

        questions = new ArrayList<>();


        String catId = getIntent().getStringExtra("catId");
        String catName = getIntent().getStringExtra("catName");

        binding.quizCategory.setText(catName);

        Random random = new Random();
        final int r = random.nextInt(9);

        database.collection("categories").document(catId).collection("questions")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.getDocuments() != null) {
                    database.collection("categories").document(catId).collection("questions")
                            .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                            for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                                Question question = snapshot.toObject(Question.class);
                                questions.add(question);
                            }
                            setNextQuestion();
                        }

                    });
                }
            }
        });

        resetTimer();


    }

    public void resetTimer() {
        timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                binding.timer.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {

                disableOption();
                binding.quizNextBtn.setVisibility(View.VISIBLE);
            }
        };
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    public void showAnswerOption() {

        if (question.getAnswer().equals(binding.quizOptionOne.getText().toString())) {
            binding.quizOptionOne.setBackground(getDrawable(R.drawable.option_right));
            binding.quizOptionOne.setTextColor(getColor(R.color.color_white));
        } else if (question.getAnswer().equals(binding.quizOptionTwo.getText().toString())) {
            binding.quizOptionTwo.setBackground(getDrawable(R.drawable.option_right));
            binding.quizOptionTwo.setTextColor(getColor(R.color.color_white));
        } else if (question.getAnswer().equals(binding.quizOptionThree.getText().toString())) {
            binding.quizOptionThree.setBackground(getDrawable(R.drawable.option_right));
            binding.quizOptionThree.setTextColor(getColor(R.color.color_white));
        } else if (question.getAnswer().equals(binding.quizOptionFour.getText().toString())) {
            binding.quizOptionFour.setBackground(getDrawable(R.drawable.option_right));
            binding.quizOptionFour.setTextColor(getColor(R.color.color_white));
        }
    }

    @SuppressLint("DefaultLocale")
    public void setNextQuestion() {


        if (timer != null) {
            timer.cancel();
        }

        resetTimer();

        timer.start();
        if (index < questions.size()) {
            binding.questionCounter.setText(String.format("%d/%d", (index + 1), questions.size()));
            question = questions.get(index);
            binding.quizQuestion.setText(question.getQuestion());
            binding.quizOptionOne.setText(question.getOption1());
            binding.quizOptionTwo.setText(question.getOption2());
            binding.quizOptionThree.setText(question.getOption3());
            binding.quizOptionFour.setText(question.getOption4());


        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    void checkAnswer(Button button) {

        String selectedAnswer = button.getText().toString();
        if (selectedAnswer.equals(question.getAnswer())) {
            correctAnswer++;
            button.setBackground(getResources().getDrawable(R.drawable.option_right, null));
            button.setTextColor(getColor(R.color.color_white));
            if (index == questions.size() - 1) {
                binding.resultBtn.setVisibility(View.VISIBLE);
                binding.quizNextBtn.setVisibility(View.INVISIBLE);
            }

        } else {

            wrongAnswer++;
            showAnswerOption();
            button.setBackground(getResources().getDrawable(R.drawable.option_wrong, null));
            button.setTextColor(getColor(R.color.color_white));
            if (index == questions.size() - 1) {
                binding.resultBtn.setVisibility(View.VISIBLE);
                binding.quizNextBtn.setVisibility(View.INVISIBLE);
            }

            binding.quizFeedback.setVisibility(View.VISIBLE);
            binding.quizFeedback.setText(String.valueOf("Yanlış Cevap Sayısı: " + wrongAnswer));


        }
        int points = correctAnswer * point;
        binding.quizPoint.setText(String.valueOf(points));


    }

    void disableOption() {
        binding.quizOptionOne.setClickable(false);
        binding.quizOptionTwo.setClickable(false);
        binding.quizOptionThree.setClickable(false);
        binding.quizOptionFour.setClickable(false);
    }

    void enableOption() {
        binding.quizOptionOne.setClickable(true);
        binding.quizOptionTwo.setClickable(true);
        binding.quizOptionThree.setClickable(true);
        binding.quizOptionFour.setClickable(true);
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    void resetOption() {
        binding.quizNextBtn.setVisibility(View.INVISIBLE);
        binding.quizOptionOne.setBackground(getResources().getDrawable(R.drawable.option_unselected, null));
        binding.quizOptionTwo.setBackground(getResources().getDrawable(R.drawable.option_unselected, null));
        binding.quizOptionThree.setBackground(getResources().getDrawable(R.drawable.option_unselected, null));
        binding.quizOptionFour.setBackground(getResources().getDrawable(R.drawable.option_unselected, null));
        binding.quizFeedback.setVisibility(View.INVISIBLE);

        binding.quizOptionOne.setTextColor(getColor(R.color.colorDarkerText));
        binding.quizOptionTwo.setTextColor(getColor(R.color.colorDarkerText));
        binding.quizOptionThree.setTextColor(getColor(R.color.colorDarkerText));
        binding.quizOptionFour.setTextColor(getColor(R.color.colorDarkerText));
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.quiz_option_one:
            case R.id.quiz_option_two:
            case R.id.quiz_option_three:
            case R.id.quiz_option_four:
                if (timer != null) {
                    timer.cancel();
                }
                Button selected = (Button) view;
                checkAnswer(selected);
                disableOption();
                binding.quizNextBtn.setVisibility(View.VISIBLE);
                //Toast.makeText(this, "Secenekler Tiklandi", Toast.LENGTH_SHORT).show();
                break;

            case R.id.quiz_next_btn:
                resetOption();
                enableOption();

                if (index < questions.size() - 1) {
                    index++;


                    if (index % 5 == 0) {
                        IntLoadAd();
                    }
                    setNextQuestion();

                } else disableOption();
                //Toast.makeText(this, "Sinav Bitti", Toast.LENGTH_SHORT).show();

                break;

            case R.id.result_btn:
                Intent intent = new Intent(QuestionActivity.this, ResultActivity.class);
                intent.putExtra("correct", correctAnswer);
                intent.putExtra("total", questions.size());
                startActivity(intent);
                finish();
                break;
        }
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Sınavdasınız!")
                .setMessage("Çıkış yapmak istediğinize emin misiniz?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        QuestionActivity.super.onBackPressed();
                    }

                }).create().show();
    }

    private void IntLoadAd() {

        InterstitialAd.load(getApplicationContext(), getResources().getString(R.string.question_transition), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);
                mInterstitialAd = interstitialAd;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                System.out.println("Add Load Failed");
                mInterstitialAd = null;
            }
        });

        if (mInterstitialAd != null) {
            mInterstitialAd.show(QuestionActivity.this);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }
    }

}














