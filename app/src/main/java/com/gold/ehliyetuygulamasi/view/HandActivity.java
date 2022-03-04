package com.gold.ehliyetuygulamasi.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.gold.ehliyetuygulamasi.R;
import com.gold.ehliyetuygulamasi.adapter.HandAdapter;
import com.gold.ehliyetuygulamasi.databinding.ActivityHandBinding;
import com.gold.ehliyetuygulamasi.model.HandSign;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HandActivity extends AppCompatActivity {


    AdRequest adRequest = new AdRequest.Builder().build();
    private InterstitialAd mInterstitialAd;

    int  number=0 ;
    Handler handler;
    Runnable runnable;

    ArrayList<HandSign> handSignArrayList;
    HandAdapter handAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        com.gold.ehliyetuygulamasi.databinding.ActivityHandBinding binding = ActivityHandBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getData();
        handSignArrayList = new ArrayList<>();

        binding.handRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        handAdapter = new HandAdapter(handSignArrayList);
        binding.handRecyclerview.setAdapter(handAdapter);

        AdView mAdView = binding.handAdView;
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(runnable,1000);
                number++;
                if (number%130==0){
                    IntLoadAd();
                }
            }

        };

        handler.post(runnable);
    }

    private void getData(){

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("sign_hand").orderBy("date", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error!=null){
                    Toast.makeText(HandActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }

                if (value!=null){
                    handSignArrayList.clear();
                    for (DocumentSnapshot snapshot: value.getDocuments()){
                        Map<String, Object> data = snapshot.getData();

                        String handName = (String) data.get("handName");
                        String handImage = (String) data.get("handImage");
                        // System.out.println(handImage);
                        HandSign handSign = new HandSign(handName,handImage);
                        handSignArrayList.add(handSign);
                    }
                    handAdapter.notifyDataSetChanged();
                }
            }
        });


    }

    private void IntLoadAd() {

        InterstitialAd.load(getApplicationContext(), getResources().getString(R.string.hands_transition), adRequest, new InterstitialAdLoadCallback() {
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
            mInterstitialAd.show(HandActivity.this);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }
    }

}