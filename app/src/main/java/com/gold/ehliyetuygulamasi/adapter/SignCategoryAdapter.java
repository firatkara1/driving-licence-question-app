package com.gold.ehliyetuygulamasi.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gold.ehliyetuygulamasi.R;
import com.gold.ehliyetuygulamasi.model.CategoryModel;
import com.gold.ehliyetuygulamasi.model.NoteModel;
import com.gold.ehliyetuygulamasi.model.SignCategoryModel;
import com.gold.ehliyetuygulamasi.view.QuestionActivity;
import com.gold.ehliyetuygulamasi.view.SignActivity;

import java.util.ArrayList;

public class SignCategoryAdapter extends RecyclerView.Adapter<SignCategoryAdapter.SignHolder> {

    Context context;
    public ArrayList<SignCategoryModel> signCategoryModels;

    public SignCategoryAdapter(Context context, ArrayList<SignCategoryModel> signCategoryModels){
        this.context = context;
        this.signCategoryModels = signCategoryModels;
    }




    @NonNull
    @Override
    public SignHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(R.layout.item_sign_category,null);
        return new SignHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull SignHolder holder, int position) {

        SignCategoryModel model = signCategoryModels.get(position);
        holder.signCategoryTxt.setText(model.getSignCategoryName());

        holder.item_subcategory_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(context, SignActivity.class);
                intent.putExtra("signCatId", model.getSignCategoryId());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return signCategoryModels.size();
    }

    public interface OnSignCategoryItemClicked {
    }


    class SignHolder extends RecyclerView.ViewHolder{

        TextView signCategoryTxt;
        Button item_subcategory_btn;

        public SignHolder(@NonNull View itemView) {
            super(itemView);
            signCategoryTxt = itemView.findViewById(R.id.sign_category_txt);
            item_subcategory_btn = itemView.findViewById(R.id.item_subcategory_btn);
        }
    }
}
