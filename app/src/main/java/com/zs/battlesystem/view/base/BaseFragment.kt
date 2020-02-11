package com.zs.battlesystem.view.base

import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseFragment : Fragment() {

    val compositeDisposable = CompositeDisposable()

    protected fun addFragment(fragment: Fragment) {
        (activity as? BaseActivity)?.addFragment(fragment)
    }

    protected fun replaceFragment(fragment: Fragment) {
        (activity as? BaseActivity)?.replaceFragment(fragment)
    }

    protected fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    open fun addSubscribers() {
    }

    open fun clearSubscribers() {
        compositeDisposable.clear()
    }

    override fun onResume() {
        super.onResume()
        addSubscribers()
    }

    override fun onPause() {
        super.onPause()
        clearSubscribers()
    }

    open fun onBackPressed(): Boolean {
        return false
    }
}