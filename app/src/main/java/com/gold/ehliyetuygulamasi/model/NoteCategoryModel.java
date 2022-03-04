package com.gold.ehliyetuygulamasi.model;

public class NoteCategoryModel {

    private String noteCategoryId,noteCategoryName;

    public NoteCategoryModel(String noteCategoryId, String noteCategoryName) {
        this.noteCategoryId = noteCategoryId;
        this.noteCategoryName = noteCategoryName;
    }
    public NoteCategoryModel(){

    }

    public String getNoteCategoryId() {
        return noteCategoryId;
    }

    public void setNoteCategoryId(String noteCategoryId) {
        this.noteCategoryId = noteCategoryId;
    }

    public String getNoteCategoryName() {
        return noteCategoryName;
    }

    public void setNoteCategoryName(String noteCategoryName) {
        this.noteCategoryName = noteCategoryName;
    }
}
