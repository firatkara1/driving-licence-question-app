package com.gold.ehliyetuygulamasi.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gold.ehliyetuygulamasi.databinding.ItemNoteBinding;
import com.gold.ehliyetuygulamasi.databinding.ItemQuestionImgCategoryBinding;
import com.gold.ehliyetuygulamasi.model.CategoryModel;
import com.gold.ehliyetuygulamasi.model.ImageQuestionCategoryModel;
import com.gold.ehliyetuygulamasi.view.ImageQuestionActivity;
import com.gold.ehliyetuygulamasi.view.QuestionActivity;

import java.util.ArrayList;

public class ImageQuestionCategoryAdapter extends RecyclerView.Adapter<ImageQuestionCategoryAdapter.ImageQuestionHolder> {

    Context context;
    ArrayList<ImageQuestionCategoryModel> imageQuestionCategoryModels;

    public ImageQuestionCategoryAdapter(Context context, ArrayList<ImageQuestionCategoryModel> imageQuestionCategoryModels) {
        this.context = context;
        this.imageQuestionCategoryModels = imageQuestionCategoryModels;
    }

    @NonNull
    @Override
    public ImageQuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemQuestionImgCategoryBinding itemQuestionImgCategoryBinding = ItemQuestionImgCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ImageQuestionHolder(itemQuestionImgCategoryBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageQuestionHolder holder, int position) {

        ImageQuestionCategoryModel model = imageQuestionCategoryModels.get(position);


        holder.itemQuestionImgCategoryBinding.questionImgCategoryTxt.setText(imageQuestionCategoryModels.get(position).getCategoryName());
        holder.itemQuestionImgCategoryBinding.itemQuestionImgCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImageQuestionActivity.class);
                intent.putExtra("catId", model.getCategoryId());
                intent.putExtra("catName", model.getCategoryName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageQuestionCategoryModels.size();
    }

    public interface OnQuestionImageCategoryItemClicked {
    }

    class ImageQuestionHolder extends RecyclerView.ViewHolder {

        ItemQuestionImgCategoryBinding itemQuestionImgCategoryBinding;

        public ImageQuestionHolder(ItemQuestionImgCategoryBinding itemQuestionImgCategoryBinding) {
            super(itemQuestionImgCategoryBinding.getRoot());
            this.itemQuestionImgCategoryBinding = itemQuestionImgCategoryBinding;
        }
    }
}
























