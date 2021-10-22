package com.baseproject.ui.main.settings

import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.baseproject.R
import com.baseproject.common.base.BaseFragment
import com.baseproject.common.base.BaseViewModel
import com.baseproject.databinding.FragmentMainBinding

class SettingsFragment : BaseFragment(R.layout.fragment_settings), View.OnClickListener {

    private val binding by viewBinding(FragmentMainBinding::bind)

    override val viewModel: BaseViewModel
        get() = TODO("Not yet implemented")

    override fun setupInsets() {
        TODO("Not yet implemented")
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}