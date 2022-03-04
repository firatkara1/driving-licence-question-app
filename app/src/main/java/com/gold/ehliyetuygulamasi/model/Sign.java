package com.gold.ehliyetuygulamasi.model;

import java.util.ArrayList;

public class Sign {

    private String signId,signName, signImage, signMeaning;



    public Sign(String signName, String signImage, String signMeaning, String signId) {
        this.signId = signId;
        this.signName = signName;
        this.signImage = signImage;
        this.signMeaning = signMeaning;
    }

    public String getSignMeaning() {
        return signMeaning;
    }

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    public void setSignMeaning(String signMeaning) {
        this.signMeaning = signMeaning;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getSignImage() {
        return signImage;
    }

    public void setSignImage(String signImage) {
        this.signImage = signImage;
    }
}
