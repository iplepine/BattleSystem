package com.zs.mol.model.db.item

import com.zs.mol.di.scope.GameScope
import com.zs.mol.model.common.DefaultLiveData
import com.zs.mol.model.common.Logger
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@GameScope
class ItemRepository @Inject constructor(private val itemDao: ItemDao) {

    fun addItem(item: Item): Single<Boolean> {
        return addItem(item.userId, item.itemKey, item.amount)
    }

    fun addItem(userId: String, id: String, amount: Long): Single<Boolean> {
        return Single.create<Boolean> {
            Logger.d("add item to inventory, itemId: $id, amount : $amount")
            val item = itemDao.findItem(userId, id)
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

    private val liveDataCache = HashMap<String, DefaultLiveData<Item>>()

    // 이거 처리를 트랜잭션으로 한번에 처리해야함... removeAllItem 같은걸로
    fun removeItem(userId: String, id: String, amount: Long): Single<Boolean> {
        return Single.create<Boolean> {
            Logger.d("remove item from inventory, itemId: $id, amount : $amount")

            val item = itemDao.findItem(userId, id)
            if (item == null) {
                it.onError(Throwable("not enough items, itemId : $id"))
                return@create
            } else {
                val newAmount = (item.amount - amount).coerceAtLeast(0)
                item.amount = newAmount
                itemDao.updateItem(item)

                updateLiveData(id, item)

                Logger.d("remove Inventory, itemId: $id, amount : ${getAmount(userId, id)}")
                it.onSuccess(true)
            }
        }.subscribeOn(Schedulers.io())
    }

    fun getAmount(userId: String, id: String): Long {
        return itemDao.findItem(userId, id)?.amount ?: 0
    }

    fun getItemLiveData(userId: String, id: String): DefaultLiveData<Item> {
        return liveDataCache[id] ?: let {
            val item = itemDao.findItem(userId, id) ?: Item(userId, id, 0).also {
                itemDao.insertItem(it)
            }
            DefaultLiveData(item).apply {
                liveDataCache[id] = this
            }
        }
    }

    private fun updateLiveData(id: String, item: Item) {
        liveDataCache[id]?.postValue(item)
    }
}