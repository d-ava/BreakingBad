package com.example.breakingbad.ui.language

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.breakingbad.databinding.FragmentLanguageChangeDialogBinding
import com.example.breakingbad.util.lang
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LanguageChangeDialogFragment:BottomSheetDialogFragment() {

    private var _binding:FragmentLanguageChangeDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLanguageChangeDialogBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    private fun setListeners(){
        binding.apply {
            ukFlag.setOnClickListener {
                lang=""
                activity?.recreate()
                findNavController().popBackStack()

            }
            geoFlag.setOnClickListener {
                lang="ka"
                activity?.recreate()
                findNavController().popBackStack()
            }

        }
    }


}

