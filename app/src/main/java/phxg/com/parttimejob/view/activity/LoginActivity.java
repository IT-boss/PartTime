package phxg.com.parttimejob.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import phxg.com.parttimejob.R;
import phxg.com.parttimejob.base.BaseActivity;
import phxg.com.parttimejob.util.PreferenceUtil;

/**
 * Created on 2018/6/5 10:36.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout mTop, mCenter, mSubmit;
    private View mBottom;
    private EditText mName, mPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (PreferenceUtil.readBoolean(context, PreferenceUtil.IS_LOGIN, false)) {
            openAC(MainActivity.class, null);
        }

        initView();
        initData();
    }

    private void initView() {
        mTop = findViewById(R.id.login_top);
        mCenter = findViewById(R.id.login_center);
        mBottom = findViewById(R.id.login_bottom);

        mName = findViewById(R.id.login_name);
        mPwd = findViewById(R.id.login_pwd);
        mSubmit = findViewById(R.id.login_btn);
    }

    private void initData() {
        mTop.setOnClickListener(this);
        mCenter.setOnClickListener(this);
        mBottom.setOnClickListener(this);
        mSubmit.setOnClickListener(this);

        checkPermissions(needPermissions);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_top:
            case R.id.login_center:
            case R.id.login_bottom:
                hideInput(this, view);
                break;
            case R.id.login_btn:
                checkForLogin();
                break;
        }
    }

    private void checkForLogin() {
        String name = mName.getText().toString().trim();
        String pwd = mPwd.getText().toString().trim();

        if (isEmpty(name) || isEmpty(pwd)) {
            showToast(getResources().getText(R.string.login_error).toString());
        } else {
            //postAsyncHttp(name, pwd);
            openAC(MainActivity.class, null);
        }

    }

    private void postAsyncHttp(String name, String pwd) {
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {
                    private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(url, cookies);
                        cookieStore.put(HttpUrl.parse("http://192.168.31.231:8080/shiro-2"), cookies);
                        for (Cookie cookie : cookies) {
                            System.out.println("cookie Name:" + cookie.name());
                            System.out.println("cookie Path:" + cookie.path());
                        }
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(HttpUrl.parse("http://192.168.31.231:8080/shiro-2"));
                        if (cookies == null) {
                            System.out.println("没加载到cookie");
                        }
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                })
                .build();
        RequestBody formBody = new FormBody.Builder()
                .add("username", name)
                .add("password", pwd)
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.31.231:8080/shiro-2/shiro-login")
                .post(formBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                showToast("登录失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                //Log.d(TAG, "--response:--" + str);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        PreferenceUtil.write(context, PreferenceUtil.IS_LOGIN, true);
                        showToast("登录成功");
                        openAC(MainActivity.class, null);
                    }
                });
            }

        });
    }

}
