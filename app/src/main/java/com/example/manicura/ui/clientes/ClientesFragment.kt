package com.example.manicura.ui.clientes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.manicura.R
import com.example.manicura.Utils
import com.example.manicura.database.ManicuraDataBase
import com.example.manicura.database.TablaCliente
import com.example.manicura.databinding.FragmentClientesBinding

private lateinit var viewManager: RecyclerView.LayoutManager
private lateinit var viewAdapter: RecyclerView.Adapter<*>
private lateinit var recyclerView: RecyclerView

class ClientesFragment : Fragment(), AdaptadorClientes.OnItemClickListener {

    private lateinit var clientesViewModel: ClientesViewModel
    private lateinit var binding: FragmentClientesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentClientesBinding>(
            inflater,
            R.layout.fragment_clientes,
            container,
            false
        )
        val application = requireNotNull(this.activity).application
        val dataSource = ManicuraDataBase.getInstance(application).manicuraDAO
        val viewModelFactory = ClientesViewModelFactory(dataSource, application)
        clientesViewModel = ViewModelProvider(this, viewModelFactory)[ClientesViewModel::class.java]

        binding.lifecycleOwner = this
        binding.framentClienteViewModel = clientesViewModel
        viewManager = LinearLayoutManager(this.context)

        clientesViewModel.listaClientes.observe(viewLifecycleOwner, Observer {
            viewAdapter = AdaptadorClientes(it, this.requireContext(), this)
            recyclerView = binding.recyclerView.apply {

                layoutManager = viewManager

                adapter = viewAdapter
            }
        })

        clientesViewModel.clickCliente.observe(viewLifecycleOwner, Observer {
            binding.etNombreCliente.setText(clientesViewModel.clickCliente.value?.nombre)
        })

        binding.btnAgregar.setOnClickListener {
            if (!binding.etNombreCliente.text.isNullOrEmpty()) {
                clientesViewModel.agregarCliente(binding.etNombreCliente.text.toString())
                Toast.makeText(binding.root.context, "Cliente agregado!", Toast.LENGTH_SHORT).show()
                binding.etNombreCliente.text.clear()
                Utils.hideSoftKeyBoard(binding.root.context, binding.root)
            } else {
                Toast.makeText(
                    binding.root.context,
                    "Debe colocar un nombre de cliente!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        binding.btnBorrar.setOnClickListener {
            if (clientesViewModel.clickCliente.value != null) {
                clientesViewModel.borrarCliente()
                Toast.makeText(binding.root.context, "Cliente Borrado!", Toast.LENGTH_SHORT).show()
                binding.etNombreCliente.text.clear()
                Utils.hideSoftKeyBoard(binding.root.context, binding.root)
            }
        }

        binding.btnEditar.setOnClickListener {
            if (!binding.etNombreCliente.text.isNullOrEmpty()) {
                clientesViewModel.editarCliente(binding.etNombreCliente.text.toString())
                Toast.makeText(binding.root.context, "Cliente actualizado!", Toast.LENGTH_SHORT)
                    .show()
                binding.etNombreCliente.text.clear()
                Utils.hideSoftKeyBoard(binding.root.context, binding.root)
            }
        }

        return binding.root
    }

    override fun onItemCLick(tablaCliente: TablaCliente) {
        clientesViewModel.clickCliente.value = tablaCliente
    }
}

