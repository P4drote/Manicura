package com.example.manicura.ui.servicios

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.manicura.R
import com.example.manicura.database.ManicuraDataBase
import com.example.manicura.databinding.FragmentServiciosBinding
import com.example.manicura.ui.clientes.ClientesViewModel

class ServiciosFragment : Fragment() {

    private lateinit var clientesViewModel: ClientesViewModel
    private lateinit var binding: FragmentServiciosBinding

    private lateinit var serviciosViewModel: ServiciosViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        serviciosViewModel = ViewModelProvider(this)[ServiciosViewModel::class.java]
        binding = DataBindingUtil.inflate<FragmentServiciosBinding>(
            inflater,
            R.layout.fragment_servicios,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = ManicuraDataBase.getInstance(application).manicuraDAO
        binding.lifecycleOwner = this
        binding.fragmentServiciosBinding = serviciosViewModel


        return binding.root
    }

}
