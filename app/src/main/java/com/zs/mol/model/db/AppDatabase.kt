package com.zs.mol.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zs.mol.model.db.item.ItemDao
import com.zs.mol.model.db.item.ItemInfo

@Database(entities = [ItemInfo::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}