package com.zs.battlesystem.view.base

import androidx.fragment.app.Fragment
import com.zs.battlesystem.model.common.Logger
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseFragment : Fragment() {

    val compositeDisposable = CompositeDisposable()

    override fun onResume() {
        super.onResume()
        addSubscribers()
        Logger.d(javaClass.simpleName + " onResume")
    }

    override fun onPause() {
        super.onPause()
        clearSubscribers()
        Logger.d(javaClass.simpleName + " onPause")
    }

    protected fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    open fun addSubscribers() {
    }

    open fun clearSubscribers() {
        compositeDisposable.clear()
    }
}