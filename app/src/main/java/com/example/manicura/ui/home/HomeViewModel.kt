package com.example.manicura.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.manicura.AdaptadorPrincipal
import com.example.manicura.Notificacion

class HomeViewModel : ViewModel() {

    private var _MontoTotal = MutableLiveData<String>()

    val MontoTotal: LiveData<String> = _MontoTotal

    private var _GananciasBrutas = MutableLiveData<String>()
    val GananciasBrutas: LiveData<String> = _MontoTotal

    private var _GananciasLiquidas = MutableLiveData<String>()
    val GananciasLiquidas: LiveData<String> = _MontoTotal



    init {
        _MontoTotal.value = "$0.000.000,00"
        _GananciasBrutas.value = "$0.000.000,00"
        _GananciasLiquidas.value = "$0.000.000,00"


    }


    fun llenarNotificaciones() : ArrayList<Notificacion> {
        val lista = ArrayList<Notificacion>()

        lista.add(
            Notificacion(
                "Amapola Hernandez",
                "Ultimo Servicio hace 21 dias",
                true,
                false
            )
        )
        lista.add(
            Notificacion(
                "Andrés Cermeño",
                "Ultimo Servicio hace 21 dias",
                true,
                true
            )
        )
        lista.add(
            Notificacion(
                "Zoe Cermeño",
                "Ultimo Servicio hace 21 dias",
                true,
                true
            )
        )
        lista.add(
            Notificacion(
                "Mia Cermeño",
                "Ultimo Servicio hace 21 dias",
                true,
                true
            )
        )
        lista.add(
            Notificacion(
                "Andrés Cermeño",
                "Ultimo Servicio hace 21 dias",
                true,
                true
            )
        )
        lista.add(
            Notificacion(
                "Andrés Cermeño",
                "Ultimo Servicio hace 21 dias",
                true,
                true
            )
        )
        lista.add(
            Notificacion(
                "Andrés Cermeño",
                "Ultimo Servicio hace 21 dias",
                true,
                true
            )
        )
        lista.add(
            Notificacion(
                "Andrés Cermeño",
                "Ultimo Servicio hace 21 dias",
                true,
                true
            )
        )
        lista.add(
            Notificacion(
                "Andrés Cermeño",
                "Ultimo Servicio hace 21 dias",
                true,
                true
            )
        )
        lista.add(
            Notificacion(
                "Andrés Cermeño",
                "Ultimo Servicio hace 21 dias",
                true,
                true
            )
        )
        return lista
    }

}