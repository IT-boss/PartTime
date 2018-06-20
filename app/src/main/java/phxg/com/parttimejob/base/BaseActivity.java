package phxg.com.parttimejob.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import phxg.com.parttimejob.R;
import phxg.com.parttimejob.service.LocationService;
import phxg.com.parttimejob.util.AcManager;
import phxg.com.parttimejob.view.LoadingDialog;
import phxg.com.parttimejob.view.activity.LoginActivity;


/**
 * Created on 2018/1/17.
 */


@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    public Context context;

    protected Handler mHandler;

    //private BaseApplication mApp;

    public HashMap<String, String> params;
    private List<FragmentActivity> list = new ArrayList<>();

    private Toast mToast = null;
    private boolean isExit = false;


    public LocationService mLocationService;

    private ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder service) {
            mLocationService = ((LocationService.LocationBinder) service).getService();
        }

        public void onServiceDisconnected(ComponentName className) {
            mLocationService = null;
        }
    };

    /**
     * 开始服务
     */
    public void startLocationService() {
        //绑定Service
        Intent intent = new Intent(this, LocationService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * 停止服务
     */
    public void stopLocationService() {
        unbindService(mConnection);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        context = this;

        mHandler = new Handler();
        params = new HashMap<>();
        AcManager.getInstance().pushOneActivity(this);
        list.add(this);
    }

    /**
     * 打开指定的activity
     */
    public void openAC(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(context, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelToast();
        list.remove(this);
        AcManager.getInstance().popOneActivity(this);
    }

    public void finishAll() {
        AcManager.getInstance().finishAllActivity();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean flag;
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                onPressedBack();
                flag = true;
                break;

            default:
                flag = super.onKeyDown(keyCode, event);
                break;
        }
        return flag;
    }


    public void onPressedBack() {
        if (isExit) {
            finishAll();
        } else {
            isExit = true;
            showToast("再次点击退出程序");
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 1500);
        }
    }

    private LoadingDialog dialog;


    /**
     * 显示加载动画
     */
    public void showProgressDialog() {
        if (dialog == null) {
            dialog = new LoadingDialog(context);
            dialog.setCanceledOnTouchOutside(false);
        }
        dialog.show();
    }

    /**
     * 隐藏加载动画
     */
    public void closeProgressDialog() {
        if (null != dialog && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * Toast
     *
     * @param text
     */
    public void showToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

    public void showToastOnUIThread(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mToast == null) {
                    mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                } else {
                    mToast.setText(text);
                    mToast.setDuration(Toast.LENGTH_SHORT);
                }
                mToast.show();
            }
        });
    }

    /**
     * 获取是否夜间模式
     */
    public boolean isNight() {
        SharedPreferences preferences = context.getSharedPreferences("textapp", Context.MODE_PRIVATE);
        return preferences.getBoolean("isNight", false);
    }

    /**
     * 判断是否为手机号码
     */
    public static boolean isPhone(String inputText) {
        Pattern p = Pattern.compile("^((1[3,4,5,6,7,8][0-9])|(14[5,7])|(17[0,6,7,8]))\\d{8}$");
        Matcher m = p.matcher(inputText);
        return m.matches();
    }

    /**
     * 登录提醒D
     */
    public void showLoginDialog() {
        new AlertDialog.Builder(context).setMessage("您还未登录，请先前往登录！")
                .setPositiveButton("取消", null)
                .setNeutralButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        openAC(LoginActivity.class, null);
                    }
                }).create().show();

    }

    /**
     * 判断字符串是否为空
     */
    public boolean isEmpty(String str) {
        if (str == null || str.equals("")) {
            return true;
        }
        return false;
    }

    /**
     * 强制隐藏输入法键盘
     *
     * @param context Context
     * @param view    EditText
     */
    public void hideInput(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /* ---------------------------Android 6.0 7.0 权限申请------------------------------ */

    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };

    private static final int PERMISSON_REQUESTCODE = 0;
    private static final int SETTING_REQUESTCODE = 1;

    /**
     * 判断是否需要检测，防止不停的弹框
     */
    public boolean isNeedCheck = true;

    /**
     * @param permissions
     * @since 2.5.0
     */
    public void checkPermissions(String... permissions) {
        List<String> needRequestPermissionList = findDeniedPermissions(permissions);
        if (null != needRequestPermissionList
                && needRequestPermissionList.size() > 0) {
            ActivityCompat.requestPermissions(this,
                    needRequestPermissionList.toArray(
                            new String[needRequestPermissionList.size()]),
                    PERMISSON_REQUESTCODE);
        }
    }

    /**
     * 获取权限集中需要申请权限的列表
     * C
     *
     * @param permissions
     * @return
     * @since 2.5.0
     */
    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissionList = new ArrayList<String>();
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this,
                    perm) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    this, perm)) {
                needRequestPermissionList.add(perm);
            }
        }
        return needRequestPermissionList;
    }

    /**
     * 检测是否说有的权限都已经授权
     *
     * @param grantResults
     * @return
     * @since 2.5.0
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] paramArrayOfInt) {
        if (requestCode == PERMISSON_REQUESTCODE) {
            if (!verifyPermissions(paramArrayOfInt)) {
                showMissingPermissionDialog();
                isNeedCheck = false;
            }
        }
    }

    /**
     * 显示提示信息
     *
     * @since 2.5.0
     */
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.notifyTitle);
        builder.setMessage(R.string.notifyMsg);

        // 拒绝, 退出应用
        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

        builder.setPositiveButton(R.string.setting,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                });

        builder.setCancelable(false);

        builder.show();
    }

    /**
     * 启动应用的设置
     *
     * @since 2.5.0
     */
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, SETTING_REQUESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SETTING_REQUESTCODE) {
            checkPermissions(needPermissions);
        }
    }
}
