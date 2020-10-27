package com.dkhai.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.util.Log;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "dkhai";
    // Used to load the 'native-lib' library on application startup.
    private static boolean loaded;
    static {
        try {
            System.loadLibrary("native_dkhai");
            loaded = true;
            Log.v(TAG, "successfully loaded native_dkhai");
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "failed to load native_dkhai");
            loaded = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView  tv = new TextView(this);
        tv.setText( stringFromJNI() );
        setContentView(tv);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}