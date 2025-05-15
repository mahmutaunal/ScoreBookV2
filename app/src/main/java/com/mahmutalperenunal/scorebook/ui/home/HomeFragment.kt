package com.mahmutalperenunal.scorebook.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mahmutalperenunal.scorebook.R
import com.mahmutalperenunal.scorebook.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupClicks()
    }

    private fun setupClicks() {
        binding.btnNewGame.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeToSetup(""))
        }

        binding.btnViewHistory.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeToHistory())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}