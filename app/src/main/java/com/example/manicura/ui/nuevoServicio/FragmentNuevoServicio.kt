package com.example.manicura.ui.nuevoServicio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.manicura.R
import com.example.manicura.Utils
import com.example.manicura.database.ManicuraDataBase
import com.example.manicura.databinding.FragmentNuevoServicioBinding
import com.example.manicura.ui.agregarCliente.AgregarClienteDialogo
import kotlinx.coroutines.launch

private lateinit var viewAdapter: RecyclerView.Adapter<*>
private lateinit var viewManager: RecyclerView.LayoutManager
private lateinit var viewModel: NuevoServicioViewModel
private lateinit var binding: FragmentNuevoServicioBinding

class FragmentNuevoServicio : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_nuevo_servicio, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = ManicuraDataBase.getInstance(application).manicuraDAO
        val viewModelFactory = NuevoServicioViewModelFactory(dataSource, application)
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(NuevoServicioViewModel::class.java)

        binding.nuevoServicioViewModel = viewModel

        binding.lifecycleOwner = this

        viewManager = LinearLayoutManager(this.context)

        var adaptador: ArrayAdapter<String>
        binding.actvNombreCliente.threshold = 0

        binding.recyclerView.layoutManager = viewManager

        //Actualizo el RecyclerView con la lista de los clientes seleccionados
        viewModel.serviciosCliente.observe(viewLifecycleOwner, Observer {
            it?.let {
                viewAdapter = AdaptadorNuevoServicio(it, this.requireContext())
                binding.recyclerView.adapter = viewAdapter
            }
        })

        //Al seleccionar cliente reviso en la bd los servicios que tenga y lleno el recyclerView
        binding.actvNombreCliente.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                var clienteSeleccionado = binding.actvNombreCliente.text.toString()
                viewModel.nombreDeClienteClick(clienteSeleccionado)
            }

        //Monitoreo cambios en las lista de clientes y actualizo lista en actvNombreCliente
        viewModel.nombresClientes.observe(viewLifecycleOwner, Observer {
            it?.let {
                adaptador = ArrayAdapter(
                    binding.root.context,
                    android.R.layout.simple_expandable_list_item_1,
                    it
                )
                binding.actvNombreCliente.setAdapter(adaptador)
            }
        })

        //Monitoreo los chip MANOS para cambiar el color segun la selección del usuario
        viewModel.hacerManos.observe(viewLifecycleOwner, Observer { manos ->
            if (manos) {
                binding.chipManos.chipBackgroundColor =
                    ContextCompat.getColorStateList(binding.root.context, R.color.colorPrimary)
            } else {
                binding.chipManos.chipBackgroundColor =
                    ContextCompat.getColorStateList(binding.root.context, R.color.colorDesabilitado)
            }
        })

        //Monitoreo los chip PIES para cambiar el color segun la selección del usuario
        viewModel.hacerPies.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.chipPies.chipBackgroundColor =
                    ContextCompat.getColorStateList(binding.root.context, R.color.colorPrimary)
            } else {
                binding.chipPies.chipBackgroundColor =
                    ContextCompat.getColorStateList(binding.root.context, R.color.colorDesabilitado)
            }
        })

        //Botón AGREGAR CLIENTE llamo al diálogo para agregar un nuevo cliente
        binding.fabAgregarCliente.setOnClickListener {
            val fragmentManager = parentFragmentManager
            val dialogo = AgregarClienteDialogo()
            dialogo.show(fragmentManager, "")
        }

        //Agrego el servicio a la bd y vuelvo a la pantalla inicial
        binding.fabAgregarServicio.setOnClickListener {
            if (binding.actvNombreCliente.text.isNotEmpty()) {
                if (binding.etCobro.text.isNotEmpty()) {
                    if (viewModel.hacerManos.value!! or viewModel.hacerPies.value!!) {
                        var nombreCliente = binding.actvNombreCliente.text.toString()
                        var montoPagado: Double = binding.etCobro.text.toString().toDouble()
                        lifecycleScope.launch {
                            viewModel.onAgregarServicio(nombreCliente, montoPagado)
                        }
                        view?.let { it1 -> Utils.hideSoftKeyBoard(binding.root.context, it1) }
                        Toast.makeText(
                            binding.root.context,
                            "Servicio agregado!",
                            Toast.LENGTH_LONG
                        ).show()
                        this.findNavController()
                            .navigate(R.id.action_fragmentNuevoServicio_to_navigation_home)
                    } else Toast.makeText(binding.root.context, "Manos? Pies?", Toast.LENGTH_LONG)
                        .show()
                } else Toast.makeText(
                    binding.root.context,
                    "Se requiere un monto a cobrar!",
                    Toast.LENGTH_LONG
                ).show()
            } else Toast.makeText(
                binding.root.context,
                "Se requiere un nombre de cliente!",
                Toast.LENGTH_LONG
            ).show()
        }

        //Botón Atrás
        binding.ibAtras.setOnClickListener { view: View ->
            Navigation.findNavController(view)
                .navigate(R.id.action_fragmentNuevoServicio_to_navigation_home)
            Utils.hideSoftKeyBoard(binding.root.context, view)
        }



        return binding.root
    }
}




