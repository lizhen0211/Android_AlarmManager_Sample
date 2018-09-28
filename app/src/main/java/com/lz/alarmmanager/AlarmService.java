package com.lz.alarmmanager;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by lz on 2018/9/28.
 */
public class AlarmService extends Service {

    private static String Tag = AlarmService.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(Tag, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(Tag, "onStartCommand " + intent.getAction());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
