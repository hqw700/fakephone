package com.mygame.fakephone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.PhoneAccount;
import android.telecom.TelecomManager;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "huangqw";
    private Button mRegister;
    private Button mDial;
    private EditText mName, mDelay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRegister = findViewById(R.id.register);
        mDial = findViewById(R.id.dial);
        mName = findViewById(R.id.name);
        mDelay = findViewById(R.id.delay);
        mRegister.setOnClickListener(v -> {
                PhoneAccountUtils.registerTestPhoneAccount(this);
                PhoneAccount account = PhoneAccountUtils.getPhoneAccount(this);
                if (account != null) {
                    // Open the phone accounts screen to have the user set CtsConnectionService as
                    // the default.
                    Intent intent = new Intent(TelecomManager.ACTION_CHANGE_PHONE_ACCOUNTS);
                    startActivity(intent);
                }
        });

        mDial.setOnClickListener(v -> {
            DelayIntentService.startActionCall(this, mName.getText().toString(),
                    Integer.parseInt(mDelay.getText().toString()), 4);
        });
    }
}