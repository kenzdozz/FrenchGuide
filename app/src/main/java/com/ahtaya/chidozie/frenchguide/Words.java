package com.ahtaya.chidozie.frenchguide;

import android.graphics.drawable.Drawable;

/**
 * Created by CHIDOZIE on 12/11/2017.
 */
public class Words {

    private String enWord, frWord;
    private int imgId = 0;
    private int mColorId = 0, mAudio = 0;

    public Words(String en, String fr, int audio){
        enWord = en;
        frWord = fr;
        mAudio = audio;
    }

    public Words(String en, String fr, int id, String type, int audio){
        enWord = en;
        frWord = fr;
        mAudio = audio;
        if (type.equals("color")) {
            mColorId = id;
        }else {
            imgId = id;
        }
    }

    public String getEnWord(){ return enWord; }

    public String getFrWord() {
        return frWord;
    }

    public int getImgId() { return imgId; }

    public int getmColorId() { return mColorId; }

    public int getmAudio() { return mAudio; }
}
