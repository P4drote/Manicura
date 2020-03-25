package com.example.manicura.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.manicura.AdaptadorPrincipal
import com.example.manicura.R
import com.example.manicura.databinding.FragmentHomeBinding

private lateinit var recyclerView: RecyclerView
private lateinit var viewAdapter: RecyclerView.Adapter<*>
private lateinit var viewManager: RecyclerView.LayoutManager

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

        viewManager = LinearLayoutManager(this.context)
        viewAdapter = AdaptadorPrincipal(
            viewModel.llenarNotificaciones(),
            this.requireContext()
        )

        recyclerView = binding.recyclerView.apply {

            layoutManager = viewManager

            adapter = viewAdapter
        }

        return binding.root

        binding.fabAgregarServicio.setOnClickListener {


        }
    }
}
