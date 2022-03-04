package com.gold.ehliyetuygulamasi.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gold.ehliyetuygulamasi.R;
import com.gold.ehliyetuygulamasi.model.CategoryModel;
import com.gold.ehliyetuygulamasi.view.QuestionActivity;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    Context context;
    ArrayList<CategoryModel> categoryModels;

    public CategoryAdapter(Context context, ArrayList<CategoryModel> categoryModels) {
        this.context = context;
        this.categoryModels = categoryModels;
    }


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_category, null);
        return new CategoryViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {

        CategoryModel model = categoryModels.get(position);

        holder.categoryTxt.setText(model.getCategoryName());

        holder.itemCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QuestionActivity.class);
                intent.putExtra("catId", model.getCategoryId());
                intent.putExtra("catName",model.getCategoryName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryModels.size();
    }

    public interface OnCategoryItemClicked {

    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView categoryTxt;
        Button itemCategoryBtn;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTxt = itemView.findViewById(R.id.category_txt);
            itemCategoryBtn = itemView.findViewById(R.id.item_category_btn);
        }
    }

}











