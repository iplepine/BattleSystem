package com.zs.mol.model.db.user

import com.zs.mol.di.scope.GameScope
import com.zs.mol.model.common.Logger
import com.zs.mol.model.db.item.ItemRepository
import com.zs.mol.model.item.ItemKey
import com.zs.mol.model.quest.QuestRepository
import com.zs.mol.model.user.User
import com.zs.mol.model.user.UserData
import javax.inject.Inject

@GameScope
class UserRepository @Inject constructor(
    private val local: UserLocalSource,
    private val remote: UserRemoteSource,
    private val itemRepository: ItemRepository,
    private val questRepository: QuestRepository
) {
    fun getUser(userId: String): User {
        val userData = local.load(userId)
        return if (userData == null) {
            Logger.d("user data is null")
            User(userId, UserData(), itemRepository, questRepository).apply {
                addItem(ItemKey.GOLD, 5000)
                    .subscribe()
            }
        } else {
            User(userId, userData, itemRepository, questRepository)
        }
    }

    fun saveUser(user: User) {
        local.save(user)
    }
}
