package com.zs.mol.model.db.item

import androidx.room.*

@Dao
interface ItemDao {
    @Query("SELECT * FROM ItemInfo")
    fun getAll(): List<ItemInfo>

    @Query("SELECT * FROM ItemInfo WHERE id = :id")
    fun findItem(id: Int): ItemInfo

    @Query("SELECT * FROM InventoryItem WHERE userId = :userId & itemKey = :itemKey")
    fun getInventoryItem(userId: String, itemKey: String): InventoryItem

    @Update
    fun updateInventoryItem(item: InventoryItem): Long

    @Insert
    fun insertAll(vararg item: ItemInfo)

    @Delete
    fun delete(user: ItemInfo)
}