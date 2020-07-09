package com.zs.mol.di.component

import com.zs.mol.di.scope.MainTabScope
import com.zs.mol.view.dungeon.DungeonTabFragment
import dagger.Subcomponent

@MainTabScope
@Subcomponent
interface DungeonTabComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): DungeonTabComponent
    }

    fun inject(tabFragment: DungeonTabFragment)
}
