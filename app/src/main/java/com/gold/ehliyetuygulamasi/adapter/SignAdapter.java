package com.gold.ehliyetuygulamasi.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gold.ehliyetuygulamasi.R;
import com.gold.ehliyetuygulamasi.databinding.ItemSignBinding;
import com.gold.ehliyetuygulamasi.model.CategoryModel;
import com.gold.ehliyetuygulamasi.model.Sign;
import com.gold.ehliyetuygulamasi.model.SignCategoryModel;
import com.gold.ehliyetuygulamasi.view.QuestionActivity;
import com.squareup.picasso.Picasso;

import java.util.AbstractList;
import java.util.ArrayList;

public class SignAdapter extends RecyclerView.Adapter<SignAdapter.SignsHolder> {


    private ArrayList<Sign> signs;

    public SignAdapter(ArrayList<Sign> signs) {
        this.signs = signs;
    }


    @NonNull
    @Override
    public SignsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemSignBinding recyclerRowBinding = ItemSignBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new SignsHolder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SignsHolder holder, int position) {

        Sign model = signs.get(position);


        holder.itemSignBinding.signName.setText(signs.get(position).getSignName());

        Picasso.get().load(signs.get(position).getSignImage()).into(holder.itemSignBinding.signImage);

        //when the sign is clicked
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent =  new Intent(context, QuestionActivity.class);
                //intent.putExtra("catId", model.getSignId());

               // Toast.makeText(context, model.getSignName().toString(), Toast.LENGTH_LONG).show();
               // context.startActivity(intent);
                Toast.makeText(v.getContext(), model.getSignName(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return signs.size();
    }

    static class SignsHolder extends RecyclerView.ViewHolder{

        ItemSignBinding itemSignBinding;


        public SignsHolder(ItemSignBinding itemSignBinding) {
            super(itemSignBinding.getRoot());
            this.itemSignBinding = itemSignBinding;

        }
    }
}

















