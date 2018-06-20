package phxg.com.parttimejob.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import phxg.com.parttimejob.R;
import phxg.com.parttimejob.view.activity.MainActivity;
import phxg.com.parttimejob.view.adapter.RecyclerViewAdapter;

/**
 * Created on 2018/6/5 10:30.
 */
public class IndexFragment extends Fragment {

    private String locCity;

    //设置图片资源:url或本地资源
    String[] images = new String[]{
            "http://img.zcool.cn/community/0166c756e1427432f875520f7cc838.jpg",
            "http://img.zcool.cn/community/01fd2756e142716ac72531cbf8bbbf.jpg",
            "http://img.zcool.cn/community/0114a856640b6d32f87545731c076a.jpg",
            "http://ww3.sinaimg.cn/large/610dc034jw1f9em0sj3yvj20u00w4acj.jpg",
            "http://ww3.sinaimg.cn/large/610dc034jw1f9nuk0nvrdj20u011haci.jpg",
            "http://ww3.sinaimg.cn/large/610dc034jw1f9rc3qcfm1j20u011hmyk.jpg"};

    private MainActivity mActivity;

    @BindView(R.id.index_city)
    TextView mCity;
    @BindView(R.id.index_edittext)
    EditText mEdit;
    @BindView(R.id.index_check)
    TextView mCheck;
    @BindView(R.id.index_item1)
    TextView mItem1;
    @BindView(R.id.index_item2)
    TextView mItem2;
    @BindView(R.id.index_item3)
    TextView mItem3;
    @BindView(R.id.index_item4)
    TextView mItem4;
    @BindView(R.id.index_item5)
    TextView mItem5;
    @BindView(R.id.index_item6)
    TextView mItem6;
    @BindView(R.id.index_item7)
    TextView mItem7;
    @BindView(R.id.index_item8)
    TextView mItem8;
    @BindView(R.id.index_banner)
    Banner mBanner;
    @BindView(R.id.index_refresh)
    PullToRefreshLayout mRefresh;
    @BindView(R.id.index_recycler_list)
    RecyclerView mRecyclerView;

    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    List<String> mImageList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View indexView = inflater.inflate(R.layout.fragment_index, container, false);

        mActivity = (MainActivity) getActivity();
        ButterKnife.bind(this, indexView);
        initData();
        return indexView;
    }


    private void initData() {

        //2.轮播图
        //把图片放到集合中
        mImageList.clear();
        mImageList.addAll(Arrays.asList(images));
        //调用ImageApp()方法实现图片的加载
        mBanner.setImageLoader(new ImageApp());
        mBanner.setImages(mImageList);
        mBanner.start();

        //3.信息列表
        mLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        mAdapter = new RecyclerViewAdapter(getData());
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        mRecyclerView.setAdapter(mAdapter);
        //item点击
        //mAdapter.setOnItemClickListener();

        //4.下拉刷新，上拉加载
        mRefresh.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 结束刷新
                        mRefresh.finishRefresh();
                    }
                }, 2000);
            }

            @Override
            public void loadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 结束刷新
                        mRefresh.finishLoadMore();
                    }
                }, 2000);
            }
        });
    }

    @OnClick({R.id.index_city, R.id.index_check, R.id.index_item1, R.id.index_item2, R.id.index_item3,
            R.id.index_item4, R.id.index_item5, R.id.index_item6, R.id.index_item7, R.id.index_item8})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.index_city:
                //选择城市
                break;
            case R.id.index_check:
                //搜索
                break;
            case R.id.index_item1:

                break;
            case R.id.index_item2:

                break;
            case R.id.index_item3:

                break;
            case R.id.index_item4:

                break;
            case R.id.index_item5:

                break;
            case R.id.index_item6:

                break;
            case R.id.index_item7:

                break;
            case R.id.index_item8:

                break;
        }
    }

    //测试数据
    private ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        String temp = " item";
        for (int i = 0; i < 20; i++) {
            data.add(i + temp);
        }
        return data;
    }

    //加载图片的类
    public class ImageApp extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);//Glide加载图片
        }
    }

}
