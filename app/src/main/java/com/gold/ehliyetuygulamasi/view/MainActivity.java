package com.gold.ehliyetuygulamasi.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gold.ehliyetuygulamasi.R;
import com.gold.ehliyetuygulamasi.databinding.ActivityMainBinding;
import com.gold.ehliyetuygulamasi.dialog.ImageQuestionCategoryDialog;
import com.gold.ehliyetuygulamasi.dialog.NoteCategoryDialog;
import com.gold.ehliyetuygulamasi.dialog.QuestionCategoryDialog;
import com.gold.ehliyetuygulamasi.dialog.SignCategoryDialog;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private CardView cardView1;
    private CardView cardView2;
    private CardView cardView3;
    private CardView cardView4;
    private CardView cardView5;
    private CardView cardView6;



    private InterstitialAd mInterstitialAd;
    AdRequest adRequest = new AdRequest.Builder().build();

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);




        defineObject();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
            }
        });

        IntLoadAd();

        AdView mAdView = binding.mainActivityAdView;
        mAdView.loadAd(adRequest);


        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuestionCategoryDialog questionCategoryDialog = new QuestionCategoryDialog();
                questionCategoryDialog.setCancelable(false);
                questionCategoryDialog.show(getSupportFragmentManager().beginTransaction(), "");

                IntLoadAd();
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NoteCategoryDialog noteCategoryDialog = new NoteCategoryDialog();
                noteCategoryDialog.setCancelable(false);
                noteCategoryDialog.show(getSupportFragmentManager().beginTransaction(), "");
                IntLoadAd();
            }
        });

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageQuestionCategoryDialog imageQuestionCategoryDialog = new ImageQuestionCategoryDialog();
                imageQuestionCategoryDialog.setCancelable(false);
                imageQuestionCategoryDialog.show(getSupportFragmentManager().beginTransaction(), "");
                IntLoadAd();
            }
        });

        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignCategoryDialog signCategoryDialog = new SignCategoryDialog();
                signCategoryDialog.setCancelable(false);
                signCategoryDialog.show(getSupportFragmentManager().beginTransaction(), "");
                IntLoadAd();
            }
        });

        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),HandActivity.class);
                startActivity(intent);
                IntLoadAd();
            }
        });


        cardView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getApplicationContext().getPackageName())));
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "Play Store Failed", Toast.LENGTH_SHORT).show();
                }
                 IntLoadAd();
            }
        });
    }




    public void defineObject() {

        cardView1 = binding.card1;
        cardView2 = binding.card2;
        cardView3 = binding.card3;
        cardView4 = binding.card4;
        cardView5 = binding.card5;
        cardView6 = binding.card6;

    }

    private void IntLoadAd(){

        InterstitialAd.load(getApplicationContext(), getResources().getString(R.string.main_activity_transition), adRequest, new InterstitialAdLoadCallback() {
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
            mInterstitialAd.show(MainActivity.this);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }
    }

}