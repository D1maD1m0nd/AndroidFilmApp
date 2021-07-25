package com.example.filmapp.utils


import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.filmapp.R


fun Fragment.showFragment(compatActivity: AppCompatActivity) = this.apply {
    compatActivity
        .supportFragmentManager.beginTransaction().replace(R.id.container, this)
        .commitAllowingStateLoss()
}

fun Fragment.showFragment(fragment: Fragment) = this.apply {
    activity?.supportFragmentManager?.beginTransaction()?.add(R.id.container, fragment)
        ?.addToBackStack(null)?.commitAllowingStateLoss()
}

