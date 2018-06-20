package phxg.com.parttimejob.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created on 2018/4/8.
 */

public class DisplayUtils {

    /**
     * convert px to its equivalent dp
     *
     * 将px转换为与之相等的dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale =  context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * convert dp to its equivalent px
     *
     * 将dp转换为与之相等的px
     */
    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * convert px to its equivalent sp
     *
     * 将px转换为sp
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * convert sp to its equivalent px
     *
     * 将sp转换为px
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    //获取像素密度densityDpi
    public static int getDensityDpi(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        // int Density = displayMetrics.density;
        // int height = displayMetrics.heightPixels;
        // int width = displayMetrics.widthPixels;
        return displayMetrics.densityDpi;
    }
}