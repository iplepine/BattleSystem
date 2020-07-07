package com.zs.mol.model.db.item

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zs.mol.model.db.AppDatabase
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.random.Random

@RunWith(AndroidJUnit4::class)
class ItemTest {
    private lateinit var itemDao: ItemDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries()
            .build()
        itemDao = db.itemDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertTest() {
        val item = Item("userId", "itemId", 3)
        val id = itemDao.insertItem(item)
        val dbItem = itemDao.findItem("userId", "itemId")
        println("db item : $dbItem")

        Assert.assertEquals(item.amount, dbItem?.amount)
    }

    @Test
    fun updateTest() {
        itemDao.insertItem(Item("userId", "itemId", 3))
        val dbItem = itemDao.findItem("userId", "itemId")!!
        dbItem.amount = 5
        itemDao.updateItem(dbItem)
        val updatedItem = itemDao.findItem("userId", "itemId")!!
        println("db item : $updatedItem")

        Assert.assertEquals(5, updatedItem?.amount)
    }


    @Test
    fun rxZipTest() {
        println("================ test start ================")
        var a = Single.create<Boolean> {
            println("Aaaaaaaaaaaa")
            it.onSuccess(true)
        }
        var b = Single.create<Boolean> {
            println("BBBBBBBBBBB")
            it.onSuccess(true)
        }

        val arr = arrayOf(1, 2, 3, 4, 5)

        arr.forEach {
            a = a.zipWith(
                createSingle((it * 111111).toString()),
                BiFunction<Boolean, Boolean, Boolean> { t1, t2 -> t1 && t2 })
        }

        Single.defer {
            if (Random.nextBoolean()) {
                a
            } else {
                b
            }
        }.subscribe({ println("cccc") }, {})

        println("================ test finish ================")
    }

    fun createSingle(text: String): Single<Boolean> {
        return Single.create<Boolean> {
            println(text)
            it.onSuccess(true)
        }
    }
}