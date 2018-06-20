package phxg.com.parttimejob.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import java.util.ArrayList;

import butterknife.ButterKnife;
import phxg.com.parttimejob.R;
import phxg.com.parttimejob.view.activity.MainActivity;
import phxg.com.parttimejob.view.adapter.RecyclerViewAdapter;

/**
 * Created on 2018/6/5 10:31.
 */
public class InfoFragment extends Fragment implements View.OnClickListener {


    //private ImageView mBack;
    private ImageView mScan;
    private EditText mCode;
    private Button mSearch;
    private ImageView mSelect;

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private PullToRefreshLayout mRefresh;

    private MainActivity mActivity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View infoView = inflater.inflate(R.layout.fragment_project, container, false);
        //mBack = infoView.findViewById(R.id.project_back);
        mCode = infoView.findViewById(R.id.project_code);
        mSearch = infoView.findViewById(R.id.project_search);
        mSelect = infoView.findViewById(R.id.project_select);
        mRecyclerView = infoView.findViewById(R.id.project_recycler_list);
        mRefresh = infoView.findViewById(R.id.project_refresh);
        mScan = infoView.findViewById(R.id.project_scan);

        mActivity = (MainActivity) getActivity();
        ButterKnife.bind(this, infoView);
        initData();
        return infoView;
    }

    private void initData() {
        mLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        mAdapter = new RecyclerViewAdapter(getData());

        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        mRecyclerView.setAdapter(mAdapter);

        //mBack.setOnClickListener(this);
        mSearch.setOnClickListener(this);
        mSelect.setOnClickListener(this);
        mScan.setOnClickListener(this);

        //item点击
        mAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //点击跳转，传递数据
                //Bundle b = new Bundle();
                //b.putParcelable("item",mData.get(position));
                //b.putString("item", getData().get(position));
                //mActivity.openAC(ProjectActivity.class, b);
                //Log.d(TAG, "onItemClick: " + position);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                //Log.d(TAG, "onItemLongClick: " + position);
            }
        });

        //下拉刷新，下拉加载
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

    //测试数据
    private ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        String temp = " item";
        for (int i = 0; i < 20; i++) {
            data.add(i + temp);
        }
        return data;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.project_search:
                //查询显示(弹出选项对话框)

                break;
            case R.id.project_select:
                //筛选显示

                break;
            case R.id.project_scan:
                //打开扫描

                break;
        }
    }

}
