package com.example.yakyakyak.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.yakyakyak.data.Medication
import java.util.Calendar

object AlarmScheduler {

    fun scheduleAll(context: Context, medication: Medication) {
        if (!medication.isActive) return
        medication.getTimeList().forEachIndexed { index, time ->
            scheduleOne(context, medication.id, medication.name, time, index)
        }
    }

    private fun scheduleOne(context: Context, medId: Int, medName: String, time: String, index: Int) {
        val parts = time.split(":")
        val hour = parts[0].toIntOrNull() ?: return
        val minute = parts[1].toIntOrNull() ?: return

        val now = Calendar.getInstance()
        val target = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (timeInMillis <= now.timeInMillis) add(Calendar.DAY_OF_MONTH, 1)
        }

        val intent = Intent(context, MedicationAlarmReceiver::class.java).apply {
            putExtra(MedicationAlarmReceiver.EXTRA_MED_ID, medId)
            putExtra(MedicationAlarmReceiver.EXTRA_MED_NAME, medName)
            putExtra(MedicationAlarmReceiver.EXTRA_TIME, time)
            putExtra(MedicationAlarmReceiver.EXTRA_INDEX, index)
        }
        val pi = PendingIntent.getBroadcast(
            context, requestCode(medId, index), intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !am.canScheduleExactAlarms()) {
            am.set(AlarmManager.RTC_WAKEUP, target.timeInMillis, pi)
        } else {
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, target.timeInMillis, pi)
        }
    }

    fun cancelAll(context: Context, medication: Medication) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        medication.getTimeList().forEachIndexed { index, _ ->
            val intent = Intent(context, MedicationAlarmReceiver::class.java)
            val pi = PendingIntent.getBroadcast(
                context, requestCode(medication.id, index), intent,
                PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
            ) ?: return@forEachIndexed
            am.cancel(pi)
        }
    }

    fun rescheduleNext(context: Context, medId: Int, medName: String, time: String, index: Int) {
        scheduleOne(context, medId, medName, time, index)
    }

    // 식사 완료 후 X분 뒤 한 번만 울리는 알람
    fun scheduleMealDelayed(context: Context, medId: Int, medName: String, mealTiming: String) {
        val delayMinutes = when (mealTiming) {
            "식후 30분" -> 30
            "식후 1시간" -> 60
            else -> return
        }
        val triggerMs = System.currentTimeMillis() + delayMinutes * 60 * 1000L

        val intent = Intent(context, MedicationAlarmReceiver::class.java).apply {
            putExtra(MedicationAlarmReceiver.EXTRA_MED_ID, medId)
            putExtra(MedicationAlarmReceiver.EXTRA_MED_NAME, medName)
            putExtra(MedicationAlarmReceiver.EXTRA_IS_MEAL_ALARM, true)
            putExtra(MedicationAlarmReceiver.EXTRA_TIME, mealTiming)
            putExtra(MedicationAlarmReceiver.EXTRA_INDEX, MEAL_ALARM_INDEX)
        }
        val pi = PendingIntent.getBroadcast(
            context, requestCode(medId, MEAL_ALARM_INDEX), intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !am.canScheduleExactAlarms()) {
            am.set(AlarmManager.RTC_WAKEUP, triggerMs, pi)
        } else {
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerMs, pi)
        }
    }

    private fun requestCode(medId: Int, index: Int) = medId * 1000 + index

    private const val MEAL_ALARM_INDEX = 500
}
