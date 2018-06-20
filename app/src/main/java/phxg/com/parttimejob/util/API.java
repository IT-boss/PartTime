package phxg.com.parttimejob.util;

import android.os.Environment;

/**
 * Created on 2018/4/8.
 */

public class API {

    //欢迎页广告链接
    public static final String ADVANCE_URL = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1528179054224&di=1a4b00409eb0b6b109bf12e2c0fe8863&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201511%2F18%2F20151118224854_5xYtr.jpeg";

    //彩票数据请求
    //双色球
    public static final String SHUANGSEQIU = "http://f.apiplus.net/ssq-20.json";
    //大乐透
    public static final String DALETOU = "http://f.apiplus.net/dlt-20.json";
    //七星彩
    public static final String QIXINGCAI = "http://f.apiplus.net/qxc-20.json";
    //排列3
    public static final String PAILIE3 = "http://f.apiplus.net/pl3-20.json";


    //本地存储路径
    public static final String SDCARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/part_time";
    public static final String DB_PATH = SDCARD_PATH + "/db";
    public static final String IMG_PATH = SDCARD_PATH + "/img";

}
