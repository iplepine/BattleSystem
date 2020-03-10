package com.zs.mol.view.base

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.zs.mol.model.common.Logger
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

    fun showToast(text: String) {
        context?.also {
            Toast.makeText(it, text, Toast.LENGTH_SHORT).show()
        }
    }

    fun showSoftKey(view: View) {
        activity?.apply {
            view.post {
                val inputMethodManager =
                    getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.showSoftInput(view, 0)
            }
        }
    }

    fun hideSoftKey() {
        activity?.apply {
            val inputMethodManager =
                getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }
    }
}