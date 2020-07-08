package com.zs.mol.model.db.item

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ItemDao {
    @Query("SELECT * FROM Item")
    fun getAll(): LiveData<List<Item>>

    @Query("SELECT * FROM Item WHERE userId = :userId AND itemKey = :itemKey")
    fun findItem(userId: String, itemKey: String): Item?

    @Query("SELECT * FROM Item WHERE userId = :userId AND itemKey = :itemKey")
    fun getItemLiveData(userId: String, itemKey: String): LiveData<Item?>

    @Insert
    fun insertItem(item: Item): Long

    @Update
    fun updateItem(vararg item: Item)
}