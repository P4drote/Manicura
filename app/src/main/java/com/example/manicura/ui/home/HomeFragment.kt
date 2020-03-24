package com.example.manicura.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.manicura.R
import com.example.manicura.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel

    private lateinit var binding : FragmentHomeBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        binding =DataBindingUtil.inflate(inflater,R.layout.fragment_home, container,false)
        //val root = inflater.inflate(R.layout.fragment_home, container, false)

        binding.homeViewModel = viewModel
        binding.setLifecycleOwner (this)

        viewModel.MontoTotal.observe(viewLifecycleOwner, Observer {

        })

        return binding.root
    }
}
