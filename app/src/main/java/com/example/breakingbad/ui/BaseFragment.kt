package com.example.breakingbad.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.breakingbad.util.Utils


typealias inflate <T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding>(private val inflate: inflate<VB>) :
    Fragment() {

    private var loadingDialog: Dialog? = null
    private var _binding: VB? = null
    protected val binding get() = _binding!!





    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        start()
        onBackPressed()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    fun hideLoading(){
        loadingDialog?.let{if (it.isShowing)it.cancel()}
    }

    fun showLoading(){
        hideLoading()
        loadingDialog = Utils.showLoadingDialog(requireContext())

    }


    private fun onBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()

            }
        })
    }



    abstract fun start()

}