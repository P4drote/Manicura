package com.example.manicura.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.manicura.Notificacion
import com.example.manicura.Utils
import com.example.manicura.database.ManicuraDAO
import kotlinx.coroutines.*

class HomeViewModel(private val dataSource: ManicuraDAO, application: Application) :
    AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    var MontoTotal = MutableLiveData<Float>()

    var GananciasBrutas = MutableLiveData<String>()

    var GananciasLiquidas = MutableLiveData<String>()

    private var _calculo = MutableLiveData<Double>()
    val calculo: LiveData<Double> get() = _calculo

    private var _Notificaciones = MutableLiveData<List<Notificacion>>()
    val Notificaciones: LiveData<List<Notificacion>> get() = _Notificaciones

    private var _Ganancias = MutableLiveData<Double>()
    val Ganancias: LiveData<Double> get() = _Ganancias

    init {
        MontoTotal.value = 0.0F
//        _Ganancias.value = 0.0
    }


    fun llenarNotificaciones(horaActual: Long, inicio: Int, fin: Int) {
        uiScope.launch {
            _Notificaciones.value = withContext(Dispatchers.IO) {
                dataSource.getNotificaciones(
                    Utils.calcularDiasAtras(horaActual, inicio),
                    Utils.calcularDiasAtras(horaActual, fin)
                )
            }
        }
    }

    fun colocarGanancias(porcentaje: Long) {
        val horaActual = System.currentTimeMillis()
        val inicioMes = Utils.calcularPrimerDiaMes(horaActual)
        val inicioDia = Utils.calcularInicioDia(horaActual)

        consultaDiaria(inicioDia, horaActual)

        consultaMensual(inicioMes, horaActual)

    }

    fun consultaDiaria(inicioDia: Long, horaActual: Long) {
        uiScope.launch {
            _calculo.value = withContext(Dispatchers.IO) {
                dataSource.getGanancias(inicioDia, horaActual)
            }
        }
    }

    fun consultaMensual(inicioMes: Long, horaActual: Long) {
        uiScope.launch {
            _Ganancias.value = withContext(Dispatchers.IO) {
                dataSource.getGanancias(inicioMes, horaActual)
            }
        }
    }


}