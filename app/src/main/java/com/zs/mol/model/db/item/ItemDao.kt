package com.zs.mol.model.db.item

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ItemDao {
    @Query("SELECT * FROM ItemInfo")
    fun getAll(): List<ItemInfo>

    @Query("SELECT * FROM ItemInfo WHERE id = :id")
    fun findItem(id: Int): ItemInfo

    @Insert
    fun insertAll(vararg item: ItemInfo)

    @Delete
    fun delete(user: ItemInfo)
}