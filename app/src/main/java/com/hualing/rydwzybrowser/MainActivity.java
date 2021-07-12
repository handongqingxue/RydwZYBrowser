package com.hualing.rydwzybrowser;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

//https://blog.csdn.net/weixin_40438421/article/details/85700109
//net：：ERR_CLEARTEXT_NOT_PERMITTED解决方案：https://blog.csdn.net/weixin_44618862/article/details/99611917
public class MainActivity extends AppCompatActivity {

    //private static final String WEB_URL = "http://192.168.2.166:8080/PositionPhZY/phone/goPage?page=index";
    private static final String WEB_URL = "http://www.qrcodesy.com:8080/PositionPhZY/phone/goPage?page=index";
    private WebView currentWV;
    private TextView titleUserIdTV;
    private int role;

    public WebView getCurrentWV() {
        return currentWV;
    }
    public void setCurrentWV(WebView currentWV) {
        this.currentWV = currentWV;
    }

    public void setTitleUserIdTVText(String text){
        titleUserIdTV.setText(text);
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
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
        titleUserIdTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View luiView = LayoutInflater.from(MainActivity.this).inflate(R.layout.login_user_info,null);
                final TextView userIdTV = luiView.findViewById(R.id.ad_userId_tv);
                userIdTV.setText(titleUserIdTV.getText().toString());
                final TextView roleTV = luiView.findViewById(R.id.ad_role_tv);
                String roleStr=null;
                switch (role){
                    case 1:
                        roleStr="管理员";
                        break;
                    case 2:
                        roleStr="普通用户";
                        break;
                    case 3:
                        roleStr="来宾用户";
                        break;
                    case 4:
                        roleStr="手机用户";
                        break;
                }
                roleTV.setText(roleStr);
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setView(luiView)
                        .setNegativeButton("确定", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                userIdTV.setText("");
                                roleTV.setText("");
                            }
                        })
                        .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                WebView webView = currentWV;
                                webView.loadUrl("javascript:document.getElementById('exit_but').click()");
                                userIdTV.setText("");
                                roleTV.setText("");
                            }
                        });
                AlertDialog alertDialog = dialog.show();
                //设置按钮样式：https://blog.csdn.net/xiayiye5/article/details/83080623
                Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);//确定
                LinearLayout.LayoutParams negativeButtonParams = (LinearLayout.LayoutParams)negativeButton.getLayoutParams();
                negativeButtonParams.setMargins(0,0,120,0);
                //negativeButton.setBackgroundColor(Color.rgb(255,0,0));

                Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);//退出
                LinearLayout.LayoutParams positiveButtonParams = (LinearLayout.LayoutParams) positiveButton.getLayoutParams();
                positiveButtonParams.setMargins(50,0,60,0);
                //positiveButton.setBackgroundColor(Color.rgb(255,0,0));
            }
        });

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
