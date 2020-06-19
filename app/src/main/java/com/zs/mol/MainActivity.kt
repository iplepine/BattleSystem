package com.zs.mol

import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.zs.mol.di.component.MainActivityComponent
import com.zs.mol.model.game.GameEngine
import com.zs.mol.view.base.BaseActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    lateinit var component: MainActivityComponent

    @Inject
    lateinit var engine: GameEngine

    var timeDisposable: Disposable? = null
    var time = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        component = (applicationContext as MolApp).component.mainActivityComponent()
            .create()
            .apply {
                inject(this@MainActivity)
            }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handleIntent()
        initNavigation()
        initViews()
    }

    override fun onResume() {
        super.onResume()
        engine.onResume()

        timeDisposable = engine.timeSubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                time += it
            }
    }

    override fun onPause() {
        super.onPause()
        engine.onPause()

        timeDisposable?.dispose()
    }

    private fun handleIntent() {
        if (intent == null) {
            Log.e("인텐트", "널널")
        } else {
            Log.e("인텐트", "노널널")
        }
    }

    private fun initViews() {
    }

    private fun initNavigation() {
        bottomNavigationView.setupWithNavController(findNavController(R.id.navHostFragment))
    }
}
