package com.lz.alarmmanager;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    /**
     * Alarms do not fire when the device is idle in Doze mode. Any scheduled alarms will be deferred until the device exits Doze.
     * If you need to ensure that your work completes even when the device is idle there are several options available. You can
     * use setAndAllowWhileIdle() or setExactAndAllowWhileIdle() to guarantee that the alarms will execute. Another option is to
     * use the new WorkManager API, which is built to perform background work either once or periodically. For more information,
     * see Schedule tasks with WorkManager.
     * <p>
     * 当设备在打盹模式下处于空闲状态时，警报不会触发。---  任何预定的警报将推迟到设备退出Doze。
     * 如果您需要确保即使设备处于空闲状态也能完成工作，可以使用多种选项。
     * 您可以使用setAndAllowWhileIdle（）或setExactAndAllowWhileIdle（）来保证警报将执行。
     * 另一种选择是使用新的WorkManager API，它可以一次或定期执行后台工作。
     * 有关更多信息，请参阅使用WorkManager计划任务。
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * set One Shot Alarm
     *
     * @param view
     */
    public void onSetOneShotAlarmClick(View view) {
        Intent pendingIntent = new Intent(getApplicationContext(), AlarmService.class);
        pendingIntent.setAction("set One Shot Alarm");
        PendingIntent alarmPendingIntent = PendingIntent.getService(getApplicationContext(), 0, pendingIntent, 0);
        AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        final long triggerAtMillis = System.currentTimeMillis() + 3 * 1000;
        alarmMgr.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, alarmPendingIntent);
    }

    /**
     * As described above, repeating alarms are a good choice for scheduling regular events or data lookups. A repeating alarm has the following characteristics:
     * <p>
     * A alarm type. For more discussion, see Choose an alarm type.
     * A trigger time. If the trigger time you specify is in the past, the alarm triggers immediately.
     * 触发时间。如果您指定的触发时间是过去的，则会立即触发警报
     * The alarm's interval. For example, once a day, every hour, every 5 minutes, and so on.
     * A pending intent that fires when the alarm is triggered. When you set a second alarm that uses the same pending intent, it replaces the original alarm.
     *
     * @param view
     */
    public void onSetArepeatingAlarmClick(View view) {
        Intent intent = new Intent(getApplicationContext(), AlarmService.class);
        intent.setAction("set A repeating Alarm");
        PendingIntent alarmIntent = PendingIntent.getService(getApplicationContext(), 0, intent, 0);
        // Hopefully your alarm will have a lower frequency than this!
        /*AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_HALF_HOUR,
                AlarmManager.INTERVAL_HALF_HOUR, alarmIntent);*/

        AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        final long triggerTime = System.currentTimeMillis() + 3 * 1000;
        final long interval = 2 * 60 * 1000;
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, triggerTime, interval, alarmIntent);
        //执行时间不固定
        /*alarmMgr.setInexactRepeating();*/

        //选择一种alarm类型
        /*AlarmManager.ELAPSED_REALTIME 相对于系统启动时间，不唤醒。
        AlarmManager.ELAPSED_REALTIME_WAKEUP 相对于系统启动时间，唤醒
        AlarmManager.RTC 相对于当前时间，不唤醒。
        AlarmManager.RTC_WAKEUP 相对于当前时间，唤醒。*/
    }

    public void onCancelRepeatAlarm(View view) {
        Intent intent = new Intent(getApplicationContext(), AlarmService.class);
        /*取消alarm使用AlarmManager.cancel()函数，传入参数是个PendingIntent实例。
        该函数会将所有跟这个PendingIntent相同的Alarm全部取消，
        怎么判断两者是否相同，android使用的是intent.filterEquals()，
        具体就是判断两个PendingIntent的action、data、type、class和category是否完全相同。*/
        //intent.setAction("cancel the repeating Alarm");
        intent.setAction("set A repeating Alarm");
        PendingIntent alarmIntent = PendingIntent.getService(this, 0, intent, 0);
        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarm.cancel(alarmIntent);
    }
}
