package com.example.thinkpad.ez300k;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ThinkPad on 01.11.2015.
 */
public class Group {
    @SerializedName("groupname")
    public String groupName;

    @SerializedName("groupdiscr")
    public String groupDescription;

    @SerializedName("numofsubs")
    public int numOfSubs;

    @SerializedName("url")
    public String imageURL;

    public Group(Group g){
        groupName = g.groupName;
        groupDescription = g.groupDescription;
        numOfSubs = g.numOfSubs;
        imageURL = g.imageURL;
    }
}
