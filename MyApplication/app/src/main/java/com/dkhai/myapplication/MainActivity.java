package com.dkhai.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "dkhai";

    private EditText et_user;
    private EditText et_pwd;
    private Button bt_login;

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

        setContentView(R.layout.activity_main);
        addListenerOnButton();
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native int callLogin(String username, String pwd);

    public void addListenerOnButton() {
        et_user = (EditText) this.findViewById(R.id.et_username);
        et_pwd = (EditText) this.findViewById(R.id.et_password);
        bt_login = (Button) this.findViewById(R.id.bt_login);

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_user.getText().toString();
                String password = et_pwd.getText().toString();


                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter user and password", Toast.LENGTH_LONG).show();
                    return;
                }
                int resultCode = callLogin(username, password);


                if (resultCode == 200) {
                    Toast.makeText(getApplicationContext(), "Right", Toast.LENGTH_LONG).show();
                }
                if (resultCode == 404) {
                    Toast.makeText(getApplicationContext(), "Wrong", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}