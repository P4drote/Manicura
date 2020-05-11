package com.example.manicura.ui.nuevoServicio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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


        fun agregarServicio() {
            var nombreCliente = binding.actvNombreCliente.text.toString()
            var montoPagado: Double = binding.etCobro.text.toString().toDouble()
            this.lifecycleScope.launch {
                viewModel.onAgregarServicio(nombreCliente, montoPagado)
            }
            //Utils.hideSoftKeyBoard(binding.root.context, this.nav_view)
            //View?.let { it1 -> Utils.hideSoftKeyBoard(binding.root.context, it1) }
            Toast.makeText(
                binding.root.context,
                "Servicio agregado!",
                Toast.LENGTH_LONG
            ).show()
        }


        fun dialogoAnimado(Nombre: String) {
            // Animated BottomSheet Material Dialog
            val dialogo = BottomSheetDialog(this.requireContext(), R.style.BottonSheetDialogTheme)
            val view = this.layoutInflater.inflate(R.layout.layout_botton_sheet, null)
            val close = view.findViewById<Button>(R.id.btn_cancel)
            val agregar = view.findViewById<Button>(R.id.btn_agregarCliente)
            val mensaje = view.findViewById<TextView>(R.id.tvMensaje)
            mensaje.text = Nombre + " no existe en la base de datos. Deseas Agregarl@? "
            close.setOnClickListener {
                dialogo.dismissWithAnimation = true
                dialogo.dismiss()
            }
            agregar.setOnClickListener {
                viewModel.onInsertarCliente(Nombre)
                dialogo.dismissWithAnimation = true
                dialogo.dismiss()
                agregarServicio()
                Navigation.findNavController(view)
                    .navigate(R.id.action_fragmentNuevoServicio_to_navigation_home)
            }
            dialogo.setContentView(view)
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

        viewModel.idClienteSeleccionado.observe(viewLifecycleOwner, Observer {
            if (it == 0L) {
                dialogoAnimado(binding.actvNombreCliente.text.toString())
            } else {
                agregarServicio()
            }
        })


        //Botón AGREGAR CLIENTE llamo al diálogo para agregar un nuevo cliente
//        binding.btnAgregarCliente.setOnClickListener {
//            val fragmentManager = supportFragmentManager
//            val dialogo = AgregarClienteDialogo()
//            dialogo.show(fragmentManager, "")
//        }

        //Agrego el servicio a la bd y vuelvo a la pantalla inicial
        binding.btnAgregarServicio.setOnClickListener {
            if (binding.actvNombreCliente.text.isNotEmpty()) {
                if (binding.etCobro.text?.isNotEmpty()!!) {
                    if (viewModel.hacerManos.value!! or viewModel.hacerPies.value!!) {
                        var idCliente: Long = 0L
                        var elNombre = binding.actvNombreCliente.text.toString()
                        this.lifecycleScope.launch {
                            viewModel.onGetIdCliente(elNombre)
                        }
                    } else FancyToast.makeText(
                        this.requireContext(),
                        "Manos? Pies?",
                        FancyToast.LENGTH_LONG,
                        FancyToast.WARNING,
                        false
                    ).show()

                } else {
                    FancyToast.makeText(
                        this.requireContext(),
                        "Se requiere un monto a cobrar!",
                        FancyToast.LENGTH_LONG,
                        FancyToast.WARNING,
                        false
                    ).show()
                    this.til_MontoACobrar.error = "Coloque el monto a cobrar!"
                }

            } else {
                FancyToast.makeText(
                    this.requireContext(),
                    "Se requiere un nombre de cliente!",
                    FancyToast.LENGTH_LONG,
                    FancyToast.WARNING,
                    false
                ).show()
                this.til_NombreCliente.error = "Elija o coloque un nombre de cliente!"
            }

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




