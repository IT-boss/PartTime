package phxg.com.parttimejob.base;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

/**
 * Created on 2018/1/17.
 */

public class BaseApplication extends Application {

    //private DBOpenHelper dbOpenHelper;

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

        //创建本地数据库
        /*try {
            BaseModelManager.currentUser = PreferenceUtil.readString(this, PreferenceUtil.USER_NAME);
            if (dbOpenHelper == null) {
                dbOpenHelper = DBOpenHelper.getHelper(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }

}
