package com.hualing.rydwzybrowser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

//https://blog.csdn.net/weixin_40438421/article/details/85700109
//net：：ERR_CLEARTEXT_NOT_PERMITTED解决方案：https://blog.csdn.net/weixin_44618862/article/details/99611917
//https://blog.csdn.net/xunfan/article/details/44587861?utm_medium=distribute.pc_relevant_download.none-task-blog-baidujs-1.nonecase&depth_1-utm_source=distribute.pc_relevant_download.none-task-blog-baidujs-1.nonecase
public class MainActivity extends AppCompatActivity {

    private static final String WEB_URL = "http://192.168.2.166:8080/PositionPhZY/phone/goPage?page=index";
    //private static final String WEB_URL = "http://www.qrcodesy.com:8080/PositionPhZY/phone/goPage?page=index";
    private WebView currentWV;
    private TextView titleUserIdTV;

    public WebView getCurrentWV() {
        return currentWV;
    }
    public void setCurrentWV(WebView currentWV) {
        this.currentWV = currentWV;
    }

    public void setTitleUserIdTVText(String text){
        titleUserIdTV.setText(text);
    }

    public void runAndroidFunction(WebView webView,String action){
        webView.loadUrl("javascript:runAndroidFunction('"+action+"')");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获得控件
        WebView webView = (WebView) findViewById(R.id.browser_wv);
        titleUserIdTV= (TextView)findViewById(R.id.title_userId_tv);

        // 设置WebView属性，能够执行Javascript脚本,但是好像不起作用
        webView.getSettings().setJavaScriptEnabled(true);
        MyWebChromeClient myWebChromeClient = new MyWebChromeClient();
        webView.setWebChromeClient(myWebChromeClient);

        try {
            // 设置打开的页面地址
            final JavaScriptInterface myJavaScriptInterface
                    = new JavaScriptInterface(this);
            webView.addJavascriptInterface(myJavaScriptInterface, "AndroidFunction");
            webView.loadUrl(WEB_URL);
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

            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
                setCurrentWV(view);
                //view.loadUrl("javascript:document.getElementById('exit_but').click()");
                //view.loadUrl("javascript:runAndroidFunction('userId')");
                //view.loadUrl("javascript:runAndroidFunction('getPageName')");
                runAndroidFunction(view,"getPageName");
            }
        });
    }
}
