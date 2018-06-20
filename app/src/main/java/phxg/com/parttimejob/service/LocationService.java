package phxg.com.parttimejob.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;


/**
 * Created  on 2018/3/22.
 */

public class LocationService extends Service {

    private static final String TAG = "LocationService";

    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    private String mLocCity = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mLocationClient = new LocationClient(getApplicationContext());//声明LocationClient类
        mLocationClient.registerLocationListener(myListener);//注册监听函数

        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        mLocationClient.setLocOption(getClientOption());

        //mLocationClient为第二步初始化过的LocationClient对象

        //调用LocationClient的start()方法，便可发起定位请求
        mLocationClient.start();
    }

    //BDAbstractLocationListener为7.2版本新增的Abstract类型的监听接口
    //原有BDLocationListener接口暂时同步保留。具体介绍请参考后文中的说明
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            double latitude = location.getLatitude();     //获取纬度信息
            double longitude = location.getLongitude();   //获取经度信息
            String time = location.getTime();             //获取时间
            String addr = location.getAddrStr();          //获取详细地址信息
            mLocCity = location.getCity();
//            Log.d(TAG, "onReceiveLocation: " + mLocCity);
        }
    }

    private LocationClientOption getClientOption() {
        LocationClientOption option = new LocationClientOption();

        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；
        option.setLocationMode(LocationMode.Hight_Accuracy);

        //可选，设置返回经纬度坐标类型，默认gcj02
        //gcj02：国测局坐标；
        //bd09ll：百度经纬度坐标；
        //bd09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标
        option.setCoorType("bd09ll");

        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效
        option.setScanSpan(2000);

        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true
        option.setOpenGps(true);

        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false
        option.setLocationNotify(true);

        //可选，定位SDK内部是一个service，并放到了独立进程。
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)
        option.setIgnoreKillProcess(false);

        //可选，设置是否收集Crash信息，默认收集，即参数为false
        option.SetIgnoreCacheException(false);

        //可选，7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位
        option.setWifiCacheTimeOut(5 * 60 * 1000);

        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false
        option.setEnableSimulateGps(false);

        //可选，是否需要地址信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的地址信息，此处必须为true
        option.setIsNeedAddress(true);

        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
        mLocationClient.setLocOption(option);

        return option;
    }

    //调用LocationClient的start()方法，便可发起定位请求
    public void startLocationService() {
        if (null != mLocationClient) {
            mLocationClient.start();
        }
    }

    //调用LocationClient的stop()方法，便可关闭定位请求
    public void stopLocationService() {
        if (null != mLocationClient && mLocationClient.isStarted()) {
            mLocationClient.stop();
        }
    }

    public String getLocCity() {
        return mLocCity;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new LocationBinder();
    }

    public class LocationBinder extends Binder {
        /**
         * 获取当前Service的实例
         *
         * @return
         */
        public LocationService getService() {
            return LocationService.this;
        }
    }

}
