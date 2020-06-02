package com.example.manicura.ui.nuevoServicio

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.manicura.Servicios
import com.example.manicura.database.ManicuraDAO
import com.example.manicura.database.TablaCliente
import com.example.manicura.database.TablaServicio
import kotlinx.coroutines.*

class NuevoServicioViewModel(private val dataSource: ManicuraDAO, application: Application) :
    AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _nombresClientes: LiveData<List<String>> = dataSource.getClientes()
    val nombresClientes: LiveData<List<String>> get() = _nombresClientes


    var serviciosCliente: MutableLiveData<List<Servicios>> = MutableLiveData()

    private var _agregarServicio = TablaServicio()
    private var _actualizarCliente = TablaCliente()
    var idClienteSeleccionado: MutableLiveData<Long> = MutableLiveData()

    private var _hacerManos = MutableLiveData<Boolean>()
    val hacerManos: LiveData<Boolean> get() = _hacerManos

    private val _hacerPies = MutableLiveData<Boolean>()
    val hacerPies: LiveData<Boolean> get() = _hacerPies

    init {
        _hacerManos.value = true
        _hacerPies.value = false
    }


    fun clickChipManos() {
        _hacerManos.value = !_hacerManos.value!!
    }

    fun clickChipPies() {
        _hacerPies.value = !_hacerPies.value!!
    }

    fun nombreDeClienteClick(cliente: String) {
        uiScope.launch {
            serviciosCliente.value = withContext(Dispatchers.IO) {
                dataSource.getServicios(cliente)
            }
        }
    }

    suspend fun onGetIdCliente(cliente: String) {
        var temporal: Long = 0L
        withContext(Dispatchers.IO) {
            temporal = dataSource.getIdCliente(cliente)
        }
        idClienteSeleccionado.value = temporal
    }

    fun onInsertarCliente(nombreCliente: String) {
        viewModelScope.launch {
            val nuevoCliente = TablaCliente()
            nuevoCliente.nombre = nombreCliente
            nuevoCliente.fechaUltimaVisita = 0L
            withContext(Dispatchers.IO) {
                dataSource.insertCliente(nuevoCliente)
            }
        }
    }

    suspend fun onAgregarServicio(cliente: String, montoPagado: Double) {
        _agregarServicio.clienteId = dataSource.getIdCliente(cliente)
        _agregarServicio.manos = _hacerManos.value!!
        _agregarServicio.pies = _hacerPies.value!!
        _agregarServicio.montoPagado = montoPagado
        _agregarServicio.fecha = System.currentTimeMillis()

        _actualizarCliente.clienteId = _agregarServicio.clienteId
        _actualizarCliente.nombre = cliente
        _actualizarCliente.fechaUltimaVisita = System.currentTimeMillis()
        _actualizarCliente.manos = hacerManos.value!!
        _actualizarCliente.pies = hacerPies.value!!

        withContext(Dispatchers.IO) {
            dataSource.insertServicio(_agregarServicio)
            dataSource.updateCliente(_actualizarCliente)
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}