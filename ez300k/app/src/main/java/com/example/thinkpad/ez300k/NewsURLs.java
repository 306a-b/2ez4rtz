package com.example.thinkpad.ez300k;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by ThinkPad on 31.10.2015.
 */
public class NewsURLs {
    static private ArrayList<String> urlList;
    private NewsURLs(){}

    public void pushToList(String url){
        urlList.add(url);
    }

    public static ArrayList<String> NewsURLs(){
        if(urlList == null){
            urlList = new ArrayList<>();
        }
        return urlList;
    }
}
