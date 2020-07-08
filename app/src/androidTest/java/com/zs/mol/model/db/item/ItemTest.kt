package com.zs.mol.model.db.item

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zs.mol.model.db.AppDatabase
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ItemTest {
    private lateinit var itemDao: ItemDao
    private lateinit var db: AppDatabase

    @JvmField
    @Rule
    val rule = InstantTaskExecutorRule()

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
    fun getItemLiveDataTest() {
        itemDao.insertItem(Item("userId", "itemId", 3))
        val dbItem = itemDao.findItem("userId", "itemId") ?: return

        val liveData = itemDao.getItemLiveData("userId", "itemId")
        liveData.observeForever {
            println(it?.amount)
        }
        dbItem.amount = 5
        itemDao.updateItem(dbItem)

        dbItem.amount = 7
        itemDao.updateItem(dbItem)

        Thread.sleep(2000)
    }
}