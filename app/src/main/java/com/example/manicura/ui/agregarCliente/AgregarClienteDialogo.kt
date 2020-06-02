package com.example.manicura.ui.agregarCliente

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.manicura.R
import com.example.manicura.Utils
import com.example.manicura.database.ManicuraDataBase
import com.example.manicura.databinding.DialogAgregarClienteBinding
import com.skydoves.transformationlayout.TransformationCompat
import com.skydoves.transformationlayout.TransformationLayout
import kotlinx.android.synthetic.main.dialog_agregar_cliente.*

private lateinit var binding: DialogAgregarClienteBinding

class AgregarClienteDialogo : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_agregar_cliente, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = ManicuraDataBase.getInstance(application).manicuraDAO
        val viewModelFactory = AgregarClienteViewModelFactory(dataSource)
        val agregarClienteViewModel =
            ViewModelProvider(this, viewModelFactory)[AgregarClienteViewModel::class.java]


        binding.agregarClienteViewModel = agregarClienteViewModel

        binding.lifecycleOwner = this

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        agregarClienteViewModel.mensage.observe(viewLifecycleOwner, Observer { message ->
            imput_layout_name.error = "Campo requerido"
            var animShake = AnimationUtils.loadAnimation(binding.root.context, R.anim.vibrar)
            imput_layout_name.startAnimation(animShake)
        })

        agregarClienteViewModel.salida.observe(viewLifecycleOwner, Observer { salida ->
            toast()
            Utils.hideSoftKeyBoard(binding.root.context, binding.root)
            dismiss()

        })

        return binding.root

    }

    private fun toast() {
        Toast.makeText(this.activity, "El cliente se ha agregado", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)

    }

    companion object {
        fun startActivity(
            context: Context,
            transformationLayout: TransformationLayout
        ) {
            val intent = Intent(context, AgregarClienteDialogo::class.java)
            TransformationCompat.startActivity(transformationLayout, intent)
        }
    }
}

