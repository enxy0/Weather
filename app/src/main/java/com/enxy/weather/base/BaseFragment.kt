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
        ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
}