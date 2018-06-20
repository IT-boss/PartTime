package phxg.com.parttimejob.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import phxg.com.parttimejob.R;
import phxg.com.parttimejob.base.BaseActivity;
import phxg.com.parttimejob.util.API;
import phxg.com.parttimejob.view.CountDownView;

/**
 * Created on 2018/6/5 11:06.
 */
public class SplashActivity extends BaseActivity {

    private ImageView mSplashImage;
    private CountDownView mCountView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mSplashImage = findViewById(R.id.splash_image);
        mCountView = findViewById(R.id.count_view);
        initView();
    }

    private void initView() {
        //自定义广告背景
        Glide.with(this).load(API.ADVANCE_URL).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                mCountView.startCountDown();
                return false;
            }

            //这个用于监听图片是否加载完成
            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                //图片加载成功后
                mCountView.startCountDown();
                return false;
            }
        }).error(R.drawable.splash_in).into(mSplashImage);

        //自动跳转
        mCountView.setAddCountDownListener(new CountDownView.OnCountDownFinishListener() {
            @Override
            public void countDownFinished() {
                openAC(LoginActivity.class, null);
                SplashActivity.this.finish();
            }
        });

        //手动跳转
        mCountView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCountView.stopCountDown();
                openAC(LoginActivity.class, null);
                SplashActivity.this.finish();
            }
        });
    }

}
