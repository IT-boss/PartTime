package phxg.com.parttimejob.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {
    public final static String PRE_SHARE_APP = "pre_share_app";
    //用户是否登录
    public final static String IS_LOGIN = "is_login";
    //保存用户ID
    public final static String USER_ID = "user_id";
    //保存用户名
    public final static String USER_NAME = "user_name";
    //保存用户昵称
    public final static String USER_NICK = "user_nick";
    //保存用户昵称
    public final static String USER_TYPE = "user_type";
    //保存注册成功后登录的操作
    public final static String REGISTER_SUCCESS = "register_success";
    //是否被迫下线[区分在哪个模块播放]
    public final static String MEDIAPLAYER_LOCATION = "mediaplayer_location";
    //保存选择测试的服务器地址
    public final static String HTTP_SERVER = "http_server";
    //保存用户设置的播放模式
    public final static String PLAY_MODE  = "play_mode";
    //保存闹钟提示框状态
    public final static String ALARM_CLOCK_EXPLAIN  = "alarm_clock_explain";
    //保存开始修行的时间
    public final static String START_PRACTICE_TIME  = "start_practice_time";
    //修行资源库播放时保存播放歌曲的信息【刷新后判断该曲目position】
    public final static String SAVA_LIBRARY_AUDIO  = "sava_library_audio";
    //判断程序是否是第一次进入闹钟列表页面
    public final static String IS_FIRST  = "is_first";
    //判断程序是否是第一次进入主页面【新手指导】
    public final static String IS_MAIN_FIRST  = "is_main_first";

    public static void write(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PRE_SHARE_APP,
                Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).commit();
    }

    public static void write(Context context, String key, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PRE_SHARE_APP,
                Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(key, value).commit();
    }

    public static void write(Context context, String key, Boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PRE_SHARE_APP,
                Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(key, value).commit();
    }

    public static String readString(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PRE_SHARE_APP,
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public static int readInt(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PRE_SHARE_APP,
                Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, 0);
    }

    public static Boolean readBoolean(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PRE_SHARE_APP,
                Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, true);
    }

    public static Boolean readBooleanDefTrue(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PRE_SHARE_APP,
                Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, true);
    }

    public static void remove(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PRE_SHARE_APP,
                Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(key).commit();
    }

    public static Boolean readBoolean(Context context, String key,boolean is) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PRE_SHARE_APP,
                Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, is);
    }

}
