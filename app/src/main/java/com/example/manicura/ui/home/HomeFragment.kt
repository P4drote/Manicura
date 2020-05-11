package com.example.manicura.ui.home

import android.animation.ValueAnimator
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.manicura.R
import com.example.manicura.database.ManicuraDataBase
import com.example.manicura.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.DecimalFormat
import java.util.*

private lateinit var recyclerView: RecyclerView
private lateinit var viewAdapter: RecyclerView.Adapter<*>
private lateinit var viewManager: RecyclerView.LayoutManager


class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var prefs: SharedPreferences
    private lateinit var binding: FragmentHomeBinding
    private var inicioNotificacion: String = "INICIO_N"
    private var finNotificacion: String = "FIN_N"
    private var porcentaje: String = "PORCENTAJE"
    private var TemporalGanancias: String = "TEMPORALGANANCIAS"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        binding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )
        //val root = inflater.inflate(R.layout.fragment_home, container, false)

        prefs = PreferenceManager.getDefaultSharedPreferences(binding.root.context)
        val application = requireNotNull(this.activity).application
        val dataSource = ManicuraDataBase.getInstance(application).manicuraDAO
        val viewModelFactory = HomeViewModelFactory(dataSource, application)
        val editor = prefs.edit()
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]

        binding.homeViewModel = viewModel
        binding.lifecycleOwner = this

//        viewModel.MontoTotal.observe(viewLifecycleOwner, Observer {
//
//        })

        viewManager = LinearLayoutManager(this.context)
        prefs = PreferenceManager.getDefaultSharedPreferences(binding.root.context)
        var inicio = prefs.getString(inicioNotificacion, "21")!!.toInt()
        var fin = prefs.getString(finNotificacion, "35")!!.toInt()
        var horaActual = System.currentTimeMillis()
        var porcentaje = prefs.getString(porcentaje, "35")!!.toLong()
        var temporalGanancias = prefs.getFloat(TemporalGanancias, 0.0F)

        viewModel.llenarNotificaciones(horaActual, inicio, fin)

        viewModel.Notificaciones.observe(viewLifecycleOwner, Observer {
            viewAdapter = AdaptadorHome(it, this.requireContext())
            recyclerView = binding.recyclerView.apply {

                layoutManager = viewManager

                adapter = viewAdapter
            }
        })

        val dec = DecimalFormat.getCurrencyInstance(Locale.CANADA)

        viewModel.colocarGanancias(porcentaje)

        viewModel.Ganancias.observe(viewLifecycleOwner, Observer { total ->
            var intermedio = total!! * porcentaje / 100.0
            if (intermedio != temporalGanancias.toDouble()) {
                val animator =
                    ValueAnimator.ofFloat(viewModel.MontoTotal.value!!, intermedio.toFloat())
                viewModel.MontoTotal.value = intermedio.toFloat()
                animator.duration = 2000
                animator.addUpdateListener { animation ->
                    tv_MontoTotal.text = dec.format(animation.animatedValue).toString()
                }
                animator.start()
                editor.putFloat(TemporalGanancias, intermedio.toFloat())
                editor.apply()
            }
            tv_MontoTotal.text = dec.format(intermedio).toString()

        })

        viewModel.calculo.observe(viewLifecycleOwner, Observer {
//            viewModel.GananciasBrutas.value = dec.format(it).toString()!!
//            viewModel.GananciasLiquidas.value =
//                dec.format((it?.times(porcentaje) ?: 0.0) / 100.0).toString()!!
        })

        binding.ibAjustes.setOnClickListener { view: View ->
            Navigation.findNavController(view)
                .navigate(R.id.action_navigation_home_to_ajustesFragment2)
        }

        binding.fabAgregarServicio.setOnClickListener { view: View ->
            //FragmentNuevoServicio.startActivity(requireContext(), transformationLayout_fab)
            Navigation.findNavController(view)
                .navigate(R.id.action_navigation_home_to_fragmentNuevoServicio)
        }

        return binding.root

    }
}
