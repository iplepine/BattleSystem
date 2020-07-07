package com.zs.mol.di.module

import android.content.Context
import androidx.room.Room
import com.zs.mol.model.db.AppDatabase
import com.zs.mol.model.db.item.ItemDao
import dagger.Module
import dagger.Provides

@Module
object DatabaseModule {
    @Provides
    @JvmStatic
    fun database(applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()
    }

    @Provides
    @JvmStatic
    fun itemDao(appDatabase: AppDatabase): ItemDao {
        return appDatabase.itemDao()
    }
}
