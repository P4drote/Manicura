package com.example.manicura.ui.agregarCliente

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manicura.database.ManicuraDAO
import com.example.manicura.database.TablaCliente
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AgregarClienteViewModel(val database: ManicuraDAO) : ViewModel() {

    private val _mensage = MutableLiveData<Boolean>()
    val mensage: LiveData<Boolean> get() = _mensage

    private val _salida = MutableLiveData<Boolean>()
    val salida: LiveData<Boolean> get() = _salida

    fun onInsertarCliente(nombreCliente: String) {
        if (nombreCliente.isEmpty()) {
            _mensage.value = true
        } else {
            viewModelScope.launch {
                val nuevoCliente = TablaCliente()
                nuevoCliente.nombre = nombreCliente
                withContext(Dispatchers.IO) {
                    database.insertCliente(nuevoCliente)
                }
                _salida.value = true
            }
        }
    }

}