package com.xiaojiang.hotfix;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.xiaojiang.hotfix.fixutils.FixUtil;
import com.xiaojiang.hotfix.test.TestError;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    public static final String DEX_DIR = "odex";
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void test(View v) {
        TestError testError = new TestError();
        Toast.makeText(MainActivity.this, testError.test(MainActivity.this), Toast.LENGTH_LONG).show();
    }

    public void hotFix(View v) {
        //do hot fix
        fixBug();
    }

    private void fixBug() {
        //模拟下在服务端下载文件
        File fileDir = getDir(DEX_DIR, Context.MODE_PRIVATE);
        String name = "classes2.dex";
        String filePath = fileDir.getAbsolutePath() + File.separator + name;
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            is = new FileInputStream(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + name);
            fos = new FileOutputStream(filePath);
            int len = 0;
            byte[] buffer = new byte[2048];
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            Log.d(TAG, "dex file wrote to private file");
            FixUtil.loadFixedDex(MainActivity.this);
            try{
                android.os.Process.killProcess(android.os.Process.myPid());
            }catch(Throwable t){
                t.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
                try {
                    if(is != null)
                        is.close();
                    if(fos != null)
                        fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

}
