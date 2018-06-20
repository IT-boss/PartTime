package phxg.com.parttimejob.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import phxg.com.parttimejob.R;


/**
 * Created on 2018/1/18.
 */

public class CommonUtils {

    private static final String TAG = "CommonUtils";
    private static final String DATABASE_PATH = API.DB_PATH;

    private Context mContext = null;
    private static CommonUtils mInstance = null;

    private Map<String, SQLiteDatabase> databases = new HashMap<String, SQLiteDatabase>();

    public static CommonUtils getInstance(Context context) {
        if (null == mInstance) {
            synchronized (CommonUtils.class) {
                if (mInstance == null) {
                    mInstance = new CommonUtils(context);
                }
            }
        }
        return mInstance;
    }

    private CommonUtils(Context context) {
        this.mContext = context;
    }

    //获取像素密度densityDpi
    public int getDensityDpi(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        // int Density = displayMetrics.density;
        // int height = displayMetrics.heightPixels;
        // int width = displayMetrics.widthPixels;
        return displayMetrics.densityDpi;
    }

    //SD卡是否存在
    public boolean hasSdcard() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    //判断文件是否存在
    public boolean hasFile(String path) {
        try {
            File f = new File(path);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return true;
    }

    /**
     * Check for copy
     */
    public void getDatabase(String dbfile) {

        Log.i(TAG, String.format("Create database %s", dbfile));
        String spath = DATABASE_PATH;
        String sfile = getDatabaseFile(dbfile);

        File file = new File(sfile);
        if (!file.exists()) {
            file = new File(spath);
            if (!file.exists() && !file.mkdirs()) {
                Log.i(TAG, "Create \"" + spath + "\" fail!");
                //Toast.makeText(mContext, "数据库拷贝失败" + spath, Toast.LENGTH_LONG).show();
            }
            if (!copyToDes(dbfile, sfile)) {
                Log.i(TAG, String.format("Copy %s to %s fail!", dbfile, sfile));
            }
        }
    }

    private String getDatabaseFile(String dbfile) {
        return DATABASE_PATH + "/" + dbfile;
    }

    /**
     * Copy to sdCard
     */
    private boolean copyToDes(String assetsSrc, String des) {
        Log.i(TAG, "Copy " + assetsSrc + " to " + des);
        InputStream istream = null;
        OutputStream ostream = null;
        try {
            istream = mContext.getResources().openRawResource(R.raw.example);
            ostream = new FileOutputStream(des);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = istream.read(buffer)) > 0) {
                ostream.write(buffer, 0, length);
            }
            istream.close();
            ostream.close();
        } catch (Exception e) {
            Log.d(TAG, "copyToFile: " + e.getMessage());
            e.printStackTrace();

            try {
                if (istream != null)
                    istream.close();
                if (ostream != null)
                    ostream.close();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
            return false;
        }
        return true;
    }

    //保存相片到文件
    public File savePhotoAsAFile(String path) throws IOException {
        String imageFileName = "jpg_" + System.currentTimeMillis() + "_";
        //获得sd卡中图片存储目录
        File storeDir = new File(path);
        if (!storeDir.exists()) {
            storeDir.mkdirs();
        }
        return File.createTempFile(imageFileName, ".jpg", storeDir);
    }

    //删除指定文件
}
