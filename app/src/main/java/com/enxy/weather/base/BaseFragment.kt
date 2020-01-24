package com.enxy.weather.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.enxy.weather.AndroidApplication
import com.enxy.weather.di.ApplicationComponent
import com.enxy.weather.ui.main.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.main_activity.*

abstract class BaseFragment : Fragment() {
    abstract val layoutId: Int

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as AndroidApplication).appComponent
    }

    fun getMainViewModel(viewModelFactory: ViewModelProvider.Factory) =
        ViewModelProviders.of(this.activity!!, viewModelFactory).get(MainViewModel::class.java)

    fun notify(message: String) {
        Snackbar.make(activity!!.mainContainer, message, Snackbar.LENGTH_LONG).show()
    }

    fun notify(message: Int) {
        Snackbar.make(activity!!.mainContainer, message, Snackbar.LENGTH_LONG).show()
    }
}