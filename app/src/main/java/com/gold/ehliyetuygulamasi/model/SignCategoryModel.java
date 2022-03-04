package com.gold.ehliyetuygulamasi.model;

public class SignCategoryModel {
    private String signCategoryId,signCategoryName;

    public SignCategoryModel(String signCategoryId, String signCategoryName) {
        this.signCategoryId = signCategoryId;
        this.signCategoryName = signCategoryName;
    }

    public SignCategoryModel(){

    }

    public String getSignCategoryId() {
        return signCategoryId;
    }

    public void setSignCategoryId(String signCategoryId) {
        this.signCategoryId = signCategoryId;
    }

    public String getSignCategoryName() {
        return signCategoryName;
    }

    public void setSignCategoryName(String signCategoryName) {
        this.signCategoryName = signCategoryName;
    }
}
