package com.example.yakyakyak.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.yakyakyak.R
import com.example.yakyakyak.ui.MainActivity

class MedicationAlarmReceiver : BroadcastReceiver() {

    companion object {
        const val EXTRA_MED_ID = "med_id"
        const val EXTRA_MED_NAME = "med_name"
        const val EXTRA_TIME = "scheduled_time"
        const val EXTRA_INDEX = "time_index"
        const val EXTRA_IS_MEAL_ALARM = "is_meal_alarm"
        const val CHANNEL_ID = "medication_alarm"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val medId = intent.getIntExtra(EXTRA_MED_ID, -1)
        val medName = intent.getStringExtra(EXTRA_MED_NAME) ?: "약"
        val time = intent.getStringExtra(EXTRA_TIME) ?: ""
        val index = intent.getIntExtra(EXTRA_INDEX, 0)
        val isMealAlarm = intent.getBooleanExtra(EXTRA_IS_MEAL_ALARM, false)

        createChannel(context)
        if (isMealAlarm) {
            showMealNotification(context, medId, medName, time)
            // 식사 후 지연 알람은 한 번만 울리므로 재스케줄 없음
        } else {
            showNotification(context, medId, medName, time)
            AlarmScheduler.rescheduleNext(context, medId, medName, time, index)
        }
    }

    private fun createChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID, "복용 알림", NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "약 복용 시간 알림"
                enableVibration(true)
            }
            context.getSystemService(NotificationManager::class.java)
                .createNotificationChannel(channel)
        }
    }

    private fun showMealNotification(context: Context, medId: Int, medName: String, mealTiming: String) {
        val launchIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pi = PendingIntent.getActivity(
            context, medId + 10000, launchIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("🍽️ 지금 약 드세요!")
            .setContentText("$medName — $mealTiming 복용 시간입니다")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pi)
            .build()

        try {
            NotificationManagerCompat.from(context).notify(medId + 10000, notification)
        } catch (_: SecurityException) {}
    }

    private fun showNotification(context: Context, medId: Int, medName: String, time: String) {
        val launchIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pi = PendingIntent.getActivity(
            context, medId, launchIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("💊 복용 시간이에요!")
            .setContentText("$medName — $time 복용 시간입니다")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pi)
            .build()

        try {
            NotificationManagerCompat.from(context).notify(medId, notification)
        } catch (_: SecurityException) {}
    }
}
