package com.zs.mol.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zs.mol.model.db.item.InventoryItem
import com.zs.mol.model.db.item.ItemDao
import com.zs.mol.model.db.item.ItemInfo

@Database(entities = [InventoryItem::class, ItemInfo::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}