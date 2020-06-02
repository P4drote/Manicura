package com.example.manicura.ui.editorServicios

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.manicura.R
import com.example.manicura.TodosServicios
import com.example.manicura.database.ManicuraDataBase
import com.example.manicura.databinding.DialogEditarServiciosBinding

private lateinit var viewModel: DialogEditarServiciosViewModel

class DialogEditarServicios : DialogFragment() {

    companion object {
        fun newInstance() = DialogEditarServicios()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
        val binding = DialogEditarServiciosBinding.inflate(layoutInflater)
        val application = requireNotNull(this.activity).application
        val dataSource = ManicuraDataBase.getInstance(application).manicuraDAO
        val servicio: TodosServicios = arguments?.getSerializable("servicio") as TodosServicios
        val viewModelFactory = DialogEditarServiciosViewModelFactory(dataSource, application)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[DialogEditarServiciosViewModel::class.java]

        binding.tvCliente.text = servicio.nombre_cliente
        binding.etCobro.setText(servicio.montoPagado.toString())
        viewModel.hacerManos.value = servicio.manos
        viewModel.hacerPies.value = servicio.pies
        viewModel.monto.value = servicio.montoPagado
        viewModel.fecha.value = servicio.fecha
        viewModel.idCliente.value = servicio.clienteId


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


        binding.btnActualizar.setOnClickListener {

        }

        binding.chipManos.setOnClickListener {
            viewModel.hacerManos.value = !viewModel.hacerManos.value!!
        }

        binding.chipPies.setOnClickListener {
            viewModel.hacerPies.value = !viewModel.hacerPies.value!!
        }



        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)


    }

}
