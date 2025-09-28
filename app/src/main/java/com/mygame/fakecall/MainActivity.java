package com.mygame.fakecall;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.PhoneAccount;
import android.telecom.TelecomManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
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
            PhoneAccount account = PhoneAccountUtils.getPhoneAccount(this);
            if (account == null || !account.isEnabled()) {
                Toast.makeText(this, "Please register a PhoneAccount first.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TelecomManager.ACTION_CHANGE_PHONE_ACCOUNTS);
                startActivity(intent);
            } else {
                DelayIntentService.startActionCall(this, mName.getText().toString(),
                        Integer.parseInt(mDelay.getText().toString()), 4);
            }
        });

        requestPermissions(new String[]{Manifest.permission.READ_PHONE_NUMBERS}, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}