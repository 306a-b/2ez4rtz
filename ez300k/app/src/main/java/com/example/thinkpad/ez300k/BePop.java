package com.example.thinkpad.ez300k;

import android.media.Image;

/**
 * class
 */
public class BePop {
    public int id;
    public String songsName;
    public String artistName;
    public String image; /* переделать */
    public String url;


    public BePop(int id, String songsName, String image, String artistName, String url) {
        this.id = id;
        this.songsName = songsName;
        this.image = image;
        this.artistName = artistName;
        this.url = url;
    }
}
