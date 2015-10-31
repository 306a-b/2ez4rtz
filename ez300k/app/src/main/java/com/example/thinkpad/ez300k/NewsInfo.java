package com.example.thinkpad.ez300k;

import java.util.ArrayList;

public class NewsInfo {
    public enum CARD_TYPE { ALBUM_IMAGE, ALBUM_NO_IMAGE };

    public String albumImageURL;
    public String singerImageURL;
    public String albumName;
    public String singerName;
    public CARD_TYPE type;

    public CARD_TYPE getType() {
        return type;
    }

    public void setType(CARD_TYPE type) {
        this.type = type;
    }
    public ArrayList<Song> songsList;

    public NewsInfo(String singerName, String albumName, String imageURL, String singerImage, ArrayList<Song> songsList){
        this.singerName = singerName;
        this.albumName = albumName;
        this.albumImageURL = imageURL;
        this.singerImageURL = singerImage;
        this.songsList = songsList;
    }

}
