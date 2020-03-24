package com.example.manicura.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private var _MontoTotal = MutableLiveData<String>()

    val MontoTotal: LiveData<String> = _MontoTotal

    private var _GananciasBrutas = MutableLiveData<String>()
    val GananciasBrutas: LiveData<String> = _MontoTotal

    private var _GananciasLiquidas = MutableLiveData<String>()
    val GananciasLiquidas: LiveData<String> = _MontoTotal

    init {
        _MontoTotal.value = "$0.000.000,00"
        _GananciasBrutas.value = "$0.000.000,00"
        _GananciasLiquidas.value = "$0.000.000,00"
    }
}