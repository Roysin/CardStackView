package org.roysin.cardstackview.utils;

import android.util.Log;

/**
 * Created by Administrator on 2016/5/4.
 */
public class LogTool {
    public static final String TAG = "CardStackViewDebug";
    public static void d(String  msg) {
        Log.d(TAG,msg);
    }
    public static void i(String  msg) {
        Log.i(TAG,msg);
    }
    public static void e(String  msg) {
        Log.e(TAG,msg);
    }
}
