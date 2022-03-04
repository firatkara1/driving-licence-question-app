package com.gold.ehliyetuygulamasi.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gold.ehliyetuygulamasi.R;
import com.gold.ehliyetuygulamasi.databinding.ItemNoteBinding;
import com.gold.ehliyetuygulamasi.databinding.ItemSignBinding;
import com.gold.ehliyetuygulamasi.model.NoteModel;
import com.gold.ehliyetuygulamasi.model.Sign;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NotesHolder> {

    private final ArrayList<NoteModel> notes;
    Context context;
    public NoteAdapter(Context context,ArrayList<NoteModel> notes) {
        this.context = context;
        this.notes = notes;
    }

    @NonNull
    @Override
    public NotesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNoteBinding recyclerRowBinding = ItemNoteBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new NotesHolder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesHolder holder, @SuppressLint("RecyclerView") int position) {

        NoteModel noteModel = notes.get(position);

        //holder.itemNoteBinding.noteDesc.setText(notes.get(position).getNote());

        


        if (notes.get(position).getNoteTitle()!=null){
            holder.itemNoteBinding.noteTitle.setText(notes.get(position).getNoteTitle());
            holder.itemNoteBinding.noteTitle.setVisibility(View.VISIBLE);

        }else holder.itemNoteBinding.noteTitle.setVisibility(View.GONE);

        if (notes.get(position).getNote()!=null){
            holder.itemNoteBinding.noteDesc.setText(notes.get(position).getNote());
            holder.itemNoteBinding.noteDesc.setVisibility(View.VISIBLE);

           }else holder.itemNoteBinding.noteDesc.setVisibility(View.GONE);

        if (notes.get(position).getNoteImage()!=null){
            holder.itemNoteBinding.noteImage.setVisibility(View.VISIBLE);

             Picasso.get().load(notes.get(position).getNoteImage()).into(holder.itemNoteBinding.noteImage);
        }else holder.itemNoteBinding.noteImage.setVisibility(View.GONE);






    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    static class NotesHolder extends RecyclerView.ViewHolder{

        ItemNoteBinding itemNoteBinding;

        public NotesHolder(ItemNoteBinding itemNoteBinding) {
            super(itemNoteBinding.getRoot());
            this.itemNoteBinding = itemNoteBinding;
        }
    }



    private void SaveSharedPref(String key, boolean value){
        SharedPreferences sharedPreferences = context.getSharedPreferences("check", Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.apply();

    }



    private boolean Update(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences("check", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key,false);
    }
}














