package com.mygame.fakephone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class PhoneStateReceiver extends BroadcastReceiver {

    private static final String TAG = "huangqw";

    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        Toast.makeText(context, state, Toast.LENGTH_SHORT).show();

        String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
        if (!TextUtils.isEmpty(number)) {
            Log.d(TAG, "PhoneStateReceiver onReceive extraIncomingNumber: " + number);
        }
    }
}
