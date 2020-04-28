package com.example.manicura.ui.ajustes

import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.manicura.R
import com.example.manicura.databinding.FragmentAjustesBinding
import kotlinx.coroutines.Job

class AjustesFragment : Fragment() {

    private lateinit var binding: FragmentAjustesBinding
    private var ViewModelJob = Job()
    private var tienda: String = "TIENDA"
    private var porcentaje: String = "PORCENTAJE"
    private var inicioNotificacion: String = "INICIO_N"
    private var finNotificacion: String = "FIN_N"
    private lateinit var prefs: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentAjustesBinding>(
            inflater,
            R.layout.fragment_ajustes, container, false
        )

        prefs = PreferenceManager.getDefaultSharedPreferences(binding.root.context)
        binding.tvNombreTienda.text = prefs.getString(tienda, "Nombre de Tienda")
        binding.tvPorcentajeGanancia.text = prefs.getString(porcentaje, "35")
        binding.tvInicioNotificacion.text = prefs.getString(inicioNotificacion, "21")
        binding.tvFinNotificacion.text = prefs.getString(finNotificacion, "35")

        //Al accionar el boton hacia atras vuelve al fragment home
        binding.ibAtras.setOnClickListener { view: View ->
            Navigation.findNavController(view)
                .navigate(R.id.action_ajustesFragment2_to_navigation_home)
        }

        //Para cambiar el nombre de la tienda
        binding.cvTienda.setOnClickListener {
            dialogoConEditText(
                View(binding.root.context),
                "Nombre de Tienda+",
                false,
                "Introduzca el nombre de la tienda",
                1
            )
            //dialogoConEditText(View(binding.root.context), "Nombre de Tienda+", false, "Introduzca el nombre de la tienda", 1)
        }

        //Para cambiar el porcentaje de ganancias
        binding.cvPorcentajeDeGanancia.setOnClickListener {
            dialogoConEditText(
                View(binding.root.context),
                "Porcentaje de Ganancia",
                true,
                "Introduzca solo el número de porcentaje",
                2
            )
        }

        //Para cambiar el inicio de las notificaciones
        binding.cvInicioNotificacion.setOnClickListener {
            dialogoConEditText(
                View(binding.root.context),
                "Inicio de Notificación",
                true,
                "Introduzca el numero de días",
                3
            )
        }

        //Para cambiar el fin de las notificaciones
        binding.cvFinNotificacion.setOnClickListener {
            dialogoConEditText(
                View(binding.root.context),
                "Fin de Notificación",
                true,
                "Introduzca el numero de días",
                4
            )
        }

        return binding.root
    }

    fun dialogoConEditText(
        view: View,
        titulo: String,
        numero: Boolean,
        hint: String,
        tipoAjuste: Int
    ) {
        val builder = AlertDialog.Builder(view.context)
        val inflater = layoutInflater
        val editor = prefs.edit()
        builder.setTitle(titulo)
        val dialogLayout = inflater.inflate(R.layout.dialogo_texto, null)
        val editText = dialogLayout.findViewById<EditText>(R.id.editText)
        if (numero) {
            editText.inputType = 2
        } else {
            editText.inputType = 1
        }
        editText.hint = hint
        builder.setView(dialogLayout)
        builder.setPositiveButton("OK") { dialogInterface, i ->

            when (tipoAjuste) {
                1 -> {
                    binding.tvNombreTienda.text = editText.text.toString()
                    editor.putString(tienda, editText.text.toString())
                    editor.apply()

                }
                2 -> {
                    binding.tvPorcentajeGanancia.text = editText.text.toString()
                    editor.putString(porcentaje, editText.text.toString())
                    editor.apply()

                }
                3 -> {
                    binding.tvInicioNotificacion.text = editText.text.toString()
                    editor.putString(inicioNotificacion, editText.text.toString())
                    editor.apply()

                }
                4 -> {
                    binding.tvFinNotificacion.text = editText.text.toString()
                    editor.putString(finNotificacion, editText.text.toString())
                    editor.apply()

                }
            }
        }
        builder.show()

    }

}
