package com.gold.ehliyetuygulamasi.model;

public class HandSign {

    public String handName, handImage;

    public HandSign(String handName, String handImage){
        this.handName = handName;
        this.handImage = handImage;
    }

    public String getHandName() {
        return handName;
    }

    public void setHandName(String handName) {
        this.handName = handName;
    }

    public String getHandImage() {
        return handImage;
    }

    public void setHandImage(String handImage) {
        this.handImage = handImage;
    }
}
