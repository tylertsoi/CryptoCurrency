package com.tyler.cryptocurrency.application

import android.app.Application
import android.content.Context
import com.tyler.cryptocurrency.infrastructure.database.RealmManager
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application() {
    @Inject
    lateinit var realmManager: RealmManager
    private operator fun get(context: Context): MyApplication {
        return context.applicationContext as MyApplication
    }

    override fun onCreate() {
        super.onCreate()

        // init Realm DB
        Realm.init(applicationContext)
        val config = RealmConfiguration.Builder()
            .name("default-realm")
            .allowQueriesOnUiThread(false)
            .allowWritesOnUiThread(false)
            .compactOnLaunch()
            .build()
        Realm.setDefaultConfiguration(config)

        // Init Realm database data by assets example data
        realmManager.loadDataToRealm()
    }
}