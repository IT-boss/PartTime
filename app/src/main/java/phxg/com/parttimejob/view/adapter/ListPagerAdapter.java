package phxg.com.parttimejob.view.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created on 2018/6/5 15:39.
 */
public class ListPagerAdapter extends PagerAdapter {

    private List<View> imageList;
    private Context context;

    public ListPagerAdapter(Context context, List<View> imageViewList) {
        this.imageList = imageViewList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    // 3. 指定复用的判断逻辑, 固定写法
    @Override
    public boolean isViewFromObject(View view, Object object) {
//          System.out.println("isViewFromObject: "+(view == object));
        // 当划到新的条目, 又返回来, view是否可以被复用.
        // 返回判断规则
        return view == object;
    }

    /**
     * 1. 返回要显示的条目内容, 创建条目
     * container: 容器: ViewPager
     * position: 当前要显示条目的位置 0 -> size
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //System.out.println("instantiateItem初始化: " + position);

        int newPosition = position % imageList.size();
        View imageView = imageList.get(newPosition);
        // a. 把View对象添加到container中
        container.addView(imageView);
        // b. 把View对象返回给框架, 适配器
        return imageView; // 必须重写, 否则报异常

       /* View view=imageList.get(position);
        if (myViewListenner != null){
            view.setOnClickListener(new View.OnClickListener() {
                // 处理极端情况，此情况出现在轮播最后一张图切换到第一张图，ViewPaper实现轮播原理决定的。
                //因为view层次和数据层次进行分开处理了,所有需要额外写下
                @Override
                public void onClick(View view) {
                    vPonsiton = position;
                    //  如果不写size!=0会报异常java.lang.ArithmeticException: divide by zero size为0的情况下
                    if (vPonsiton >size && size!=0) {
                        vPonsiton = vPonsiton % size;
                    }
                    myViewListenner.onItemClick(vPonsiton);
                }
            });
        }
        container.addView(view);
        return view;*/
    }

    // 2. 销毁条目
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // object 要销毁的对象
        System.out.println("destroyItem销毁: " + position);
        container.removeView((View) object);
    }


}
