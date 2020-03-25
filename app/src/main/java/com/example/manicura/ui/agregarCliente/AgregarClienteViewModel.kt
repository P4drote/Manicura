package com.example.manicura.ui.agregarCliente

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.manicura.database.ManicuraDAO
import com.example.manicura.database.TablaCliente
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class AgregarClienteViewModel(
    val database: ManicuraDAO,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    //var nombreCLiente: String = ""
    var campoRequerido: String = ""
    var hicieronClick = MutableLiveData<Boolean?>()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun onInsertarCliente(nombreCliente: String) {

        uiScope.launch {
            val nuevoCliente = TablaCliente()
            nuevoCliente.nombre = nombreCliente
            database.insertCliente(nuevoCliente)
            //hicieronClick.value = true
        }

    }

}