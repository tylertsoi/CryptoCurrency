package com.tyler.cryptocurrency.data.repo.realm

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.tyler.cryptocurrency.data.repositories.realm.RealmCurrencyListRepo
import com.tyler.cryptocurrency.data.repositories.realm.model.RealmCurrencyInfo
import com.tyler.cryptocurrency.domain.interfaces.repositories.CurrencyListRepo
import com.tyler.cryptocurrency.infrastructure.database.RealmManager
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RealmCurrencyInfoTest {

    private lateinit var realm: Realm

    private lateinit var currencyListRepo: CurrencyListRepo

    @Before
    fun setUp() = runBlocking {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        Realm.init(appContext)
        val config = RealmConfiguration.Builder()
            .name("test-realm")
            .allowQueriesOnUiThread(false)
            .allowWritesOnUiThread(false)
            .compactOnLaunch()
            .build()

        realm = Realm.getInstance(config)

        //Load data
        RealmManager(appContext).loadDataToRealm()

        currencyListRepo = RealmCurrencyListRepo()
    }

    @After
    fun tearDown() {

    }

    @Test
    fun getCurrencyNotEmpty() = runBlocking {
        val result = currencyListRepo.getCurrencyList()
        Assert.assertNotEquals(0, result.size)
    }


    private suspend fun insertMockData() {
        realm.executeTransactionAwait { r: Realm ->
            val cities = listOf(
                RealmCurrencyInfo(
                    "BTC",
                    "Bitcoin",
                    "BTC"
                ), RealmCurrencyInfo(
                    "ETH",
                    "Ethereum",
                    "ETH"
                )
            )
            r.insertOrUpdate(cities)
        }
    }
}