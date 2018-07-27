package cn.yunyunhei.legutestdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.io.File;
import java.io.InputStream;

/**
 * Created by test on 2018/7/26.
 *
 * @author test
 */

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }

    private void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    InputStream open = null;

                    open = getAssets().open("skin/skin.zip");

                    String targetFilePath = TestApp.getInstance().getFilesDir().getAbsolutePath() + "/1.zip";

                    File file = new File(targetFilePath);

                    FileUtils.writeBytesToFile(open, file);

                    open.close();

                    String dstPath = TestApp.getInstance().getFilesDir().getAbsolutePath() + "test/";

                    File dstFile = new File(dstPath);
                    if (!dstFile.exists()) {
                        dstFile.mkdirs();
                    }

                    TestApp.TestLog("start unzip");

                    ZipUtil.unZipResCustomDir(file, dstPath);

                    TestApp.TestLog("finish unzip");

                } catch (Exception e) {
                    e.printStackTrace();
                }


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }).start();
    }
}
