package com.zs.mol.view.base

import android.app.Activity
import android.app.AppComponentFactory
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.zs.mol.MainActivity
import com.zs.mol.model.common.Logger
import com.zs.mol.view.ViewModelFactory
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

open class BaseFragment : Fragment(), BaseGameView {

    val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as MainActivity).component.inject(this)
    }

    protected fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    open fun addSubscribers() {
    }

    open fun clearSubscribers() {
        compositeDisposable.clear()
    }

    fun showToast(resourceId: Int) {
        context?.also {
            Toast.makeText(it, resourceId, Toast.LENGTH_SHORT).show()
        }
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

    override fun onError(error: Throwable) {
        showToast(error.toString())
    }
}