package com.zs.mol.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zs.mol.model.db.item.Item
import com.zs.mol.model.db.item.ItemDao

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}