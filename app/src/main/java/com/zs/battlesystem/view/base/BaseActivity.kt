package com.zs.battlesystem.view.base

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.zs.battlesystem.R
import com.zs.battlesystem.model.common.Logger

open class BaseActivity : AppCompatActivity(), FragmentManager.OnBackStackChangedListener {
    var currentFragment: Fragment? = null

    fun replaceFragment(fragment: Fragment) {
        currentFragment?.onPause()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

        currentFragment = fragment
    }

    fun addFragment(fragment: Fragment) {
        currentFragment?.onPause()

        supportFragmentManager
            .beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .add(R.id.content, fragment)
            .addToBackStack(fragment.toString())
            .commit()

        currentFragment = fragment
    }

    override fun onBackStackChanged() {
        Logger.d(currentFragment.toString())
        currentFragment?.onPause()
        currentFragment = supportFragmentManager.fragments.last()
        currentFragment?.onResume()
        Logger.d(currentFragment.toString())
    }

    override fun onBackPressed() {
        if ((currentFragment as? BaseFragment)?.onBackPressed() == true) {
            return
        }

        Logger.d("fragment count : ${supportFragmentManager.fragments.size}")

        if (supportFragmentManager.fragments.size > 1) {
            supportFragmentManager.popBackStack()
            return
        }

        super.onBackPressed()
    }
}