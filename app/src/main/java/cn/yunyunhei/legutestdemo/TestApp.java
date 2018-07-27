package cn.yunyunhei.legutestdemo;

import android.app.Application;
import android.util.Log;

/**
 * Created by test on 2018/7/26.
 *
 * @author test
 */

public class TestApp extends Application{


    private static TestApp mApp;

    public static TestApp getInstance(){
        return mApp;
    }


    public static void TestLog(String msg) {
        Log.d("TestTime", msg);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }
}
