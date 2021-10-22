package com.baseproject.ui.main.dashboard

import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.baseproject.R
import com.baseproject.common.base.BaseFragment
import com.baseproject.common.base.BaseViewModel
import com.baseproject.databinding.FragmentMainBinding

class DashboardFragment : BaseFragment(R.layout.fragment_dashboard), View.OnClickListener {

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