package com.gold.ehliyetuygulamasi.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.gold.ehliyetuygulamasi.R;
import com.gold.ehliyetuygulamasi.adapter.SignAdapter;
import com.gold.ehliyetuygulamasi.databinding.ActivitySignBinding;
import com.gold.ehliyetuygulamasi.model.Question;
import com.gold.ehliyetuygulamasi.model.Sign;
import com.gold.ehliyetuygulamasi.model.SignCategoryModel;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class SignActivity extends AppCompatActivity {


    private InterstitialAd mInterstitialAd;
    AdRequest adRequest = new AdRequest.Builder().build();

    FirebaseFirestore database;
    Handler handler;
    Runnable runnable;

    ArrayList<Sign> signs;
    SignAdapter signAdapter;
    ActivitySignBinding binding;


    int  number=0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        binding = ActivitySignBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        signs = new ArrayList<>();

        database = FirebaseFirestore.getInstance();

        binding.signRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        signAdapter = new SignAdapter(signs);
        binding.signRecyclerView.setAdapter(signAdapter);

        getData();

        AdView mAdView = binding.signAdView;
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);





        //IntLoadAd();

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(runnable,1000);
                number++;
                if (number%130==0){
                    IntLoadAd();
                }
                //System.out.println(number);

            }

        };

        handler.post(runnable);


    }


    public void getData() {
        String signCatId = getIntent().getStringExtra("signCatId");

        database.collection("sign_category").document(signCatId).collection("signs").orderBy("date", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(SignActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }

                if (value != null) {
                    signs.clear();
                    for (DocumentSnapshot snapshot : value.getDocuments()) {
                        Map<String, Object> data = snapshot.getData();

                        String signName = (String) data.get("signName");
                        String signImage = (String) data.get("signImage");
                        String signMeaning = (String) data.get("signMeaning");


                        Sign model = new Sign(signName, signImage, signMeaning, signCatId);
                        signs.add(model);

                    }
                    signAdapter.notifyDataSetChanged();
                }


            }
        });

    }

    private void IntLoadAd() {

        InterstitialAd.load(getApplicationContext(), getResources().getString(R.string.signs_transition), adRequest, new InterstitialAdLoadCallback() {
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
            mInterstitialAd.show(SignActivity.this);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Yeteri kadar Çalıştınız Mı?")
                .setMessage("Çıkış yapmak istediğinize emin misiniz?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        SignActivity.super.onBackPressed();
                        handler.removeCallbacks(runnable);

                    }

                }).create().show();
    }

}






















