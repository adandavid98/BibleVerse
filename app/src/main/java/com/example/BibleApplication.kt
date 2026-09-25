package com.example

import android.app.Application
import android.util.Log
import com.example.sync.FirebaseSyncManager
import com.google.firebase.FirebaseApp

class BibleApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        try {
            if (FirebaseApp.getApps(this).isEmpty()) {
                val app = FirebaseApp.initializeApp(this)
                Log.d("BibleApplication", "Firebase inicializado manualmente: ${app?.name}")
            } else {
                Log.d("BibleApplication", "Firebase ya estaba inicializado")
            }
            FirebaseSyncManager.init(this)
            com.example.data.bible.BiblePericopesCatalog.init(this)
        } catch (e: Exception) {
            Log.e("BibleApplication", "Error al inicializar: ${e.message}", e)
        }
    }
}
