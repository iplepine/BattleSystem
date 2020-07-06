package com.zs.mol.model.db.item

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item(
    @ColumnInfo(name = "userId") var userId: String,
    @ColumnInfo(name = "itemKey") val itemKey: String,
    @ColumnInfo(name = "amount") var amount: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
