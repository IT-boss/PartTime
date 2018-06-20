package phxg.com.parttimejob.view.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.ButterKnife;
import butterknife.OnClick;
import phxg.com.parttimejob.R;
import phxg.com.parttimejob.util.API;
import phxg.com.parttimejob.view.activity.LoginActivity;
import phxg.com.parttimejob.view.activity.MainActivity;

/**
 * Created on 2018/6/5 10:31.
 */
public class MineFragment extends Fragment {

    private MainActivity mActivity;

    private ImageView mPhoto;
    private TextView mQuit, mName;
    private TextView mLiShi, mXinYong, mSetting;
    private LinearLayout mYongJin, mYaoQing;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mineView = inflater.inflate(R.layout.fragment_mine, container, false);
        mQuit = mineView.findViewById(R.id.mine_quit);
        mPhoto = mineView.findViewById(R.id.user_photo);
        mName = mineView.findViewById(R.id.mine_name);
        mLiShi = mineView.findViewById(R.id.mine_lishi);
        mXinYong = mineView.findViewById(R.id.mine_xinyong);
        mYongJin = mineView.findViewById(R.id.mine_yongjin);
        mYaoQing = mineView.findViewById(R.id.mine_yaoqingma);
        mSetting = mineView.findViewById(R.id.mine_setting);

        mActivity = (MainActivity) getActivity();
        ButterKnife.bind(this, mineView);
        initView();
        return mineView;
    }

    private void initView() {

        Glide.with(this)
                .load(API.ADVANCE_URL)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(mPhoto);

       /* Glide.with(this)
                .load(API.ADVANCE_URL)
                .transform(new RoundTransformation(mActivity, 240))
                //.bitmapTransform(new RotateTransformation(mContext, 20))
                .into(mPhoto);*/
    }

    @OnClick(R.id.mine_quit)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_quit:
                mActivity.openAC(LoginActivity.class, null);
                mActivity.finish();
                break;
        }
    }
}
