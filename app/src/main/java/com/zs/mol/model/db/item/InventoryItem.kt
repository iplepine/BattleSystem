package com.zs.mol.model.db.item

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class InventoryItem(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "userId") val userId: String,
    @ColumnInfo(name = "itemKey") val itemKey: String,
    @ColumnInfo(name = "amount") val amount: Long
)
