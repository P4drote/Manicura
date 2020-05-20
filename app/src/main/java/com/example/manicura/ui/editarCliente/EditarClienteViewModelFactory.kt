package com.example.manicura.ui.editarCliente

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.manicura.database.ManicuraDAO

class EditarClienteViewModelFactory(private val dataSource: ManicuraDAO) :
    ViewModelProvider.Factory {
    @Suppress("unchequed_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditarClienteViewModel::class.java)) {
            return EditarClienteViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}