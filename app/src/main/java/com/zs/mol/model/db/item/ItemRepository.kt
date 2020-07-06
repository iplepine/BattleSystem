package com.zs.mol.model.db.item

import androidx.lifecycle.LiveData
import com.zs.mol.di.scope.GameScope
import com.zs.mol.model.common.DefaultLiveData
import com.zs.mol.model.common.Logger
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@GameScope
class ItemRepository @Inject constructor(private val itemDao: ItemDao) {

    private val liveDataCache = HashMap<String, DefaultLiveData<Long>>()

    fun addItem(userId: String, id: String, amount: Long): Single<Boolean> {
        return Single.create<Boolean> {
            Logger.d("add item to inventory, itemId: $id, amount : $amount")
            val item = itemDao.getItem(userId, id)?.value
            var newAmount = amount

            if (item == null) {
                itemDao.insertItem(Item(userId, id, amount))
            } else {
                newAmount += item.amount
                item.amount = newAmount
                itemDao.updateItem(item)
            }

            Logger.d("current Inventory, itemId: $id, amount : ${getAmount(userId, id)}")

            it.onSuccess(true)
        }.subscribeOn(Schedulers.io())
    }

    fun removeItem(userId: String, id: String, amount: Long): Single<Boolean> {
        return Single.create<Boolean> {
            Logger.d("remove item from inventory, itemId: $id, amount : $amount")

            val item = itemDao.getItem(userId, id)?.value
            if (item == null) {
                it.onError(Throwable("not enough items, itemId : $id"))
                return@create
            } else {
                val newAmount = (item.amount - amount).coerceAtLeast(0)
                item.amount = newAmount
                itemDao.updateItem(item)

                updateLiveData(id, newAmount)

                Logger.d("remove Inventory, itemId: $id, amount : ${getAmount(userId, id)}")
                it.onSuccess(true)
            }
        }.subscribeOn(Schedulers.io())
    }

    fun getAmount(userId: String, id: String): Long {
        return itemDao.getItem(userId, id)?.value?.amount ?: 0
    }

    fun getItemLiveData(userId: String, id: String): LiveData<Item?> {
        return itemDao.getItem(userId, id)
    }

    private fun updateLiveData(id: String, amount: Long) {
        liveDataCache[id]?.value = amount
    }
}