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
    fun getItem(userId: String, itemKey: String): LiveData<Item?>

    @Query("SELECT * FROM Item WHERE userId = :userId AND itemKey = :itemKey")
    fun findItem(userId: String, itemKey: String): Item

    @Insert
    fun insertItem(item: Item): Long

    @Update
    fun updateItem(item: Item)
}