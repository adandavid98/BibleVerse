package com.example.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.service.NotificationHelper
import java.util.Calendar

class DailyVerseReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val prefs = context.getSharedPreferences(NotificationHelper.PREFS_NAME, Context.MODE_PRIVATE)
        val isEnabled = prefs.getBoolean(NotificationHelper.KEY_REMINDER_ENABLED, true)
        val hour = prefs.getInt(NotificationHelper.KEY_REMINDER_HOUR, 8)
        val minute = prefs.getInt(NotificationHelper.KEY_REMINDER_MINUTE, 0)

        if (!isEnabled) return

        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            // Reprogram reminder on system reboot without showing notification immediately
            NotificationHelper.scheduleDailyReminder(context, hour, minute)
        } else {
            // Alarm trigger: show notification and reschedule for the next day
            val verse = NotificationHelper.getVerseOfTheDay()
            NotificationHelper.showVerseNotification(
                context = context,
                reference = verse.reference,
                text = verse.text,
                reflection = verse.context
            )
            NotificationHelper.scheduleDailyReminder(context, hour, minute)
        }
    }
}
