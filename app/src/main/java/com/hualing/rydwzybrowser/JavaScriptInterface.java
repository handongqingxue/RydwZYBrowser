package com.hualing.rydwzybrowser;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.webkit.JavascriptInterface;

//https://blog.csdn.net/yushuangping/article/details/84134944
public class JavaScriptInterface {
    MainActivity mContext;
    final Handler myHandler = new Handler();

    JavaScriptInterface(MainActivity c) {
        mContext = c;
    }

    @JavascriptInterface
    public void getPageName(String webText){
        final String text = webText;
        myHandler.post(new Runnable() {
            @Override
            public void run() {
                // This gets executed on the UI thread so it can safely modify Views
                //myTextView.setText(msgeToast);
                Log.e("getPageName===",""+text);
                //mContext.setTitleUserIdTVText(text);
                if(!"login".equals(text))
                    mContext.runAndroidFunction(mContext.getCurrentWV(),"showUserId");
            }
        });

        //Toast.makeText(mContext, webMessage, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void showUserId(String webText){
        final String text = webText;
        myHandler.post(new Runnable() {
            @Override
            public void run() {
                // This gets executed on the UI thread so it can safely modify Views
                //myTextView.setText(msgeToast);
                Log.e("showUserId===",""+text);
                mContext.setTitleUserIdTVText(text);
            }
        });

        //Toast.makeText(mContext, webMessage, Toast.LENGTH_SHORT).show();
    }
}
