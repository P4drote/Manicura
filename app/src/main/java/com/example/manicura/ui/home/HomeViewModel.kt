package com.example.manicura.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _MontoTotal = MutableLiveData<String>().apply {
        value = "$0.000.000,00"
    }
    val MontoTotal: LiveData<String> = _MontoTotal
}