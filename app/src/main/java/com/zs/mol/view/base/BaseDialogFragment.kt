package com.zs.mol.view.base

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.zs.mol.MainActivity
import com.zs.mol.model.common.Logger
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

open class BaseDialogFragment : DialogFragment(), BaseGameView {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

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

    fun showToast(text: String) {
        context?.also {
            Toast.makeText(it, text, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onError(error: Throwable) {
        showToast(error.toString())
        dismiss()
    }
}