package com.example.thinkpad.ez300k;

import retrofit.RestAdapter;

/**
 * Created by ThinkPad on 31.10.2015.
 */
public class Client{
    static private AllInterfaces client;
    private static final String servUrl = "http://10.25.3.190:1488";
    private Client(){}
    public static AllInterfaces sharedInstance(){
        if(client == null){
            RestAdapter what = new RestAdapter.Builder().setEndpoint(servUrl).build();
            client = what.create(AllInterfaces.class);
        }
        return client;
    }
}
