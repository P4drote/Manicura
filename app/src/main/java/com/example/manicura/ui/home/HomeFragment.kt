package com.example.manicura.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.manicura.R
import com.example.manicura.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var binding : FragmentHomeBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        binding =DataBindingUtil.inflate(inflater,R.layout.fragment_home, container,false)
        //val root = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel.MontoTotal.observe(viewLifecycleOwner, Observer {

        })
        //homeViewModel.text.observe(viewLifecycleOwner, Observer {

        //})
        return binding.root
    }
}
