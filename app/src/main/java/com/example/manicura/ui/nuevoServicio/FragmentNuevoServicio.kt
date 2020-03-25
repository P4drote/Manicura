package com.example.manicura.ui.nuevoServicio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.manicura.R
import com.example.manicura.databinding.FragmentNuevoServicioBinding
import com.example.manicura.ui.agregarCliente.AgregarClienteDialogo


class FragmentNuevoServicio : Fragment() {

    private lateinit var binding: FragmentNuevoServicioBinding
    private lateinit var viewModel: NuevoServicioViewModel
    var HacerPies: Boolean = true
    var HacerManos: Boolean = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProviders.of(this).get(NuevoServicioViewModel::class.java)
        binding = DataBindingUtil.inflate<FragmentNuevoServicioBinding>(
            inflater,
            R.layout.fragment_nuevo_servicio, container, false
        )

        binding.lifecycleOwner = this

        binding.ibAtras.setOnClickListener { view: View ->
            Navigation.findNavController(view)
                .navigate(R.id.action_fragmentNuevoServicio_to_navigation_home)
        }

        binding.chipManos.setOnClickListener {
            if (HacerManos) {
                binding.chipManos.chipBackgroundColor =
                    ContextCompat.getColorStateList(binding.root.context, R.color.colorPrimary)
                HacerManos = false
            } else {
                binding.chipManos.chipBackgroundColor =
                    ContextCompat.getColorStateList(binding.root.context, R.color.colorDesabilitado)
                HacerManos = true
            }
        }

        binding.chipPies.setOnClickListener {
            if (HacerPies) {
                binding.chipPies.chipBackgroundColor =
                    ContextCompat.getColorStateList(binding.root.context, R.color.colorPrimary)
                HacerPies = false
            } else {
                binding.chipPies.chipBackgroundColor =
                    ContextCompat.getColorStateList(binding.root.context, R.color.colorDesabilitado)
                HacerPies = true
            }
        }

        binding.fabAgregarCliente.setOnClickListener {
            val fragmentManager = parentFragmentManager
            val dialogo =
                AgregarClienteDialogo()
            dialogo.show(fragmentManager, "")
        }

        return binding.root
    }


}
