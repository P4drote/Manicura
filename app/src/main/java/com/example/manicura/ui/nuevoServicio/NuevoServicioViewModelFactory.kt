package com.example.manicura.ui.nuevoServicio

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.manicura.database.ManicuraDAO

class NuevoServicioViewModelFactory(
    private val dataSource: ManicuraDAO,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchequed_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NuevoServicioViewModel::class.java)) {
            return NuevoServicioViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}