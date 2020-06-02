package com.example.manicura.ui.servicios

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.manicura.TodosServicios
import com.example.manicura.database.ManicuraDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ServiciosViewModel(private val dataSource: ManicuraDAO, application: Application) :
    AndroidViewModel(application) {

    private val _todosLosServicios = MutableLiveData<List<TodosServicios>>()
    val todosLosServicios: LiveData<List<TodosServicios>> get() = _todosLosServicios

    private val _serviciosMesActual = MutableLiveData<List<TodosServicios>>()
    val serviciosMesActual: LiveData<List<TodosServicios>> get() = _serviciosMesActual

    private val _serviciosMesPasado = MutableLiveData<List<TodosServicios>>()
    val serviciosMesPasado: LiveData<List<TodosServicios>> get() = _serviciosMesPasado

    fun obtenerTodosLosServicios() {
        viewModelScope.launch {
            _todosLosServicios.value = withContext(Dispatchers.IO) {
                dataSource.getTodosServicios()
            }
        }
    }

    fun obtenerServiciosMes(fechaInicial: Long, fechaFinal: Long) {
        viewModelScope.launch {
            _serviciosMesActual.value = withContext(Dispatchers.IO) {
                dataSource.getServiciosPorFecha(fechaInicial, fechaFinal)
            }
        }
    }

    fun obtenerServiciosMesPasado(fechaInicial: Long, fechaFinal: Long) {
        viewModelScope.launch {
            _serviciosMesPasado.value = withContext(Dispatchers.IO) {
                dataSource.getServiciosPorFecha(fechaInicial, fechaFinal)
            }
        }
    }
}