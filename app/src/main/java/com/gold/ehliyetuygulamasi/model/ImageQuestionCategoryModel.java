package com.gold.ehliyetuygulamasi.model;

public class ImageQuestionCategoryModel {
    private String categoryId, categoryName;

    public ImageQuestionCategoryModel(String categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public  ImageQuestionCategoryModel(){

    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
