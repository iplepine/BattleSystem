package com.zs.mol.view.base

import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.zs.mol.model.common.Logger
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseDialogFragment : DialogFragment() {

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

    fun showToast(text: String) {
        context?.also {
            Toast.makeText(it, text, Toast.LENGTH_SHORT).show()
        }
    }
}