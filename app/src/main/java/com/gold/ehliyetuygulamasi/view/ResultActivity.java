package com.gold.ehliyetuygulamasi.view;

import static com.gold.ehliyetuygulamasi.R.color.colorGreen;
import static com.gold.ehliyetuygulamasi.R.color.colorMyBlue;
import static com.gold.ehliyetuygulamasi.R.color.colorRed;
import static com.gold.ehliyetuygulamasi.R.color.colorYellow;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.gold.ehliyetuygulamasi.R;
import com.gold.ehliyetuygulamasi.databinding.ActivityResultBinding;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class ResultActivity extends AppCompatActivity {

    ActivityResultBinding binding;
    int point = 4;

    Handler handler;
    Runnable runnable;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.imageView.setMinAndMaxFrame(30,500);

        binding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.imageView.playAnimation();
            }
        });
        AdView mAdView = binding.resultAdView;
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);




        int correctAnswers = getIntent().getIntExtra("correct",0);
        int totalQuestions = getIntent().getIntExtra("total",0);

        int points = correctAnswers*point;

        binding.score.setText(String.format("%d/%d", correctAnswers,totalQuestions));
        binding.earnedPoints.setText(String.valueOf(points));

        if (correctAnswers<10){
            binding.resultTxt.setText(R.string.correct_10);
            binding.imageView.setImageResource(R.drawable.sad);
            binding.resultTxt.setTextColor(getColor(colorRed));
        }else if(correctAnswers<14){
            binding.resultTxt.setText(R.string.correct_15);
            binding.resultTxt.setTextColor(getColor(colorYellow));

        }else if(correctAnswers<=15){
            binding.resultTxt.setText(R.string.correct_20);
            binding.resultTxt.setTextColor(getColor(colorGreen));

        }

        binding.homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}