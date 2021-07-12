package com.hualing.rydwzybrowser;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.webkit.JavascriptInterface;

//如何使用addJavaScriptInterface：https://blog.csdn.net/xunfan/article/details/44587861?utm_medium=distribute.pc_relevant_download.none-task-blog-baidujs-1.nonecase&depth_1-utm_source=distribute.pc_relevant_download.none-task-blog-baidujs-1.nonecase
//addJavascriptInterface报错解决方案：https://blog.csdn.net/yushuangping/article/details/84134944
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
                //Log.e("getPageName===",""+text);
                //mContext.setTitleUserIdTVText(text);
                if(!"login".equals(text))
                    mContext.runAndroidFunction(mContext.getCurrentWV(),"showUserInfo");
            }
        });

        //Toast.makeText(mContext, webMessage, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void showUserInfo(String webText){
        String[] userInfoArr = webText.split(",");
        final String userId = userInfoArr[0];
        final int role = Integer.valueOf(userInfoArr[1]);
        myHandler.post(new Runnable() {
            @Override
            public void run() {
                // This gets executed on the UI thread so it can safely modify Views
                //myTextView.setText(msgeToast);
                //Log.e("showUserInfo===",""+text);
                mContext.setTitleUserIdTVText(userId);
                mContext.setRole(role);
            }
        });

        //Toast.makeText(mContext, webMessage, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void removeUserId(){
        myHandler.post(new Runnable() {
            @Override
            public void run() {
                mContext.setTitleUserIdTVText("");
            }
        });
    }
}
