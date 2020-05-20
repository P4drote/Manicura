package com.example.manicura.ui.editarCliente

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.manicura.Servicios
import com.example.manicura.database.ManicuraDAO
import com.example.manicura.database.TablaCliente
import kotlinx.coroutines.*

class EditarClienteViewModel(val database: ManicuraDAO) : ViewModel() {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var serviciosCliente: MutableLiveData<List<Servicios>> = MutableLiveData()

    var clienteBorrado: MutableLiveData<Boolean> = MutableLiveData()

    var clienteActualizado: MutableLiveData<Boolean> = MutableLiveData()

    private val _elCliente = MutableLiveData<TablaCliente>()
    val elCliente: LiveData<TablaCliente> get() = _elCliente


    fun obtenerCliente(clienteid: Long) {
        uiScope.launch {
            _elCliente.value = withContext(Dispatchers.IO) {
                database.getCliente(clienteid)
            }
            serviciosCliente.value = withContext(Dispatchers.IO) {
                database.getServicios(_elCliente.value!!.nombre)
            }
        }
    }

    fun borrarCliente() {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                _elCliente.value?.let { database.deleteCliente(it) }
            }
            clienteBorrado.value = true
        }
    }

    fun actualizarCliente(nombreActualizar: String) {
        _elCliente.value!!.nombre = nombreActualizar
        uiScope.launch {
            withContext(Dispatchers.IO) {
                database.updateCliente(_elCliente.value!!)
            }
            clienteActualizado.value = true
        }
    }

}