package phxg.com.parttimejob.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import phxg.com.parttimejob.R;
import phxg.com.parttimejob.base.BaseActivity;
import phxg.com.parttimejob.view.fragment.IndexFragment;
import phxg.com.parttimejob.view.fragment.InfoFragment;
import phxg.com.parttimejob.view.fragment.MineFragment;

public class MainActivity extends BaseActivity implements View.OnClickListener {


    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragment = new ArrayList<Fragment>();

    private TextView mIndex, mNews, mMine;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startLocationService();

        initView();
        initData();
    }

    private void initView() {
        mIndex = findViewById(R.id.index);
        mNews = findViewById(R.id.news);
        mMine = findViewById(R.id.mine);
        mViewPager = findViewById(R.id.main_vpager);
    }

    private void initData() {
        mIndex.setOnClickListener(this);
        mNews.setOnClickListener(this);
        mMine.setOnClickListener(this);

        mFragment.add(new IndexFragment());
        mFragment.add(new InfoFragment());
        mFragment.add(new MineFragment());

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            @Override
            public int getCount() {
                return mFragment.size();
            }
        };

        mViewPager.setAdapter(mAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                tabSelect(position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //首次显示主页
        mIndex.setSelected(true);
        mIndex.setTextColor(getResources().getColor(R.color.colorTheme));
        mViewPager.setCurrentItem(0);
    }

    @Override
    public void onClick(View view) {
        int tag = Integer.valueOf(view.getTag().toString().trim());
        mViewPager.setCurrentItem(tag);
        tabSelect(tag);
    }

    //tab选项
    private void tabSelect(int tag) {
        setTabNormal();

        switch (tag) {
            case 0:
                mIndex.setSelected(true);
                mIndex.setTextColor(getResources().getColor(R.color.colorTheme));
                break;
            case 1:
                mNews.setSelected(true);
                mNews.setTextColor(getResources().getColor(R.color.colorTheme));
                break;
            case 2:
                mMine.setSelected(true);
                mMine.setTextColor(getResources().getColor(R.color.colorTheme));
                break;
        }

    }

    private void setTabNormal() {
        mIndex.setSelected(false);
        mIndex.setTextColor(getResources().getColor(R.color.colorText));
        mNews.setSelected(false);
        mNews.setTextColor(getResources().getColor(R.color.colorText));
        mMine.setSelected(false);
        mMine.setTextColor(getResources().getColor(R.color.colorText));
    }

}
