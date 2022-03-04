package com.gold.ehliyetuygulamasi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gold.ehliyetuygulamasi.databinding.ItemHandSignBinding;
import com.gold.ehliyetuygulamasi.model.HandSign;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HandAdapter extends RecyclerView.Adapter<HandAdapter.HandHolder> {


    private ArrayList<HandSign> handSignArrayList;

    public HandAdapter(ArrayList<HandSign> handSignArrayList) {
        this.handSignArrayList = handSignArrayList;
    }

    @NonNull
    @Override
    public HandHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHandSignBinding itemHandSignBinding = ItemHandSignBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new HandHolder(itemHandSignBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull HandHolder holder, int position) {

        holder.itemHandSignBinding.handSignName.setText(handSignArrayList.get(position).handName);
        Picasso.get().load(handSignArrayList.get(position).getHandImage()).into(holder.itemHandSignBinding.handSignImageview);
    }

    @Override
    public int getItemCount() {
        return handSignArrayList.size();
    }

    class HandHolder extends RecyclerView.ViewHolder{

        ItemHandSignBinding itemHandSignBinding;

        public HandHolder(ItemHandSignBinding itemHandSignBinding) {
            super(itemHandSignBinding.getRoot());
            this.itemHandSignBinding = itemHandSignBinding;
        }
    }
}






