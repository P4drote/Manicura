package com.example.manicura.ui.agregarCliente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.manicura.R
import com.example.manicura.database.ManicuraDataBase
import com.example.manicura.databinding.DialogAgregarClienteBinding
import kotlinx.android.synthetic.main.dialog_agregar_cliente.*


class AgregarClienteDialogo : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: DialogAgregarClienteBinding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_agregar_cliente, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = ManicuraDataBase.getInstance(application).manicuraDAO
        val viewModelFactory = AgregarClienteViewModelFactory(dataSource)
        val agregarClienteViewModel =
            ViewModelProvider(this, viewModelFactory)[AgregarClienteViewModel::class.java]

        binding.agregarClienteViewModel = agregarClienteViewModel

        binding.lifecycleOwner = this

        agregarClienteViewModel.mensage.observe(viewLifecycleOwner, Observer { message ->
            imput_layout_name.error = "Campo requerido"
        })

        agregarClienteViewModel.salida.observe(viewLifecycleOwner, Observer { salida ->
            toast()
            dismiss()
        })

//        binding.buttonRegistrarCliente.setOnClickListener {
//            var nombre = binding.etNuevoCliente.text.toString()
//
//            if (nombre.isEmpty()) {
//                imput_layout_name.error = "Campo requerido"
//                return@setOnClickListener
//            } else {
//                lifecycleScope.launch {
//                    withContext(Dispatchers.IO) {AgregarClienteViewModel.onInsertarCliente(nombre)}
//                    toast()
//                }
//                this.dismiss()
//            }
//        }

        return binding.root

    }

    private fun toast() {
        Toast.makeText(this.context, "El cliente se ha agregado", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)

    }


}

