package org.roysin.cardstackview.view;

import android.view.View;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/5/4.
 */
public class ViewPool {
    private HashMap<String,View> viewMap;
    public ViewPool(){
        viewMap = new HashMap<>();
    }
    public void put(String key, View value){
        viewMap.put(key,value);
    }
    public View get(String key){
        return viewMap.get(key);
    }
}
