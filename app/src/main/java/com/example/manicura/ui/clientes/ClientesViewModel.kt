package com.example.manicura.ui.clientes

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manicura.database.ManicuraDAO
import com.example.manicura.database.TablaCliente
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ClientesViewModel(private val dataSource: ManicuraDAO, application: Application) :
    ViewModel() {

    private val _listaClientes: LiveData<List<TablaCliente>> = dataSource.getTodosLosClientes()
    val listaClientes: LiveData<List<TablaCliente>> get() = _listaClientes

    var agregar = MutableLiveData<Boolean>()

    var clickCliente = MutableLiveData<TablaCliente>()

    init {
        agregar.value = true
    }


    fun agregarCliente(nombre: String) {
        var nuevoCliente = TablaCliente()
        nuevoCliente.nombre = nombre
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                dataSource.insertCliente(nuevoCliente)
                dataSource.getTodosLosClientes()
            }
        }
    }

    fun borrarCliente() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                dataSource.deleteCliente(clickCliente.value!!)
                dataSource.getTodosLosClientes()
            }
        }
    }

    fun editarCliente(nombre: String) {
        clickCliente.value?.nombre = nombre
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                dataSource.updateCliente(clickCliente.value!!)
            }
        }
    }

}