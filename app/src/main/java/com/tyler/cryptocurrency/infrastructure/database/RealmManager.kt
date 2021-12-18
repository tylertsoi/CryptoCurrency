package com.tyler.cryptocurrency.infrastructure.database

import android.content.Context
import com.tyler.cryptocurrency.data.repositories.realm.model.RealmCurrencyInfo
import io.realm.Realm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream
import javax.inject.Inject

class RealmManager @Inject constructor(
    private val context: Context
) {
    lateinit var realm: Realm

    // Load sample data into realm database using assets file
    fun loadDataToRealm() {
        val inputStream: InputStream = context.assets.open("currency_list_sample.json")
        CoroutineScope(Dispatchers.IO).launch {
            realm = Realm.getDefaultInstance()
            realm.executeTransaction {
                it.createAllFromJson(RealmCurrencyInfo::class.java, inputStream)
            }
        }
    }
}