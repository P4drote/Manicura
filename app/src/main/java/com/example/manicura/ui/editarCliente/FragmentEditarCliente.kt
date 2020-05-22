package com.example.manicura.ui.editarCliente

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.manicura.R
import com.example.manicura.Utils
import com.example.manicura.database.ManicuraDataBase
import com.example.manicura.databinding.FragmentEditarClienteBinding
import com.example.manicura.ui.nuevoServicio.AdaptadorNuevoServicio
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.shashank.sony.fancytoastlib.FancyToast

private lateinit var viewAdapter: RecyclerView.Adapter<*>
private lateinit var viewManager: RecyclerView.LayoutManager
private lateinit var binding: FragmentEditarClienteBinding

class FragmentEditarCliente : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_editar_cliente, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = ManicuraDataBase.getInstance(application).manicuraDAO
        val viewModelFactory = EditarClienteViewModelFactory(dataSource)
        val editarClienteViewModel =
            ViewModelProvider(this, viewModelFactory)[EditarClienteViewModel::class.java]

        binding.editarClienteViewModel = editarClienteViewModel

        binding.lifecycleOwner = this

        val identificadorCliente = arguments?.getLong("idCliente")

        viewManager = LinearLayoutManager(this.requireContext())

        binding.recyclerView.layoutManager = viewManager

        if (identificadorCliente != null) {
            editarClienteViewModel.obtenerCliente(identificadorCliente)
        }

        editarClienteViewModel.serviciosCliente.observe(viewLifecycleOwner, Observer {
            binding.etNombreCliente.setText(editarClienteViewModel.elCliente.value!!.nombre)
            if (it.size != 0) {
                binding.tvMensaje.isVisible = false
                viewAdapter = AdaptadorNuevoServicio(it, this.requireContext())
                binding.recyclerView.adapter = viewAdapter
            } else {
                binding.tvMensaje.isVisible = true
            }
        })

        fun Salida(Mensaje: String) {
            FancyToast.makeText(
                this.requireContext(),
                Mensaje,
                FancyToast.LENGTH_LONG,
                FancyToast.SUCCESS,
                false
            ).show()
            Utils.hideSoftKeyBoard(binding.root.context, binding.root)
            activity?.onBackPressed()
        }

        editarClienteViewModel.clienteActualizado.observe(viewLifecycleOwner, Observer {
            Salida("Cliente Actualizado!")
        })

        editarClienteViewModel.clienteBorrado.observe(viewLifecycleOwner, Observer {
            Salida("Cliente Borrado!")
        })

        @SuppressLint("SetTextI18n")
        fun dialogoAnimadoActualizar(NombreAnterior: String, NombreNuevo: String) {
            // Animated BottomSheet Material Dialog
            val dialogo = BottomSheetDialog(this.requireContext(), R.style.BottonSheetDialogTheme)
            val viewBottonLayout = this.layoutInflater.inflate(R.layout.layout_botton_update, null)
            val close = viewBottonLayout.findViewById<Button>(R.id.btn_cancel)
            val aceptar = viewBottonLayout.findViewById<Button>(R.id.btn_agregarCliente)
            val mensaje = viewBottonLayout.findViewById<TextView>(R.id.tvMensaje)

            mensaje.text = "Desea actualizar el Cliente de $NombreAnterior a $NombreNuevo?"

            close.setOnClickListener {
                dialogo.dismissWithAnimation = true
                dialogo.dismiss()
            }
            aceptar.setOnClickListener {
                editarClienteViewModel.actualizarCliente(binding.etNombreCliente.text.toString())
                //viewModel.onInsertarCliente(Nombre)
                dialogo.dismissWithAnimation = true
                dialogo.dismiss()
            }
            dialogo.setContentView(viewBottonLayout)
            dialogo.setCancelable(false)
            dialogo.show()
        }

        fun dialogoAnimadoEliminar(NombreCliente: String) {
            // Animated BottomSheet Material Dialog
            val dialogo = BottomSheetDialog(this.requireContext(), R.style.BottonSheetDialogTheme)
            val viewBottonLayout = this.layoutInflater.inflate(R.layout.layout_botton_delete, null)
            val close = viewBottonLayout.findViewById<Button>(R.id.btn_cancel)
            val aceptar = viewBottonLayout.findViewById<Button>(R.id.btn_agregarCliente)
            val mensaje = viewBottonLayout.findViewById<TextView>(R.id.tvMensaje)

            mensaje.text = "Desea borrar al Cliente $NombreCliente?"

            close.setOnClickListener {
                dialogo.dismissWithAnimation = true
                dialogo.dismiss()
            }
            aceptar.setOnClickListener {
                editarClienteViewModel.borrarCliente()
                dialogo.dismissWithAnimation = true
                dialogo.dismiss()
            }
            dialogo.setContentView(viewBottonLayout)
            dialogo.setCancelable(false)
            dialogo.show()
        }

        binding.ibAtras.setOnClickListener { view: View ->
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_fragmentEditarCliente_to_navigation_clientes)
            Utils.hideSoftKeyBoard(binding.root.context, view)
        }

        binding.btnActualizar.setOnClickListener {
            dialogoAnimadoActualizar(
                editarClienteViewModel.elCliente.value!!.nombre,
                binding.etNombreCliente.text.toString()
            )
        }

        binding.btnEliminar.setOnClickListener {
            dialogoAnimadoEliminar(binding.etNombreCliente.text.toString())
        }

        return binding.root
    }


}
