package com.lz.alarmmanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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

        if (intent.getStringExtra("type").equals(MainActivity.REPEAT_TYPE)) {
            repeatAlarm();//大于 Android 4.4 再次调启alarm
        }
        //此处使用默认值，服务被系统杀死后。
        //系统可自动将其重启
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean stopService(Intent name) {
        Log.e(Tag, "stopService");
        return super.stopService(name);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(Tag, "onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void repeatAlarm() {
        Intent repeatIntent = new Intent(getApplicationContext(), AlarmService.class);
        repeatIntent.setAction("set A repeating Alarm");
        repeatIntent.putExtra("type", MainActivity.REPEAT_TYPE);
        PendingIntent alarmIntent = PendingIntent.getService(getApplicationContext(), 0, repeatIntent, 0);

        AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        final long triggerTime = System.currentTimeMillis() + MainActivity.INTERVAL;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//Android 6.0 中引入了低电耗模式和应用待机模式
            alarmMgr.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, alarmIntent);
        } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//Android 4.4，传递给此方法的触发时间被视为不精确
            alarmMgr.setExact(AlarmManager.RTC_WAKEUP, triggerTime, alarmIntent);
        }
    }
}
