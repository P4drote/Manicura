package com.example.manicura.ui.nuevoServicio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.manicura.R
import com.example.manicura.Utils
import com.example.manicura.database.ManicuraDataBase
import com.example.manicura.databinding.FragmentNuevoServicioBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.fragment_nuevo_servicio.*
import kotlinx.coroutines.launch

private lateinit var viewAdapter: RecyclerView.Adapter<*>
private lateinit var viewManager: RecyclerView.LayoutManager
private lateinit var viewModel: NuevoServicioViewModel
private lateinit var binding: FragmentNuevoServicioBinding

class FragmentNuevoServicio : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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

        viewManager = LinearLayoutManager(this.requireContext())

        var adaptador: ArrayAdapter<String>
        binding.actvNombreCliente.threshold = 0

        binding.recyclerView.layoutManager = viewManager

        var animacion = LayoutAnimationController(
            AnimationUtils.loadAnimation(
                binding.root.context,
                R.anim.item_anim
            )
        )
        animacion.delay = 0.20F
        animacion.order = LayoutAnimationController.ORDER_NORMAL
        binding.recyclerView.layoutAnimation = animacion

        //ads
        MobileAds.initialize(binding.root.context) {}
        val mAdView = binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        fun agregarServicio() {
            var nombreCliente = binding.actvNombreCliente.text.toString()
            var montoPagado: Double = binding.etCobro.text.toString().toDouble()
            this.lifecycleScope.launch {
                viewModel.onAgregarServicio(nombreCliente, montoPagado)
            }
            //Utils.hideSoftKeyBoard(binding.root.context, this.nav_view)
            binding.root.rootView?.let { it1 -> Utils.hideSoftKeyBoard(binding.root.context, it1) }
            FancyToast.makeText(
                this.requireContext(),
                "Servicio agregado!",
                FancyToast.LENGTH_LONG,
                FancyToast.SUCCESS,
                false
            ).show()

            activity?.onBackPressed()
        }


        //Función de dialogo animado para preguntar al usuario si va a agregar un nuevo cliente a la base de datos
        fun dialogoAnimado(Nombre: String) {
            // Animated BottomSheet Material Dialog
            val dialogo = BottomSheetDialog(this.requireContext(), R.style.BottonSheetDialogTheme)
            val viewBottonLayout = this.layoutInflater.inflate(R.layout.layout_botton_sheet, null)
            val close = viewBottonLayout.findViewById<Button>(R.id.btn_cancel)
            val agregar = viewBottonLayout.findViewById<Button>(R.id.btn_agregarCliente)
            val mensaje = viewBottonLayout.findViewById<TextView>(R.id.tvMensaje)
            val mensajeDialog = "$Nombre no existe en la base de datos. Deseas Agregarl@?"
            mensaje.text = mensajeDialog
            close.setOnClickListener {
                dialogo.dismissWithAnimation = true
                dialogo.dismiss()
            }
            agregar.setOnClickListener {
                viewModel.onInsertarCliente(Nombre)
                dialogo.dismissWithAnimation = true
                dialogo.dismiss()
                agregarServicio()
            }
            dialogo.setContentView(viewBottonLayout)
            dialogo.setCancelable(false)
            dialogo.show()
        }

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
                val clienteSeleccionado = binding.actvNombreCliente.text.toString()
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

        viewModel.idClienteSeleccionado.observe(viewLifecycleOwner, Observer {
            if (it == 0L) {
                dialogoAnimado(binding.actvNombreCliente.text.toString())
            } else {
                agregarServicio()
            }
        })


        //Agrego el servicio a la bd y vuelvo a la pantalla inicial
        binding.btnAgregarServicio.setOnClickListener {
            var animShake = AnimationUtils.loadAnimation(binding.root.context, R.anim.vibrar)
            if (binding.actvNombreCliente.text.isNotEmpty()) {
                if (binding.etCobro.text?.isNotEmpty()!!) {
                    if (viewModel.hacerManos.value!! or viewModel.hacerPies.value!!) {
                        var idCliente: Long = 0L
                        var elNombre = binding.actvNombreCliente.text.toString()
                        this.lifecycleScope.launch {
                            viewModel.onGetIdCliente(elNombre)
                        }
                    } else {
                        FancyToast.makeText(
                            this.requireContext(),
                            "Manos? Pies?",
                            FancyToast.LENGTH_LONG,
                            FancyToast.WARNING,
                            false
                        ).show()
                        chip_Manos.startAnimation(animShake)
                        chip_Pies.startAnimation(animShake)
                        til_MontoACobrar.error = null
                        til_NombreCliente.error = null
                    }
                } else {
                    FancyToast.makeText(
                        this.requireContext(),
                        "Se requiere un monto a cobrar!",
                        FancyToast.LENGTH_LONG,
                        FancyToast.WARNING,
                        false
                    ).show()
                    til_MontoACobrar.startAnimation(animShake)
                    this.til_MontoACobrar.error = "Coloque el monto a cobrar!"
                    til_NombreCliente.error = null
                }

            } else {
                FancyToast.makeText(
                    this.requireContext(),
                    "Se requiere un nombre de cliente!",
                    FancyToast.LENGTH_LONG,
                    FancyToast.WARNING,
                    false
                ).show()

                til_NombreCliente.startAnimation(animShake)
                this.til_NombreCliente.error = "Elija o coloque un nombre de cliente!"
            }
        }

        //Botón Atrás
        binding.ibAtras.setOnClickListener { view: View ->
            Utils.hideSoftKeyBoard(binding.root.context, view)
            Thread.sleep(100)
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_fragmentNuevoServicio_to_navigation_home)

        }
        return binding.root
    }

}




