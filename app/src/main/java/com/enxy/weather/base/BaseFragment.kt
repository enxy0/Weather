package com.enxy.weather.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.main_activity.*

abstract class BaseFragment : Fragment() {
    abstract val layoutId: Int

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    fun notify(message: String) {
        Snackbar.make(requireActivity().mainContainer, message, Snackbar.LENGTH_LONG).show()
    }

    fun notify(message: Int) {
        Snackbar.make(requireActivity().mainContainer, message, Snackbar.LENGTH_LONG).show()
    }
}