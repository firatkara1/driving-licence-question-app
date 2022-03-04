package com.gold.ehliyetuygulamasi.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.gold.ehliyetuygulamasi.R;
import com.gold.ehliyetuygulamasi.dialog.NoteCategoryDialog;
import com.gold.ehliyetuygulamasi.model.NoteCategoryModel;
import com.gold.ehliyetuygulamasi.model.SignCategoryModel;
import com.gold.ehliyetuygulamasi.view.MainActivity;
import com.gold.ehliyetuygulamasi.view.NoteActivity;
import com.gold.ehliyetuygulamasi.view.QuestionActivity;
import com.gold.ehliyetuygulamasi.view.SignActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.ArrayList;

public class NoteCategoryAdapter extends RecyclerView.Adapter<NoteCategoryAdapter.CategoryNoteHolder> {

    private InterstitialAd mInterstitialAd;
    AdRequest adRequest = new AdRequest.Builder().build();

    Context context;
    public ArrayList<NoteCategoryModel> noteCategoryModels;

    public NoteCategoryAdapter(Context context, ArrayList<NoteCategoryModel> noteCategoryModels) {
        this.context = context;
        this.noteCategoryModels = noteCategoryModels;
    }


    @NonNull
    @Override
    public CategoryNoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(R.layout.item_note_category,null);
        return new CategoryNoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryNoteHolder holder, int position) {

        NoteCategoryModel model = noteCategoryModels.get(position);
        holder.noteCategoryTxt.setText(model.getNoteCategoryName());

        holder.itemNoteCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(context, NoteActivity.class);
                intent.putExtra("noteCatId", model.getNoteCategoryId());

                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return noteCategoryModels.size();
    }

    public interface OnNoteCategoryItemClicked {
    }

    class CategoryNoteHolder extends RecyclerView.ViewHolder{

        TextView noteCategoryTxt;
        Button itemNoteCategoryBtn;

        public CategoryNoteHolder(@NonNull View itemView) {
            super(itemView);
            noteCategoryTxt = itemView.findViewById(R.id.note_category_txt);
            itemNoteCategoryBtn = itemView.findViewById(R.id.item_note_category_btn);
        }
    }


}












