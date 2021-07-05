package com.hualing.rydwzybrowser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

//https://blog.csdn.net/weixin_40438421/article/details/85700109
//net：：ERR_CLEARTEXT_NOT_PERMITTED解决方案：https://blog.csdn.net/weixin_44618862/article/details/99611917
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获得控件
        WebView webView = (WebView) findViewById(R.id.browser_wv);

        // 设置WebView属性，能够执行Javascript脚本,但是好像不起作用
        webView.getSettings().setJavaScriptEnabled(true);
        MyWebChromeClient myWebChromeClient = new MyWebChromeClient();
        webView.setWebChromeClient(myWebChromeClient);

        try {
            // 设置打开的页面地址
            //webView.loadUrl("http://192.168.2.166:8080/PositionPhZY/phone/goPage?page=index");
            webView.loadUrl("http://www.qrcodesy.com:8080/PositionPhZY/phone/goPage?page=login");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                // 把初始页面Url加入历史记录

                return true;
            }
        });
    }
}
