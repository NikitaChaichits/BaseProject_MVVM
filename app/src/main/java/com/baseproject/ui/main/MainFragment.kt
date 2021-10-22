package com.baseproject.ui.main

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.baseproject.R
import com.baseproject.common.base.BaseFragment
import com.baseproject.common.base.BaseViewModel
import com.baseproject.databinding.FragmentMainBinding

class MainFragment : BaseFragment(R.layout.fragment_main), View.OnClickListener {

    private val binding by viewBinding(FragmentMainBinding::bind)

    override val viewModel: BaseViewModel
        get() = TODO("Not yet implemented")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBottomNavGraph()
        setupButtons()
        observeViewModel()
    }

    override fun setupInsets() {
        TODO("Not yet implemented")
    }

    private fun setupBottomNavGraph() {
        binding.bottomNavGraph.setupWithNavController(findNavController())
    }

    private fun setupButtons() {
    }

    override fun onClick(view: View?) {
        when (view?.id) {
        }
    }

    override fun observeViewModel() {
        super.observeViewModel()

    }
}