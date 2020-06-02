package com.example.manicura.ui.servicios

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.manicura.R
import com.example.manicura.TodosServicios
import com.example.manicura.Utils
import com.example.manicura.database.ManicuraDataBase
import com.example.manicura.databinding.FragmentServiciosBinding
import com.example.manicura.ui.editorServicios.DialogEditarServicios

class ServiciosFragment : Fragment(), AdaptadorServicios.OnItemClickListener {

    private lateinit var binding: FragmentServiciosBinding
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: RecyclerView.Adapter<*>

    private lateinit var serviciosViewModel: ServiciosViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentServiciosBinding.inflate(layoutInflater)

        val application = requireNotNull(this.activity).application
        val dataSource = ManicuraDataBase.getInstance(application).manicuraDAO
        val viewModelFactory = ServiciosViewModelFactory(dataSource, application)
        serviciosViewModel =
            ViewModelProvider(this, viewModelFactory)[ServiciosViewModel::class.java]
        binding.lifecycleOwner = this

        viewManager = LinearLayoutManager(this.requireContext())

        binding.recyclerView.layoutManager = viewManager

        binding.topMenu.setOnItemSelectedListener { id ->
            val option = when (id) {
                R.id.mesActual -> mesActual()
                R.id.mesAnterior -> mesAnterior()
                else -> todosLosServicios()
            }
        }

        binding.topMenu.setItemSelected(R.id.mesActual)

        serviciosViewModel.todosLosServicios.observe(viewLifecycleOwner, Observer {
            viewAdapter = AdaptadorServicios(it, binding.root.context, this)
            binding.recyclerView.adapter = viewAdapter
        })

        serviciosViewModel.serviciosMesActual.observe(viewLifecycleOwner, Observer {
            viewAdapter = AdaptadorServicios(it, binding.root.context, this)
            binding.recyclerView.adapter = viewAdapter
        })

        serviciosViewModel.serviciosMesPasado.observe(viewLifecycleOwner, Observer {
            viewAdapter = AdaptadorServicios(it, binding.root.context, this)
            binding.recyclerView.adapter = viewAdapter
        })

        return binding.root
    }

    private fun todosLosServicios() {
        serviciosViewModel.obtenerTodosLosServicios()
    }

    @SuppressLint("NewApi")
    private fun mesAnterior() {
        val ultimodiaMes = Utils.calcularPrimerDiaMes(System.currentTimeMillis()) - 1L
        val primerdiaMes = Utils.calcularPrimerDiaMes(ultimodiaMes)
        serviciosViewModel.obtenerServiciosMesPasado(primerdiaMes, ultimodiaMes)
    }

    @SuppressLint("NewApi")
    private fun mesActual() {
        val fechaActual = System.currentTimeMillis()
        val primerdiaMes = Utils.calcularPrimerDiaMes(fechaActual)
        val ultimodiaMes = Utils.calcularUltimoDiaMes(fechaActual)
        serviciosViewModel.obtenerServiciosMes(primerdiaMes, ultimodiaMes)
    }

    override fun onItemCLick(servicios: TodosServicios) {
        val fragmentManager = parentFragmentManager
        var datosAEnviar = Bundle()
        datosAEnviar.putSerializable("servicio", servicios)
//        datosAEnviar.putLong("idCliente", idCliente)
//        datosAEnviar.putLong("fecha", fecha)
//        datosAEnviar.putString("cliente", cliente)
        val dialogo = DialogEditarServicios()
        dialogo.arguments = datosAEnviar
        dialogo.show(fragmentManager, "")
    }


}
