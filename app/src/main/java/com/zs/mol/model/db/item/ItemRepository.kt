package com.zs.mol.model.db.item

import com.google.firebase.firestore.auth.User
import com.zs.mol.di.scope.AfterLogin
import javax.inject.Inject

@AfterLogin
class ItemRepository @Inject constructor(user: User)