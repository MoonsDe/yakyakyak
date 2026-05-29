package com.example.yakyakyak.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.yakyakyak.data.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_BOOT_COMPLETED) return
        CoroutineScope(Dispatchers.IO).launch {
            val medications = AppDatabase.getDatabase(context)
                .medicationDao()
                .getAllActiveMedicationsSync()
            medications.forEach { AlarmScheduler.scheduleAll(context, it) }
        }
    }
}
