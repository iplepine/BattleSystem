package com.zs.mol

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import com.zs.mol.view.base.BaseActivity
import com.zs.mol.widget.WidgetUpdateService

class WidgetConfigure : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootView = FrameLayout(applicationContext)
        setContentView(rootView)

        WidgetUpdateService.enqueueWork(
            applicationContext, Intent(
                applicationContext,
                WidgetUpdateService::class.java
            )
        )

        finish()
    }
}
