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
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.gold.ehliyetuygulamasi.R;
import com.gold.ehliyetuygulamasi.adapter.NoteAdapter;
import com.gold.ehliyetuygulamasi.adapter.SignAdapter;
import com.gold.ehliyetuygulamasi.databinding.ActivityNoteBinding;
import com.gold.ehliyetuygulamasi.model.NoteModel;
import com.gold.ehliyetuygulamasi.model.Sign;
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
import java.util.Map;

public class NoteActivity extends AppCompatActivity {

    FirebaseFirestore database;

    //private AdView mAdView;

    ArrayList<NoteModel> notes;
    NoteAdapter noteAdapter;
    ActivityNoteBinding binding;

    int  number=0 ;
    Handler handler;
    Runnable runnable;
    AdRequest adRequest = new AdRequest.Builder().build();
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        binding = ActivityNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        notes = new ArrayList<>();

        database = FirebaseFirestore.getInstance();

        binding.noteRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        noteAdapter = new NoteAdapter(getApplicationContext(),notes);//kontrol
        binding.noteRecyclerView.setAdapter(noteAdapter);

        getData();

        AdView mAdView = binding.noteAdView;
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



    public void getData() {
        String noteCatId = getIntent().getStringExtra("noteCatId");

        database.collection("notes").document(noteCatId).collection("note").orderBy("date", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(NoteActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }

                if (value != null) {
                    notes.clear();
                    for (DocumentSnapshot snapshot : value.getDocuments()) {
                        Map<String, Object> data = snapshot.getData();

                        assert data != null;
                        String noteTitle = (String) data.get("noteTitle");
                        String noteDesc = (String) data.get("noteDesc");
                        String noteImage = (String) data.get("noteImage");




                        NoteModel noteModel = new NoteModel(noteCatId,noteTitle,noteDesc,noteImage);
                        notes.add(noteModel);

                    }
                    noteAdapter.notifyDataSetChanged();
                }


            }
        });

    }

    private void IntLoadAd() {

        InterstitialAd.load(getApplicationContext(), getResources().getString(R.string.notes_transition), adRequest, new InterstitialAdLoadCallback() {
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
            mInterstitialAd.show(NoteActivity.this);
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
                        NoteActivity.super.onBackPressed();
                        handler.removeCallbacks(runnable);
                    }

                }).create().show();
    }

}



















