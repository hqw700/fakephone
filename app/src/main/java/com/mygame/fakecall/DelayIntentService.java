package com.mygame.fakecall;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telecom.TelecomManager;
import android.util.Log;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class DelayIntentService extends IntentService {

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.mygame.fakephone.action.FOO";
    private static final String ACTION_CALL = "com.mygame.fakephone.action.CALL";

    // TODO: Rename parameters
    private static final String EXTRA_NUM = "com.mygame.fakephone.extra.NUMBER";
    private static final String EXTRA_PARAM1 = "com.mygame.fakephone.extra.DELAY";
    private static final String EXTRA_PARAM2 = "com.mygame.fakephone.extra.REPEAT";
    private static final String TAG = "DelayIntentService";
    private Handler mHandler;
    private long mCurTime;

    public DelayIntentService() {
        super("DelayIntentService");
        mHandler = new Handler();
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, DelayIntentService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionCall(Context context, String num, int delay, int repeat) {
        Intent intent = new Intent(context, DelayIntentService.class);
        intent.setAction(ACTION_CALL);
        intent.putExtra(EXTRA_NUM, num);
        intent.putExtra(EXTRA_PARAM1, delay);
        intent.putExtra(EXTRA_PARAM2, repeat);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_CALL.equals(action)) {
                final String number = intent.getStringExtra(EXTRA_NUM);
                final int delay = intent.getIntExtra(EXTRA_PARAM1, 1);
                final int repeat = intent.getIntExtra(EXTRA_PARAM2, 1);
                Log.d(TAG, "onHandleIntent: delay " + delay + " S");
                mCurTime = System.currentTimeMillis();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "mHandler run: end " + (System.currentTimeMillis() - mCurTime));
                    }
                }, delay * 1000);
                handleActionCall(number, delay, repeat);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionCall(String number, int delay, int repeat) {
        int count = 0;
        for (int i = 0; i < delay; i++) {
            try {
                Thread.sleep(1000);
                count ++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "handleActionCall: " + count);
        }
        runCall(number);
        Log.d(TAG, "Thread.sleep run: end " + (System.currentTimeMillis() - mCurTime));
    }

    private void runCall(String number) {
        Bundle extras = new Bundle();
        extras.putParcelable(TelecomManager.EXTRA_INCOMING_CALL_ADDRESS, Uri.fromParts("tel", number, null));
        extras.putBoolean(CtsConnection.EXTRA_PLAY_CS_AUDIO, true);
        TelecomManager telecomManager = (TelecomManager) getSystemService(Context.TELECOM_SERVICE);
        if (telecomManager == null) {
            Log.w(TAG, "Step 2 fail - telecom service is null");
            return;
        }
        Log.i(TAG, "Step 2 pass - adding new incoming call");
        telecomManager.addNewIncomingCall(PhoneAccountUtils.TEST_PHONE_ACCOUNT_HANDLE, extras);
    }
}