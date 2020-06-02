package com.example.manicura.ui.editorServicios

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.manicura.database.ManicuraDAO

/**
 * Creada por Andrés Cermeño el 1/6/2020
 */
class DialogEditarServiciosViewModelFactory(
    private val dataSource: ManicuraDAO,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchequed_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DialogEditarServiciosViewModel::class.java)) {
            return DialogEditarServiciosViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}